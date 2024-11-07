package com.sefa.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Autowired
	private MyUserDetailService userDetailSvc;
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	//Authorization(yetkilendirme)
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		//burada kendi konfigürasyonumuzu yazıyoruz
		return http
				//spring boot'da default olarak saldırılarını engellemek csfr açık gelir bu da tüm istekleri blocklar
				//bunu düzeltmek için ya kendi ayarlarmızı yazmalıyız yada geçici olarak kapatmalıyız
			.csrf(httpSecurityCsrfCongigurer -> httpSecurityCsrfCongigurer.disable()) 
			.authorizeHttpRequests(registry -> {
				registry.requestMatchers("/home", "/register/**", "/authenticate").permitAll();
				registry.requestMatchers("/admin/**").hasRole("ADMIN");
				registry.requestMatchers("/user/**").hasRole("USER");
				registry.anyRequest().authenticated(); //yukarıda belirtilen url'ler dışındaki her istekler için kimlik doğrula
			})
			//login sayfası da herkese erişilebilir olmalı
			.formLogin(formLogin -> formLogin.permitAll()) //default login page
			// JWT doğrulama filtresini UsernamePasswordAuthenticationFilter'dan önce çalıştır
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.build();
		
	}
	
	
	//Authentication using In-memory (yani kullanıcılar herhangi bir database de tutulmuyor)
	/*
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails normalUser = User.builder()
				.username("sefa")
				.password("$2a$12$qvF1K1jbAq0Efl4FoFhjzuHJNL6Hb0ixsgOGRWlybZlqGl3RUQW02") //1357
				.roles("USER")
				.build();
		UserDetails adminUser = User.builder()
				.username("admin")
				.password("$2a$12$KuNicb5uIGHq9hs/4tgEPukNxs4BWtz1DnxunYjdz9zXA.QamUFvq") //2468
				.roles("ADMIN", "USER")
				.build();
		return new InMemoryUserDetailsManager(normalUser, adminUser);
	}
	*/
	
	//Authentication using real database
	@Bean
	public UserDetailsService userDetailsService() {
		return userDetailSvc;
	}
	
	//Bu bean, kimlik doğrulama sürecinde kullanılmak üzere Spring context'ine kaydedilecektir.
	@Bean 
	public AuthenticationProvider authenticationProvider () {
		//DaoAuthenticaton sınıfı, kullanıcı bilgilerini veritabanından alır ve doğrulama işlemini gerçekleştirir. 
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailSvc);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(authenticationProvider());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	

}
