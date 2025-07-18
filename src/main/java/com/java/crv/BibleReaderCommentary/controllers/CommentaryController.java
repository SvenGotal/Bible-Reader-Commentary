package com.java.crv.BibleReaderCommentary.controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.java.crv.BibleReaderCommentary.domain.Commentary;
import com.java.crv.BibleReaderCommentary.repositories.CommentaryRepository;
import com.java.crv.BibleReaderCommentary.repositories.UserRepository;

@Controller
public class CommentaryController {
	private UserRepository userRepository;
	private CommentaryRepository commentaryRepository;

	
	public CommentaryController(UserRepository userRepository, CommentaryRepository commentaryRepository) {
		this.userRepository = userRepository;
		this.commentaryRepository = commentaryRepository;
	}
	
	@GetMapping("public/comment/{id}")
	public String getSharedCommentForm(@PathVariable Long id, Model model) {
		
		String errorCannotFindCommentMessage = "Komentar nije pronađen ili ne postoji.";
		
		try {
			Commentary commentToShare = commentaryRepository.findById(id).get();
			
			if(commentToShare != null) {
				model.addAttribute("commentToShare", commentToShare);
			}
			else {
				model.addAttribute("errorCannotFindCommentMessage)", errorCannotFindCommentMessage);
			}
			
		}
		catch (NullPointerException e) {
			e.printStackTrace();
		}
		
		
		return "/forms/sharedcommentform";
	}
	
}
