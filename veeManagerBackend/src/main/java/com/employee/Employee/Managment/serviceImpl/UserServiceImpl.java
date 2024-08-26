package com.employee.Employee.Managment.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.employee.Employee.Managment.config.EmailService;
import com.employee.Employee.Managment.config.EmailUtil;
import com.employee.Employee.Managment.config.JavaHelper;
import com.employee.Employee.Managment.entities.Employee;
import com.employee.Employee.Managment.entities.User;
import com.employee.Employee.Managment.entities.Vendor;
import com.employee.Employee.Managment.exception.BadRequestException;
import com.employee.Employee.Managment.model.EmployeeDto;
import com.employee.Employee.Managment.model.LoginModel;
import com.employee.Employee.Managment.model.UserDto;
import com.employee.Employee.Managment.model.VendorDto;
import com.employee.Employee.Managment.repos.EmployeeRepo;
import com.employee.Employee.Managment.repos.UserRepo;
import com.employee.Employee.Managment.repos.VendorRepo;
import com.employee.Employee.Managment.service.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import jakarta.annotation.PostConstruct;

@Service
public class UserServiceImpl implements UserService {
	private static final String MAP_NAME = "user-map";

	@Autowired
	private HazelcastInstance hazelcastInstance;

	private IMap<String, User> userMap;

	@PostConstruct
	public void init() {
		userMap = hazelcastInstance.getMap(MAP_NAME);
	}

	private ModelMapper modelMapper = new ModelMapper();

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private VendorRepo vendorRepo;

	@Autowired
	private EmailService emailService;

	@Value("${clientId}")
	private String clientId;

	@Override
	public String getLoginUser(UserDto userDto, boolean isValidate) {

		if (userDto.isValidLogin() || !isValidate) {
			userDto.setEmail(userDto.getEmail().trim().toLowerCase());
			Optional<User> userOpt = userRepo.findByEmail(userDto.getEmail());

			if (userOpt.isEmpty()) {
				throw BadRequestException.of("Invalid email address");
			}

			if ((!isValidate)
					|| (JavaHelper.checkPassword(userDto.getPassword(), userOpt.get().getPassword(), isValidate))) {
				JSONObject json = new JSONObject();
				return json.put("id", userOpt.get().getUserId()).toString();
			}
			throw BadRequestException.of("Email or password is wrong");

		}
		throw BadRequestException.of("Please check your input credentials");

	}

