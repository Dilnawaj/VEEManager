package com.employee.Employee.Managment.model;

import java.util.ArrayList;
import java.util.List;

import com.employee.Employee.Managment.entities.Employee;

public class UserDto {

	private String name;

	private String email;

	private String about;

	private String dob;

	private String password;
	
	private List<EmployeeDto> employee = new ArrayList<>();
	
	private List<VendorDto> vendor = new ArrayList<>();
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



	public List<EmployeeDto> getEmployee() {
		return employee;
	}

	public void setEmployee(List<EmployeeDto> employee) {
		this.employee = employee;
	}
	
	

	public List<VendorDto> getVendor() {
		return vendor;
	}

	public void setVendor(List<VendorDto> vendor) {
		this.vendor = vendor;
	}

	public boolean isValid() {
		if ((!"".equals(getEmail()) && getEmail() != null) && (!"".equals(getName()) && getName() != null)
				) {
			return true;
		}
		return false;
	}
	public boolean isValidLogin() {
		if ((!"".equals(getEmail()) && getEmail() != null) 
				&& (!"".equals(getPassword()) && getPassword() != null)) {
			return true;
		}
		return false;
	}
}
