package com.employee.Employee.Managment.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.Employee.Managment.entities.Employee;
import com.employee.Employee.Managment.entities.User;
import com.employee.Employee.Managment.exception.BadRequestException;
import com.employee.Employee.Managment.model.EmployeeDto;
import com.employee.Employee.Managment.model.UserDto;
import com.employee.Employee.Managment.repos.EmployeeRepo;
import com.employee.Employee.Managment.repos.UserRepo;
import com.employee.Employee.Managment.service.EmployeeService;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import jakarta.annotation.PostConstruct;

@Service
public class EmployeeImpl implements EmployeeService {
	
	  private static final String MAP_NAME = "employee-map";

	    @Autowired
	    private HazelcastInstance hazelcastInstance;


	    private IMap<String, Employee> employeeMap;

	    @PostConstruct
	    public void init() {
	        employeeMap = hazelcastInstance.getMap(MAP_NAME);
	    }

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private EmployeeRepo employeeRepo;

	private ModelMapper modelMapper = new ModelMapper();

	@Override
	public void addEmployee(EmployeeDto employeeDto, String userId) {

		Optional<User> userOpt = userRepo.findById(userId.toString());
		if (userOpt.isPresent()) {

			Optional<Employee> employeeOpt = employeeRepo.findByEmailAddress(employeeDto.getEmailAddress());
			if (employeeOpt.isEmpty()) {
				Employee employee = this.modelMapper.map(employeeDto, Employee.class);
				employee.setManager(userOpt.get().getName());
				employee.setUser(userOpt.get());
				employee = employeeRepo.save(employee);
				 employeeMap.put(employee.getId(), employee);
				return ;
			}
			throw BadRequestException.of("Employee already exsist.");
		}

		throw BadRequestException.of("Manager does not exist.");
	}

	@Override
	public void updateEmployee(EmployeeDto employeeDto, String userId,String employeeId) {
		Optional<Employee> employeeOpt = employeeRepo.findById(employeeId.toString());
		if (employeeOpt.isPresent()) {
			Employee employee = employeeOpt.get();
			employee.setCtc(employeeDto.getCtc());
			employee.setDesignation(employeeDto.getDesignation());
			employee.setEmailAddress(employeeDto.getEmailAddress());
			employee.setName(employeeDto.getName());
			employeeRepo.save(employee);
		 employeeMap.put(employee.getId(), employee);
			return ;
		}
		throw BadRequestException.of("Employee does not exsist");
	}

	@Override
	public EmployeeDto getEmployee(String employeeId) {
		if(employeeMap.get(employeeId)!=null)
		{
			return this.modelMapper.map(employeeMap.get(employeeId), EmployeeDto.class);
		}
		Optional<Employee> employeeOpt = employeeRepo.findById(employeeId.toString());

		if (employeeOpt.isPresent()) {
			return this.modelMapper.map(employeeOpt.get(), EmployeeDto.class);
		}

		throw BadRequestException.of("Employee does not exsist");
	}

	@Override
	public String deleteEmployee(String employeeId) {
		Optional<Employee> employeeOpt = employeeRepo.findById(employeeId.toString());
		if (employeeOpt.isPresent()) {
			employeeRepo.delete(employeeOpt.get());
			JSONObject json = new JSONObject();
			 employeeMap.remove(employeeId);
			return json.put("message", "Employee deleted  successfully.").toString();
		}
		throw BadRequestException.of("Employee does not exsist");
	}

}
