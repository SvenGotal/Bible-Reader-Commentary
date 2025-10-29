package com.java.crv.BibleReaderCommentary.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.java.crv.BibleReaderCommentary.domain.User;
import com.java.crv.BibleReaderCommentary.exceptions.UserNotFoundException;
import com.java.crv.BibleReaderCommentary.repositories.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public List<User> getAllUsers(){
		
		return StreamSupport.stream(userRepository.findAll().spliterator(), false).toList(); 
		
	}
	
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	public User getUserById(Long id) {
		
		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found: " + id));

	}
	
}







