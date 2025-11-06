package com.java.crv.BibleReaderCommentary.services;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.java.crv.BibleReaderCommentary.domain.User;
import com.java.crv.BibleReaderCommentary.domain.UserRoles;
import com.java.crv.BibleReaderCommentary.exceptions.UserAlreadyExistsException;
import com.java.crv.BibleReaderCommentary.exceptions.UserNotFoundException;
import com.java.crv.BibleReaderCommentary.exceptions.UserParameterValidationException;
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
	public User saveUser(String username, String password, String email, UserRoles userRole) {
		
		if(!StringUtils.hasText(username)) throw new UserParameterValidationException("Username is empty...");
		if(!StringUtils.hasText(password)) throw new UserParameterValidationException("Password is empty...");
		if(!StringUtils.hasText(email)) throw new UserParameterValidationException("Email is empty...");
		if(userRole == null) userRole = UserRoles.USER; // default user's role to USER
		
		String newUsername = username.trim();
		String newPassword = passwordEncoder.encode(password);
		String newEmail = email.trim().toLowerCase();
		
		/* findByUsername returns User instead of Optional */
		if(userRepository.findByUsername(newUsername) != null) throw new UserAlreadyExistsException("User already exists...");
		
		User newUser = new User().setUsername(newUsername).setPassword(newPassword).setEmail(newEmail).setRole(userRole);
		
		return userRepository.save(newUser);						 
	}
	
}







