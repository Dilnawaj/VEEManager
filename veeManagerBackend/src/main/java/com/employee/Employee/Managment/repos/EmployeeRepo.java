package com.employee.Employee.Managment.repos;

import java.util.List;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.employee.Employee.Managment.entities.Employee;
import com.employee.Employee.Managment.entities.User;

public interface EmployeeRepo extends MongoRepository<Employee,String> {
	
	Optional<Employee> findByEmailAddress(String email);
	
	List<Employee> findByManager(String manager);
	List<Employee> findByUser(User user);
}
