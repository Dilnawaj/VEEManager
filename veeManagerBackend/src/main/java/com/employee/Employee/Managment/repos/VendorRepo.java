package com.employee.Employee.Managment.repos;

import java.util.List;
import java.util.Optional;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.employee.Employee.Managment.entities.User;
import com.employee.Employee.Managment.entities.Vendor;

public interface VendorRepo extends MongoRepository<Vendor,String>{

	Optional<Vendor> findByEmailAddress(String email);
	
	List<Vendor> findByUser(User user);
}
