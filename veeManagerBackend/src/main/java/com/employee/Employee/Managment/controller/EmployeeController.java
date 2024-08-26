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
	ResponseEntity<String> addEmployee(@RequestBody EmployeeDto employeeDto, @PathVariable String userId) {
		employeeService.addEmployee(employeeDto, userId);
		return ResponseEntity.ok("");
	}

	@PutMapping("/update")
	ResponseEntity<String> updateEmployee(@RequestBody EmployeeDto employeeDto, @RequestParam String userId,@RequestParam String employeeId) {
		employeeService.updateEmployee(employeeDto, userId,employeeId);
		return ResponseEntity.ok("");
	}


	@GetMapping("/{employeeId}")
	ResponseEntity<EmployeeDto> getEmployee(@PathVariable String employeeId) {
		return ResponseEntity.ok(employeeService.getEmployee(employeeId));
	}

	@DeleteMapping("/{employeeId}")
	ResponseEntity<String> deleteEmployee(@PathVariable String employeeId) {
		return ResponseEntity.ok(employeeService.deleteEmployee(employeeId));
	}

}
