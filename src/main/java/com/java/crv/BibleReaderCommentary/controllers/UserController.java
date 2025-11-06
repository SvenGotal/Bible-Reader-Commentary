package com.java.crv.BibleReaderCommentary.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.java.crv.BibleReaderCommentary.domain.User;
import com.java.crv.BibleReaderCommentary.services.UserService;

@Controller
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("public/submitForm")
	public String submitForm(@ModelAttribute("user") @Validated User newUser, BindingResult bindingResult, Model model) {
		
		if (bindingResult.hasErrors()) {		
			model.addAttribute("ErrorMsg", "binding error");
			return "errors/errorView";
		}					
		userService.saveUser(newUser.getUsername(), newUser.getPassword(), newUser.getEmail(), newUser.getRole());
			
		return "redirect:/";
	}
}
