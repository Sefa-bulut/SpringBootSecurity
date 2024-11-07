package com.sefa.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sefa.security.entities.MyUser;
import com.sefa.security.repos.MyUserRepository;

@RestController
public class RegistrationController {
	
	@Autowired
	private MyUserRepository myUserRepository;
	@Autowired
	private PasswordEncoder passwordEncoder; //bu bize bcryt olarak verecek çünkü contentcontroller sınıfında o şekilde configüre ettik
	
	@PostMapping("/register/user")
	public MyUser createUser(@RequestBody MyUser user) {
		//passwordu şifreleyip o şekilde database'e kayıt ediyoruz
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return myUserRepository.save(user);
	}
	
}
