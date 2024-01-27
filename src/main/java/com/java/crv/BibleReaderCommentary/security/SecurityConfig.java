package com.java.crv.BibleReaderCommentary.security;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfiguration{

	private final UserDetailsService userDetailsService;
	@Autowired
	@Qualifier("encoder")
	private BCryptPasswordEncoder passwordEncoder;
	
	public SecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	protected void configure(HttpSecurity http) throws Exception {
				
		http.authorizeHttpRequests((authorizeHttpRequests) ->
			authorizeHttpRequests.requestMatchers("/h2-console/**"));		
	}
		
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth, DataSource dataSource) throws Exception {
		auth.jdbcAuthentication()
		.dataSource(dataSource)
		.passwordEncoder(passwordEncoder);
	}
	
}
