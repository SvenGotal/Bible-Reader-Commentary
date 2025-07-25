package com.java.crv.BibleReaderCommentary.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.java.crv.BibleReaderCommentary.domain.Commentary;
import com.java.crv.BibleReaderCommentary.repositories.CommentaryRepository;

@Controller
public class CommentaryController {
	private CommentaryRepository commentaryRepository;

	
	public CommentaryController(CommentaryRepository commentaryRepository) {
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
	
	@PostMapping("/private/myCommentsEdit")
	public String editExistingComment(@ModelAttribute("comment") Commentary comment, RedirectAttributes redirectAttributes) {
		
		Long commentId;
		Commentary editedComment;
		Commentary databaseComment;
		
		try {
			if(comment.getText().isEmpty()) {
				redirectAttributes.addFlashAttribute("message", "Error, comment failed to post...");
				return "redirect:/private/myComments";
			}
			
			commentId = comment.getId();
			if(commentId == null) {
				redirectAttributes.addFlashAttribute("message", "Error, comment id missing...");
				return "redirect:/private/myComments";
			}
			
			databaseComment = commentaryRepository.findById(commentId).get();
			editedComment = comment;
			
			if(databaseComment.getText() == null) {
				redirectAttributes.addFlashAttribute("message", "Error, comment not found in database...");
				return "redirect:/private/myComments";
			}
			
			Boolean commentSubjectsMatch = editedComment.getSubject().trim().equals( databaseComment.getSubject().trim());
			Boolean commentTextsMatch = editedComment.getText().trim().equals(databaseComment.getText().trim());
			
			if(!commentSubjectsMatch) {
				databaseComment.setSubject(editedComment.getSubject().trim());
			}
			
			if(!commentTextsMatch) {
				databaseComment.setText(editedComment.getText().trim());
			}
			databaseComment.setPublished(editedComment.getPublished());
			
			commentaryRepository.save(databaseComment);
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		
		return "redirect:/private/myComments";
	}
	
	@PostMapping("/private/myComments/deleteComment")
	public String deleteExistingComment(@RequestParam Long commentId, Model model, RedirectAttributes redirectAttributes) {
		
		try {
			if(commentId == null) {
				redirectAttributes.addFlashAttribute("message", "Error, comment id missing...");
				return "redirect:/private/myComments";
			}
			
			commentaryRepository.deleteById(commentId);
			
		}
		catch(NullPointerException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "Error, comment missing in database...");
			return "redirect:/private/myComments";
		}
		catch(Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "Unknown error...");
			return "redirect:/private/myComments";
		}
		return "redirect:/private/myComments";
	}
	
}
