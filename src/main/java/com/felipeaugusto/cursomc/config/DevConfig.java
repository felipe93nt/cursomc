package com.felipeaugusto.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.felipeaugusto.cursomc.services.DBService;
import com.felipeaugusto.cursomc.services.EmailService;
import com.felipeaugusto.cursomc.services.MockEmailService;

@Configuration
@Profile("dev")
public class DevConfig {
	
	@Autowired
	private DBService dbService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Bean 
	public boolean instanciateDataBase() throws ParseException {
		if(!"create".equals(strategy)) {
			return false;
		}
		dbService.InstantiateDevDatabase();
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
}
