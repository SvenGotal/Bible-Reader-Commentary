package com.java.crv.BibleReaderCommentary.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;

import com.java.crv.BibleReaderCommentary.domain.User;
import com.java.crv.BibleReaderCommentary.domain.UserRoles;
import com.java.crv.BibleReaderCommentary.repositories.UserRepository;

@Service
@Configuration
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
				.roles(user.getRole().name())
				.build();
	}
	
	@Bean
	protected SecurityFilterChain secConfig(HttpSecurity http) throws Exception{
		
		
		http.authorizeHttpRequests((requests) -> 
		requests
			.requestMatchers("/"/*,"/h2-console/**"*/).permitAll()
			.requestMatchers("/h2-console/**").hasAnyRole(UserRoles.OWNER.name(),UserRoles.ADMIN.name())
			.anyRequest()
			.authenticated())
				.formLogin(Customizer.withDefaults())
				.csrf( csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
				.headers( headers -> headers
						.frameOptions(frame -> frame.disable())
			);
		
		return http.build();
	}
	
	@Bean(name="encoder")
	protected BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
