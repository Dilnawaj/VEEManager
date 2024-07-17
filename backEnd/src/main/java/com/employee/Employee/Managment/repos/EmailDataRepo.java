package com.employee.Employee.Managment.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.Employee.Managment.entities.EmailData;

public interface EmailDataRepo extends JpaRepository<EmailData,Long>{
	List<EmailData> findByUserId(Long userId);

}
