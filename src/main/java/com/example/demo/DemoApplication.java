package com.example.demo;

import com.example.demo.Models.UserDTO;
import com.example.demo.Security.CustomUserDetailService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String[] args) {
//		UserDetails userDetails = applicationContext.getBean(CustomUserDetailService.class).loadUserByUsername(email);
//		UserDTO userDTO = (UserDTO) userDetails;
	}

}
