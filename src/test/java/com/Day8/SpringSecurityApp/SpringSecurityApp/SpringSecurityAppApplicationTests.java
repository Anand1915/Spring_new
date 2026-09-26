package com.Day8.SpringSecurityApp.SpringSecurityApp;

import com.Day8.SpringSecurityApp.SpringSecurityApp.entites.User;
import com.Day8.SpringSecurityApp.SpringSecurityApp.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringSecurityAppApplicationTests {
    @Autowired
	private JwtService jwtService;
	@Test
	void contextLoads() {

		User user = new User(4L,"Anand@gmail.com","1234");

		String token = jwtService.generateToken(user);

		System.out.println(token);

		Long id = jwtService.getUserIdFromToken(token);

		System.out.println(id);
	}



}
