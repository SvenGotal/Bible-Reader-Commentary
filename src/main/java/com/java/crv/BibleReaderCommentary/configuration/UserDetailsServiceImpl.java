package com.java.crv.BibleReaderCommentary.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
			/* loadUserByUsername is a custom implementation for the default Spring Boot login
			 * form. Without it, users logging in will be impossible. Custom implementation is
			 * required because of the specificity of the UserRepository connected to User model */
			User user = userRepo.findByUsername(username);
			if (user == null) {
				throw new UsernameNotFoundException("User: " + username + " not found...");
			}
			return org.springframework.security.core.userdetails.User.builder()
					.username(user.getUsername())
					.password(user.getPassword())
					.roles(user.getRole().name())
					.build();
	}	
	
	/**secConfig is a SecurityFilterChain for configuring security over the entire app.*/
	@Bean
	protected SecurityFilterChain secConfig(HttpSecurity http) throws Exception{
		
		/* secConfig configures which URL's are accessible to the public. URLs like index, 
		 * /public/** etc are accessible to everyone. Administrative URLs will be inaccessible by default 
		 * to unregistered users. For good measure access to H2 browser console and /admin/** is denied */

		http
		.authorizeHttpRequests((requests) -> requests
			.requestMatchers("/", "/login", "/public/**","/scripts/**","/images/**","/styles/**","/templates/**").permitAll()
			.requestMatchers("/h2-console/**", "/admin/**").hasAnyRole(UserRoles.OWNER.name(),UserRoles.ADMIN.name())
			.requestMatchers("/private/**").hasAnyRole("USER", "ADMIN", "OWNER")
			.anyRequest().authenticated())
			.formLogin(login -> login
				.loginPage("/login")
				.permitAll())
			.logout(logout -> logout
					.logoutSuccessUrl("/")
					.permitAll()
			)
			.csrf( csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
					.headers( headers -> headers.frameOptions(frame -> frame.disable())
			);
		return http.build();
	}
	
	/**Password Encoder bean is used during user creation and in future will be used for password reset
	 * in userspace form where a user will be able to configure their account.*/
	@Bean(name="passwordEncoder")
	protected BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
}
