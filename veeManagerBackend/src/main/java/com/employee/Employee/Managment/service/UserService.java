package com.employee.Employee.Managment.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.text.ParseException;

import com.employee.Employee.Managment.model.LoginModel;
import com.employee.Employee.Managment.model.UserDto;

public interface UserService {

	String signup(UserDto userDto) throws UnsupportedEncodingException;

	UserDto getUserDetails(String userId);
	
	String deleteUser(String userId);

String  googleSignUp(String code) throws GeneralSecurityException, IOException;

String resetPassword(LoginModel loginModel) throws ParseException;


	String getEmailFromGoogleAccessToken(String code) throws GeneralSecurityException, IOException;



	String getLoginUser(UserDto userDto, boolean isValiddate);

	String getLoginUser(String email, boolean isValiddate);

	InputStream getResource(String path, String imageName) throws FileNotFoundException;

}
