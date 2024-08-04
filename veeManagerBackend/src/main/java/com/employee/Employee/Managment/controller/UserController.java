package com.employee.Employee.Managment.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.employee.Employee.Managment.model.LoginModel;
import com.employee.Employee.Managment.model.UserDto;
import com.employee.Employee.Managment.service.UserService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Value("${project.image}")
	private String path;

	@PostMapping(value = "/signup", produces = "application/json")
	ResponseEntity<String> signUpUser(@RequestBody UserDto userDto) throws UnsupportedEncodingException {
		return ResponseEntity.ok(userService.signup(userDto));
	}
	@GetMapping(value = "/account/googlesignupprocess/{code}", produces = "application/json; charset=utf-8")
	public ResponseEntity<Object> googlesignup(@PathVariable String code) throws GeneralSecurityException, IOException {

		return ResponseEntity.ok(userService.googleSignUp(code));
	}
	
	@PostMapping(value = "/reset", produces = "application/json; charset=utf-8", consumes = "application/json")
	public ResponseEntity<Object> resetPassword(@RequestBody LoginModel loginModel) throws ParseException {
		return ResponseEntity.ok(userService.resetPassword(loginModel));
	}
	


	@GetMapping(value = "/google/login/{code}", produces = "application/json; charset=utf-8")
	public ResponseEntity<Object> googleLogin(@PathVariable String code) throws GeneralSecurityException, IOException {
		String email = userService.getEmailFromGoogleAccessToken(code);
		return ResponseEntity.ok(userService.getLoginUser(email, false));
	}
	@PostMapping(value = "/login", produces = "application/json")
	ResponseEntity<String> loginUser(@RequestBody UserDto userDto) {
		return ResponseEntity.ok(userService.getLoginUser(userDto, true));
	}

	@GetMapping(value = "/{userId}", produces = "application/json")
	ResponseEntity<UserDto> loginUser(@PathVariable String userId) {
		return ResponseEntity.ok(userService.getUserDetails(userId));
	}
	@DeleteMapping("/{userId}")
	ResponseEntity<String> deleteUser(@PathVariable String userId) {
		return ResponseEntity.ok(userService.deleteUser(userId));
	}
	
	@GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response)
			throws IOException {
		
		InputStream resource = this.userService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());

	}
}