	public UserDto getUserDetails(String userId) {

		if (userMap.get(userId) != null) {
			return this.modelMapper.map(userMap.get(userId), UserDto.class);
		}

		Optional<User> userOptional = userRepo.findById(userId.toString());
		UserDto userDto = new UserDto();
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			userDto = this.modelMapper.map(user, UserDto.class);
			userDto.setEmployee(employeeRepo.findByUser(user).stream()
					.map(e -> this.modelMapper.map(e, EmployeeDto.class)).collect(Collectors.toList()));

			userDto.setVendor(vendorRepo.findByUser(user).stream().map(e -> this.modelMapper.map(e, VendorDto.class))
					.collect(Collectors.toList()));
		}
		return userDto;
	}

	public String getFreshVerificationCode() {
		String verificationCode = getVerificationCode();
		List<User> users = userRepo.findByVerificationCode(verificationCode);
		if (users == null || users.isEmpty()) {
			return verificationCode;
		}
		while (!users.isEmpty()) {
			verificationCode = getVerificationCode();
			users = userRepo.findByVerificationCode(verificationCode);
			if (users == null) {
				return verificationCode;
			}
		}
		return verificationCode;
	}

	public String getVerificationCode() {
		String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "*@" + "0123456789" + "abcdefghijklmnopqrstuvwxyz";
		StringBuilder sb = new StringBuilder(20);
		SecureRandom secureRandom = new SecureRandom();
		for (int i = 0; i < 150; i++) {
			int index = secureRandom.nextInt(alphaNumericString.length());

			sb.append(alphaNumericString.charAt(index));
		}

		return sb.toString();
	}

	@Override
	public String deleteUser(String userId) {
		Optional<User> userOpt = userRepo.findById(userId.toString());
		if (userOpt.isPresent()) {
			userRepo.delete(userOpt.get());
			JSONObject json = new JSONObject();
			userMap.remove(userId);
			return json.put("message", "Employee deleted  successfully.").toString();
		}
		throw BadRequestException.of("Employee does not exsist");
	}

	@Override
	public void signup(UserDto userDto) throws UnsupportedEncodingException {

		if (userDto.isValid()) {
			userDto.setEmail(userDto.getEmail().trim().toLowerCase());
			Optional<User> userOpt = userRepo.findByEmail(userDto.getEmail());
			if (userOpt.isPresent()) {
				throw BadRequestException.of("User already exists");
			}

			User user = this.modelMapper.map(userDto, User.class);
			user.setVerificationCode(getFreshVerificationCode());
			if (userDto.getPassword() == null) {
				emailService.sendEmailForRegister(user);
			} else {
				user.setPasswordSet(true);
			}
			user.setLinkExpiryDate(JavaHelper.getCurrentDate().toString());
			userRepo.save(user);
			// Store user in Hazelcast map
			userMap.put(user.getUserId(), user);

			JSONObject json = new JSONObject();

		} else {
			throw BadRequestException.of("Please check your input credentials");
		}

	}

	public void googleSignUp(String code) throws GeneralSecurityException, IOException {
		if (code != null) {
			// 30-June-2022 @manish get id token from credential
			// first verify credential code with google then it will return us id Token.
			// id token contain all user information.
			GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(),
					new GsonFactory())
					// Specify the CLIENT_ID
					.setAudience(Collections.singletonList(clientId))
					// Or, if multiple clients access then:
					// .setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
					.build();
			// verify google jwt code
			GoogleIdToken idToken = verifier.verify(code);
			if (idToken != null) {
				// get user details from payload
				Payload payload = idToken.getPayload();
				String userEmail = payload.getEmail();
				String name = (String) payload.get("name");
				if (userEmail != null && !userEmail.isEmpty() && !userEmail.equals("")) {
					UserDto userDto = new UserDto();
					userDto.setEmail(userEmail);
					userDto.setName(name);
					userDto.setPassword(EmailUtil.generateRandomPassword(10, 97, 122));
					userDto.setAbout("Google User");
					signup(userDto);

				}
			}
		}
		throw BadRequestException.of("Error occur while user signup");

	}

	public String resetPassword(LoginModel loginModel) throws ParseException {

		if (loginModel.checkValidationForPassword()) {
			List<User> users = new ArrayList<>();
			Date currentDate = JavaHelper.getCurrentDate();
			if (loginModel.getCode().equals("")) {
				User user = userRepo.findByEmail(loginModel.getEmail()).get();
				user.setLinkExpiryDate(JavaHelper.getCurrentDate().toString());
				users.add(user);

			} else {
				users = userRepo.findByVerificationCode(loginModel.getCode());
			}

			if (users.size() == 1) {
				User user = users.get(0);
				Date expiryDate = JavaHelper.dateStringToDate(user.getLinkExpiryDate());
				Integer minutes = JavaHelper.getDiffInMinutes(expiryDate, currentDate);
				if (minutes <= 15) {
					user.setPassword(JavaHelper.getBcrypt(loginModel.getPassword()));
					user.setVerificationCode(null);
					user.setPasswordSet(true);
					userRepo.save(user);
					return "";
				}
				throw BadRequestException.of("Link is expire.Please try again.");
			}
			throw BadRequestException.of("The link has already been used. Please try to reset your password again.");

		}
		throw BadRequestException.of("Invalid credentials");
	}

	@Override
	public String getEmailFromGoogleAccessToken(String code) throws GeneralSecurityException, IOException {
		// 30-June-2022 @manish get id token from credential
		// first verify credential code with google then it will return us id Token.
		// id token contain all user information.
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
				// Specify the CLIENT_ID
				.setAudience(Collections.singletonList(clientId))
				// Or, if multiple clients access then:
				// .setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
				.build();
		// verify google jwt code
		GoogleIdToken idToken = verifier.verify(code);
		if (idToken != null) {
			// get user details from payload
			Payload payload = idToken.getPayload();
			return payload.getEmail();
		}
		return "";
	}

	@Override
	public String getLoginUser(String email, boolean isValiddate) {
		UserDto userDto = new UserDto();
		userDto.setEmail(email);
		return getLoginUser(userDto, false);
	}

	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullPath = path + File.separator + fileName;
		return new FileInputStream(fullPath);

	}

}
