package com.sefa.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sefa.security.entities.MyUser;
import com.sefa.security.repos.MyUserRepository;

@Service
public class MyUserDetailService implements UserDetailsService {

	@Autowired
	private MyUserRepository myUserRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<MyUser> user = myUserRepository.findByUsername(username);
		if(user.isPresent()) {
			var userObj = user.get();
			UserDetails userDetail = User.builder()
					.username(userObj.getUsername())
					.password(userObj.getPassword())
					.roles(getRoles(userObj))
					.build();
			return userDetail;
		} else {
			throw new UsernameNotFoundException(username);
		}
	}

	private String[] getRoles(MyUser user) {
		if(user.getRole() == null) {
			return new String[] {"USER"};
		}
		return user.getRole().split(",");
	}

}
