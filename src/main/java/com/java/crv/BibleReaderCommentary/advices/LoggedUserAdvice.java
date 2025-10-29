package com.java.crv.BibleReaderCommentary.advices;

import java.security.Principal;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.java.crv.BibleReaderCommentary.domain.User;
import com.java.crv.BibleReaderCommentary.services.UserService;

/**
 * Controller advice for handling global user logging.
 * */
@ControllerAdvice
public class LoggedUserAdvice {
	
	private UserService userService;
	
	public LoggedUserAdvice(UserService userService) {
		this.userService = userService; 
	}
	
	/**
	 * Checks for users if logged into the system, otherwise they are "guests".
	 * If a user is logged in, it sends the User to Model. 
	 * */
	@ModelAttribute
	public void displayLoggedUser(Model model, Principal principal) {
		
		try {
			if(principal != null) {
				User currentlyLoggedUser = userService.getUserByUsername(principal.getName());
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
