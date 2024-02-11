package com.java.crv.BibleReaderCommentary.controllers;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.java.crv.BibleReaderCommentary.domain.Commentary;
import com.java.crv.BibleReaderCommentary.domain.User;
import com.java.crv.BibleReaderCommentary.repositories.CommentaryRepository;
import com.java.crv.BibleReaderCommentary.repositories.UserRepository;

@Controller
public class UserSpaceController {
	
	private CommentaryRepository commentaryRepository;
	private UserRepository userRepository;
	
	public UserSpaceController(CommentaryRepository commentaryRepository, UserRepository userRepository) {
		this.commentaryRepository = commentaryRepository; 
		this.userRepository = userRepository;
	}
	
	@GetMapping("/private/myComments")
	public String getMyCommentsForm(Model model, Principal princ) {
		
		String username = princ.getName();
		User loggedUser = userRepository.findByUsername(username);
		
		ArrayList<Commentary> comments = (ArrayList<Commentary>) commentaryRepository.findAllByUserId(loggedUser.getId());
		
		model.addAttribute("comments", comments);
		
		return "forms/mycomments";
	}
}
