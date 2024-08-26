package com.employee.Employee.Managment.entities;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Vendor")
public class Vendor implements Serializable{
	@Id
	private String id;
	
	private String name;

	private String upi;
	
	private String emailAddress;


	private String manager;

	@DBRef
	private User user;

	private static final long serialVersionUID = -3515756744958077422L;
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getUpi() {
		return upi;
	}


	public void setUpi(String upi) {
		this.upi = upi;
	}


	public String getEmailAddress() {
		return emailAddress;
	}


	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public String getManager() {
		return manager;
	}


	public void setManager(String manager) {
		this.manager = manager;
	}
	
	
	
}
