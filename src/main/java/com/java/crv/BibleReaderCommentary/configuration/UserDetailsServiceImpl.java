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
		
		/*SecurityFilterChain - configures which URL's are accessible and to whom. Users are discriminated against their roles and allowed
		 * access only to certain portions of the web app*/
		
		//todo allow URL's that fetch book, chapter and verses in index
		
		http.authorizeHttpRequests((requests) -> 
		requests
			.requestMatchers("/","/public/**","/scripts/ajax_script.js","/images/**").permitAll()
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
	
	@Bean
	protected SecurityFilterChain loginConfig(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests( (requests) -> 
		requests
		.requestMatchers("/login").permitAll()
		.anyRequest().authenticated()
		).formLogin(Customizer.withDefaults());
		
		return http.build();
	}
	
	@Bean
	protected SecurityFilterChain logoutConfig(HttpSecurity http) throws Exception{
		
		http.authorizeHttpRequests( (requests) -> 
		requests
		.requestMatchers("logout").permitAll()
		.anyRequest().authenticated()
		).logout(Customizer.withDefaults());
		
		return http.build();
	}
	
	@Bean
	protected SecurityFilterChain usersConfig(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests( (requests) ->
		requests.requestMatchers("/private/**")
		.hasAnyRole(UserRoles.USER.name(), UserRoles.ADMIN.name(), UserRoles.OWNER.name())
		.anyRequest()
		.authenticated());
		
		return http.build();
	
	}
	
	@Bean(name="encoder")
	protected BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
