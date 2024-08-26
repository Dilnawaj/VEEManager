package com.employee.Employee.Managment.service;

import com.employee.Employee.Managment.model.EmployeeDto;

public interface EmployeeService {

	void addEmployee(EmployeeDto employeeDto, String userId);

	void updateEmployee(EmployeeDto employeeDto, String userId, String employeeId);

	EmployeeDto getEmployee(String employeeId);

	String deleteEmployee(String employeeId);
}
