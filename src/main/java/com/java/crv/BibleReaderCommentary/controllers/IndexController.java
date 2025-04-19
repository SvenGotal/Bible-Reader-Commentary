package com.java.crv.BibleReaderCommentary.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


import com.java.crv.BibleReaderCommentary.domain.User;
import com.java.crv.BibleReaderCommentary.domain.UserRoles;
import com.java.crv.BibleReaderCommentary.repositories.BookRepository;
import com.java.crv.BibleReaderCommentary.repositories.CommentaryRepository;
import com.java.crv.BibleReaderCommentary.repositories.UserRepository;



@Controller
@RequestMapping("/")
public class IndexController {
		
	private UserRepository userRepository;
	private BookRepository bookRepository;
	
	public IndexController(CommentaryRepository comments, 
			UserRepository userRepository, 
			BookRepository bookRepository
			) 
	{
		this.userRepository = userRepository;
		this.bookRepository = bookRepository;
	}
	
	@GetMapping("/")
	public String getIndex(@ModelAttribute("binding") String binding,
			Model model,
			Principal princ) 
	{	
		
		String currentUsername = "guest";
		Boolean userValidated = false;
		/* Take current logged user to adjust options visibility */
		if(princ != null) {
			if((currentUsername = princ.getName()) != null) {
				User currentUser = userRepository.findByUsername(currentUsername);	
				if(currentUser != null) {
					UserRoles currentUserRole = currentUser.getRole();
					userValidated = currentUserRole.name() != null ? true : false;
					model.addAttribute("userRole", currentUserRole.name());
					model.addAttribute("username", currentUser.getUsername());
				}
			}

		}
		else {
			model.addAttribute("userRole", currentUsername);	
			model.addAttribute("username", "guest");
		}
		model.addAttribute("userValidated", userValidated);
		model.addAttribute("books", bookRepository.findAll());
		
		return "index";
	}
}
