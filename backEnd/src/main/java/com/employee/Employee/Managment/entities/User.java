package com.employee.Employee.Managment.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "userId")
	private Long userId;
	
	private String name;

	private String email;

	private String about;

	private String dob;

	private String password;
	
	private String verificationCode;

	private String linkExpiryDate;
	

	private boolean isPasswordSet;
	
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Employee> employee = new ArrayList<>();
	
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Vendor> vendor = new ArrayList<>();

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

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
		  if(password!=null)
		  {
		        this.password = password;
		  }
	
	    }

	public List<Employee> getEmployee() {
		return employee;
	}

	public void setEmployee(List<Employee> employee) {
		this.employee = employee;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getLinkExpiryDate() {
		return linkExpiryDate;
	}

	public void setLinkExpiryDate(String linkExpiryDate) {
		this.linkExpiryDate = linkExpiryDate;
	}

	public boolean isPasswordSet() {
		return isPasswordSet;
	}

	public void setPasswordSet(boolean isPasswordSet) {
		this.isPasswordSet = isPasswordSet;
	}

	public List<Vendor> getVendor() {
		return vendor;
	}

	public void setVendor(List<Vendor> vendor) {
		this.vendor = vendor;
	}

}
