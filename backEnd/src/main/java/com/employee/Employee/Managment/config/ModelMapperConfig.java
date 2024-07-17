package com.employee.Employee.Managment.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
	@Bean
	ModelMapper newModelMapper()
	{
		return new ModelMapper();
	}

}
