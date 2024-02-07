package com.java.crv.BibleReaderCommentary.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java.crv.BibleReaderCommentary.repositories.CommentaryRepository;



@Controller
@RequestMapping("/")
public class IndexController {
		
	private CommentaryRepository comments;
	
	public IndexController(CommentaryRepository comments) {
		this.comments = comments;
	}
	
	@GetMapping
	public String getIndex(@ModelAttribute("binding") String binding, Model model) {	
		
		model.addAttribute("binding", binding);
		model.addAttribute("comments", comments.findAll());
		
		
		return "/index";
	}
	
	@GetMapping("/login")
	public String loginUser() {
		return "redirect:/";
	}
}
