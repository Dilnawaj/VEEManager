package com.employee.Employee.Managment.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.Employee.Managment.entities.User;

public interface UserRepo extends JpaRepository<User,Long>{
	
	Optional<User> findByEmail(String email);
	
	List<User> findByVerificationCode(String code);
	

}
