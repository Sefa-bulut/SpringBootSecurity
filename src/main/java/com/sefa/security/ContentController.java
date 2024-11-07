package com.sefa.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sefa.security.webtoken.JwtService;
import com.sefa.security.webtoken.LoginForm;

@RestController
public class ContentController {
	
	@Autowired
	private AuthenticationManager authenticationManager; //Buradaki hazır metotlarla kimlik doğrulumada yapıcaz
	@Autowired
	private JwtService jwtService;
	@Autowired
	private MyUserDetailService myUserDetailService;
	
	@GetMapping("/home")
	public String handleWelcome() {
		return "home";
	}
	
	@GetMapping("/admin/home")
	public String handleAdminHome() {
		return "home_admin";
	}
	
	@GetMapping("/user/home")
	public String handleUserHome() {
		//buradaki return ettiğimiz "home_user"
		//aslında templates deki "home_user.html" dosyamız
		return "home_user";
	}
	
	@PostMapping("/authenticate")
	public String authenticateAndGetToken(@RequestBody LoginForm loginForm) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginForm.getUsername(), loginForm.getPassword()
			));
		if(authentication.isAuthenticated()) {
			return jwtService.generateToken(myUserDetailService.loadUserByUsername(loginForm.getUsername()));
		} else {
			throw new UsernameNotFoundException("Geçersiz Kullanıcı!");
		}
	}
	
}
