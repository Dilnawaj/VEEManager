package com.employee.Employee.Managment.repos;

import java.util.List;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.employee.Employee.Managment.entities.EmailData;

public interface EmailDataRepo extends MongoRepository<EmailData,String>{
	List<EmailData> findByUserId(String userId);

}
