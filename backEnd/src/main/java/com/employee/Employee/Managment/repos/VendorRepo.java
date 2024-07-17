package com.employee.Employee.Managment.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.Employee.Managment.entities.Vendor;

public interface VendorRepo extends JpaRepository<Vendor,Long>{

	Optional<Vendor> findByEmailAddress(String email);
}
