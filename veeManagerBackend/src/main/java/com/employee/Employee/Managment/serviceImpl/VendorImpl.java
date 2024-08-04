package com.employee.Employee.Managment.serviceImpl;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.employee.Employee.Managment.config.EmailService;
import com.employee.Employee.Managment.entities.EmailData;
import com.employee.Employee.Managment.entities.User;
import com.employee.Employee.Managment.entities.Vendor;
import com.employee.Employee.Managment.exception.BadRequestException;
import com.employee.Employee.Managment.model.Email;
import com.employee.Employee.Managment.model.ShareEmail;
import com.employee.Employee.Managment.model.VendorDto;
import com.employee.Employee.Managment.repos.EmailDataRepo;
import com.employee.Employee.Managment.repos.UserRepo;
import com.employee.Employee.Managment.repos.VendorRepo;
import com.employee.Employee.Managment.service.VendorService;
@Service
public class VendorImpl implements VendorService{
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private VendorRepo vendorRepo;
	@Autowired
	private EmailService emailService;
	@Autowired
	private EmailDataRepo emailDataRepo;
	
	private ModelMapper modelMapper = new ModelMapper();
	@Override
	public String addVendor(VendorDto vendorDto, String userId) {
		Optional<User> userOpt = userRepo.findById(userId.toString());
		if (userOpt.isPresent()) {

			Optional<Vendor> vendorOpt = vendorRepo.findByEmailAddress(vendorDto.getEmailAddress());
			if (vendorOpt.isEmpty()) {
				Vendor vendor = this.modelMapper.map(vendorDto, Vendor.class);
				vendor.setManager(userOpt.get().getName());
				vendor.setUser(userOpt.get());
				vendor = vendorRepo.save(vendor);
				JSONObject json = new JSONObject();
				return json.put("message", "Vendor created successfully.").toString();
			}
			throw BadRequestException.of("Vendor already exsist.");
		}

		throw BadRequestException.of("Manager does not exist.");
	}

	@Override
	public String deleteVendor(String vendorId) {
		Optional<Vendor> vendorOpt = vendorRepo.findById(vendorId.toString());
		if (vendorOpt.isPresent()) {
			vendorRepo.delete(vendorOpt.get());
			JSONObject json = new JSONObject();
			return json.put("message", "Vendor deleted  successfully.").toString();
		}
		throw BadRequestException.of("Vendor does not exsist");
	}

	@Override
	public VendorDto getVendor(String vendorId) {
		Optional<Vendor> vendorOpt = vendorRepo.findById(vendorId.toString());

		if (vendorOpt.isPresent()) {
			return this.modelMapper.map(vendorOpt.get(), VendorDto.class);
		}

		throw BadRequestException.of("Vendor does not exsist");
	}

	@Override
	public String updateVendor(VendorDto vendorDto, String userId, String vendorId) {
		Optional<Vendor> vendorOpt = vendorRepo.findById(vendorId.toString());
		if (vendorOpt.isPresent()) {
			Vendor vendor = vendorOpt.get();
			
			vendor.setUpi(vendorDto.getUpi());
			vendor.setEmailAddress(vendorDto.getEmailAddress());
			vendor.setName(vendorDto.getName());
			vendorRepo.save(vendor);
			JSONObject json = new JSONObject();
			return json.put("message", "Vendor updated  successfully.").toString();
		}
		throw BadRequestException.of("Vendor does not exsist");
	}

	@Async
	public void sharePost(ShareEmail shareEmail) throws UnsupportedEncodingException {
		
		Map<String,String> emailAndUpi=  shareEmail.getEmails().stream().collect(Collectors.toMap(e->e.getEmail(),e->e.getUpi()));
		

	    for (Map.Entry<String, String> entry : emailAndUpi.entrySet()) {
	    	String email = entry.getKey();
	    	Optional<Vendor> vendorOpt= vendorRepo.findByEmailAddress(email);
	        
	       if(!vendorOpt.isEmpty())
	       {

		        String upi = entry.getValue();
		        emailService.sendEmailForVendors(email,upi,vendorOpt.get());
	       }
	    }
		
		
	}

	@Override
	public List<EmailData> getAllEmail(String userId) {
	return emailDataRepo.findByUserId(userId);
	}

}
