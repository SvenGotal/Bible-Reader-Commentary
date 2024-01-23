package com.java.crv.BibleReaderCommentary.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java.crv.BibleReaderCommentary.domain.Commentary;
import com.java.crv.BibleReaderCommentary.repositories.CommentaryRepository;

@Controller
@RequestMapping("/submitComment")
public class SubmitCommentController {
	
	private CommentaryRepository commentaryRepository;
	
	public SubmitCommentController(CommentaryRepository commentaryRepository) {
		this.commentaryRepository = commentaryRepository;
	}
	
	@GetMapping
	public String getCommentForm(Model model) {
		model.addAttribute("comment", new Commentary());
		return "forms/submitcomment";
	}
	
	@PostMapping
	public String addComment(@ModelAttribute("comment") Commentary comment, BindingResult validation, Model model) {
		
		String binding;
		
		if(validation.hasErrors()) {
			binding = "Failed to insert data!";			
			return "forms/submitComment";
		}
		
		commentaryRepository.save(comment);
		binding = "Data succesfully stored!";
		
		model.addAttribute(binding);
		System.out.println(comment.toString());
		
		return "redirect:/";
	}
	
	
}
