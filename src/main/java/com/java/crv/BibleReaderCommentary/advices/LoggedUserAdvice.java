package com.java.crv.BibleReaderCommentary.advices;

import java.security.Principal;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.java.crv.BibleReaderCommentary.domain.User;
import com.java.crv.BibleReaderCommentary.domain.UserRoles;
import com.java.crv.BibleReaderCommentary.repositories.UserRepository;

@ControllerAdvice
public class LoggedUserAdvice {
	
	private UserRepository userRepository;
	
	public LoggedUserAdvice(UserRepository userRepository) {
		this.userRepository = userRepository; 
	}

	@ModelAttribute
	public void displayLoggedUser(Model model, Principal principal) {
		
		try {
			if(principal != null) {
				User currentlyLoggedUser = userRepository.findByUsername(principal.getName());
				model.addAttribute("currentlyLoggedUser", currentlyLoggedUser);
			}

			
		}
		catch(NullPointerException e) {
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	
}
