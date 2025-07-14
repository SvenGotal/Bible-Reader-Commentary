package com.java.crv.BibleReaderCommentary.advices;

import java.security.Principal;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.java.crv.BibleReaderCommentary.repositories.UserRepository;

@ControllerAdvice
public class AllRegisteredUsersAdvice {
	
	private UserRepository userRepository;
	
	public AllRegisteredUsersAdvice(UserRepository userRepository) {
		this.userRepository = userRepository; 
	}
	
	@ModelAttribute
	public void displayAllRegisteredUsers(Model model, Principal principal) {
		
		try {
			Long allProfilesInADatabase = userRepository.count();
			model.addAttribute("allProfilesInADatabase", allProfilesInADatabase);
			
		}
		catch(NullPointerException e) {
			
		}
		
	}
	
	
}




