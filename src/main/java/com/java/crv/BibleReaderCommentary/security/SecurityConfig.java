package com.java.crv.BibleReaderCommentary.security;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;

public class SecurityConfig extends WebSecurityConfiguration{

	private final UserDetailsService userDetailsService;
	
	public SecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	protected void configure(HttpSecurity http) throws Exception {
				
		http.authorizeRequests()
        .requestMatchers("/h2-console/**").permitAll()
        .anyRequest().authenticated();
		
		http.csrf().disable();
        http.headers().frameOptions().disable();
	}
		
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth, DataSource dataSource) throws Exception {
		auth.jdbcAuthentication()
		.dataSource(dataSource)
		.passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
