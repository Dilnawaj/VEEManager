package com.employee.Employee.Managment.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class LoginModel {

	@NotNull
	@NotEmpty
	@Email(message = "Email Address is not Valid")
	private String email;

	@NotNull
	@NotEmpty
	private String password;


	/**
	 * 
	 * code hold credential return by google login.
	 * 
	 * @param code credential get by google login
	 */
	private String code;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		if (password == null) {
			return "";
		}
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	



	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean checkValidationForPassword() {
		if (!getPassword().equals("")) {
			return true;
		}
		return false;
	}

}
