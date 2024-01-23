package com.java.crv.BibleReaderCommentary.configuration;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.java.crv.BibleReaderCommentary.domain.User;
import com.java.crv.BibleReaderCommentary.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	private final UserRepository userRepo;
	
	public UserDetailsServiceImpl (UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepo.findByUsername(username);
		if (user == null) {
			//todo implement exception rather than output to console
			System.out.println("Username not found!");
		}
		
		return org.springframework.security.core.userdetails.User.builder()
				.username(user.getUsername())
				.password(user.getPassword())
				.roles("USER")
				.build();
	}

}
