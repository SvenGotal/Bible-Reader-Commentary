package com.java.crv.BibleReaderCommentary.services;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.java.crv.BibleReaderCommentary.domain.User;
import com.java.crv.BibleReaderCommentary.domain.UserRoles;
import com.java.crv.BibleReaderCommentary.exceptions.UserNotFoundException;
import com.java.crv.BibleReaderCommentary.repositories.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	/**
	 * Get all users from the database.
	 * @return List<User> with all users.
	 * */
	public List<User> getAllUsers(){
		return StreamSupport.stream(userRepository.findAll().spliterator(), false).toList(); 
	}
	
	/**
	 * Get only one User from the database, by the username.
	 * @return User
	 * */
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	/**
	 * Get only one User from the database, by the user's id.
	 * @return User
	 * */
	public User getUserById(Long id) {	
		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found: " + id));
	}
	
	/**
	 * Save user into the database.
	 * @param
	 * */
	public void saveUser(String username, String password, String email, UserRoles userRole) {
		
		if(!StringUtils.hasText(username)) throw new UserNotFoundException("Username is empty...");
		if(!StringUtils.hasText(password)) throw new UserNotFoundException("Password is empty...");
		if(!StringUtils.hasText(email)) throw new UserNotFoundException("Email is empty...");
		if(userRole == null) userRole = UserRoles.USER;
		
		if(userRepository.findByUsername(username.trim()) != null) throw new UserNotFoundException("User already exists...");
		
		userRepository.save(new User()
				.setUsername(username.trim())
				.setPassword(passwordEncoder.encode(password))
				.setEmail(email.trim())
				.setRole(userRole));
		
	}
	
}







