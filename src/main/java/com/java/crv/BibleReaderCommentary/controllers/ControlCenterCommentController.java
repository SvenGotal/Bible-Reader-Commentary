package com.java.crv.BibleReaderCommentary.controllers;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java.crv.BibleReaderCommentary.domain.Commentary;
import com.java.crv.BibleReaderCommentary.repositories.CommentaryRepository;


@Controller
public class ControlCenterCommentController {
	private CommentaryRepository commentaryRepository;

	
	public ControlCenterCommentController(CommentaryRepository commentaryRepository) {

		this.commentaryRepository = commentaryRepository;
	}
	
	
	
	@PostMapping("/admin/deleteCommentFromDatabase")
	public String deleteCommentFromDatabase(@RequestParam Long commentId, Model model) {
		
		String errorDeletingCommentMessage = "Error encountered while deleting commentary";
		
		try {
			
			commentaryRepository.deleteById(commentId);
			
		}
		catch (NullPointerException e) {
			e.printStackTrace();
			model.addAttribute("errorDeletingCommentMessage", errorDeletingCommentMessage);
		}
		
		
		return "redirect:/admin/commentcontrol";
		
	}
}
