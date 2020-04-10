package com.felipeaugusto.cursomc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.felipeaugusto.cursomc.services.S3Service;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}
	
	@Autowired
	private S3Service s3;
	
	
	@Override
	public void run(String... args) throws Exception {
		s3.uploadFile("C:\\Users\\Victor\\Desktop\\Certificados\\Certificaçao_Java.png");
	}
}
