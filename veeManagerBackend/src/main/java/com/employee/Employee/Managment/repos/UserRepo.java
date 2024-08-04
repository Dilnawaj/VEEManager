package com.employee.Employee.Managment.repos;

import java.util.List;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.employee.Employee.Managment.entities.User;

public interface UserRepo extends MongoRepository<User,String>{
	
	Optional<User> findByEmail(String email);
	
	List<User> findByVerificationCode(String code);
	

}
