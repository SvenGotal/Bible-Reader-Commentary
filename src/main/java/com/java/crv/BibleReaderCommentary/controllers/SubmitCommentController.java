package com.java.crv.BibleReaderCommentary.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.java.crv.BibleReaderCommentary.domain.Commentary;
import com.java.crv.BibleReaderCommentary.repositories.BookRepository;
import com.java.crv.BibleReaderCommentary.repositories.CommentaryRepository;
import com.java.crv.BibleReaderCommentary.repositories.UserRepository;

@Controller
@RequestMapping("/submitComment")
public class SubmitCommentController {
	
	private CommentaryRepository commentaryRepository;
	private UserRepository userRepository;
	private BookRepository bookRepository;
	
	public SubmitCommentController(CommentaryRepository commentaryRepository, UserRepository userRepository, BookRepository bookRepository) {
		this.commentaryRepository = commentaryRepository;
		this.userRepository = userRepository;
		this.bookRepository = bookRepository;
	}
	
	
	@GetMapping
	public String getCommentForm(Model model) {
		model.addAttribute("comment", new Commentary());
		return "forms/submitcomment";
	}
	
	@PostMapping
	public String addComment(@ModelAttribute("comment") Commentary comment, BindingResult validation, RedirectAttributes redirectAttributes, Principal princ) {		
		
		if(validation.hasErrors()) {
			redirectAttributes.addFlashAttribute("binding", "Failed to insert Data!");		
			return "forms/submitComment";
		}
		System.out.println("Looking for user...");
		comment.setUser(userRepository.findByUsername(princ.getName()));
		if(comment.getUser()==null)
			System.out.println("User not found!");
		
		System.out.println("User: " + comment.getUser().getUsername() + " found!");
				
		
		commentaryRepository.save(comment);		
		redirectAttributes.addFlashAttribute("binding", "Data succesfully stored!");
		
		return "redirect:/";
	}		
}
