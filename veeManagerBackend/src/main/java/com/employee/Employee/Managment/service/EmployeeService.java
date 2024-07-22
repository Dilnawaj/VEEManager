package com.employee.Employee.Managment.service;

import com.employee.Employee.Managment.model.EmployeeDto;

public interface EmployeeService {

	String addEmployee(EmployeeDto employeeDto, Long userId);

	String updateEmployee(EmployeeDto employeeDto, Long userId, Long employeeId);

	EmployeeDto getEmployee(Long employeeId);

	String deleteEmployee(Long employeeId);
}
