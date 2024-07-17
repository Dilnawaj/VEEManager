package com.employee.Employee.Managment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.employee.Employee.Managment.model.EmployeeDto;
import com.employee.Employee.Managment.service.EmployeeService;

@Controller
@CrossOrigin
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@PostMapping("add/{userId}")
	ResponseEntity<String> addEmployee(@RequestBody EmployeeDto employeeDto, @PathVariable Long userId) {
		return ResponseEntity.ok(employeeService.addEmployee(employeeDto, userId));
	}

	@PutMapping("/update")
	ResponseEntity<String> updateEmployee(@RequestBody EmployeeDto employeeDto, @RequestParam Long userId,@RequestParam Long employeeId) {
		return ResponseEntity.ok(employeeService.updateEmployee(employeeDto, userId,employeeId));
	}


	@GetMapping("/{employeeId}")
	ResponseEntity<EmployeeDto> getEmployee(@PathVariable Long employeeId) {
		return ResponseEntity.ok(employeeService.getEmployee(employeeId));
	}

	@DeleteMapping("/{employeeId}")
	ResponseEntity<String> deleteEmployee(@PathVariable Long employeeId) {
		return ResponseEntity.ok(employeeService.deleteEmployee(employeeId));
	}

}
