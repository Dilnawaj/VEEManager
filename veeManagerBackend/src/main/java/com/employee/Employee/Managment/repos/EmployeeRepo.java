package com.employee.Employee.Managment.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.Employee.Managment.entities.Employee;

public interface EmployeeRepo extends JpaRepository<Employee,Long> {
	
	Optional<Employee> findByEmailAddress(String email);
	
	List<Employee> findByManager(String manager);
}
