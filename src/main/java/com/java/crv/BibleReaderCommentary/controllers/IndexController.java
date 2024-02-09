package com.java.crv.BibleReaderCommentary.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java.crv.BibleReaderCommentary.domain.User;
import com.java.crv.BibleReaderCommentary.repositories.CommentaryRepository;
import com.java.crv.BibleReaderCommentary.repositories.UserRepository;



@Controller
@RequestMapping("/")
public class IndexController {
		
	private CommentaryRepository comments;
	private UserRepository userRepository;
	
	public IndexController(CommentaryRepository comments, UserRepository userRepository) {
		this.comments = comments;
		this.userRepository = userRepository;
	}
	
	@GetMapping
	public String getIndex(@ModelAttribute("binding") String binding,
			Model model,
			Principal princ) 
	{	
		
		/* Take current logged user to adjust visibility */
		String currentUsername = princ.getName();
		User currentUser = userRepository.findByUsername(currentUsername);
		
		
		
		model.addAttribute("binding", binding);
		model.addAttribute("comments", comments.findAll());
		
		
		return "/index";
	}
	
	@GetMapping("/login")
	public String loginUser() {
		return "redirect:/";
	}
}
