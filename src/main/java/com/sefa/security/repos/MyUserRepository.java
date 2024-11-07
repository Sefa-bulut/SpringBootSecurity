package com.sefa.security.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sefa.security.entities.MyUser;

public interface MyUserRepository extends JpaRepository<MyUser, Long> { //MyUser entitimiz, Long ise bizim primary key tipimiz
	
	Optional<MyUser> findByUsername(String username);
	
}
