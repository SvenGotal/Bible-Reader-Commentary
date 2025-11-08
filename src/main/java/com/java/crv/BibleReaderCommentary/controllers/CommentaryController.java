package com.java.crv.BibleReaderCommentary.controllers;

import java.security.Principal;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.java.crv.BibleReaderCommentary.domain.Commentary;
import com.java.crv.BibleReaderCommentary.domain.User;
import com.java.crv.BibleReaderCommentary.services.CommentaryService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CommentaryController {
	private CommentaryService commentaryService;

	
	public CommentaryController(CommentaryService commentaryService) {
		this.commentaryService = commentaryService;
	}
	
	@GetMapping("/public/comment/{id}")
	public String getSharedCommentForm(@PathVariable Long id, Model model) {
		
		String errorCannotFindCommentMessage = "Komentar nije pronađen ili ne postoji.";
		
		try {
			Commentary commentToShare = commentaryService.getCommentaryById(id);
			
			if(commentToShare != null) {
				model.addAttribute("commentToShare", commentToShare);
			}
			else {
				model.addAttribute("errorCannotFindCommentMessage", errorCannotFindCommentMessage);
			}
			
		}
		catch (NullPointerException e) {
			e.printStackTrace();
		}
		catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		
		
		return "forms/sharedcommentform";
	}
	
	@PostMapping("/private/myCommentsDelete")
	public String deleteMyComment(@RequestParam("commentId") Long commentIdDelete) {
		
		commentaryService.deleteCommentaryById(commentIdDelete);			

		return "redirect:/private/myComments";
	}
	
	@PostMapping("/private/myCommentMakePublicOrPrivate")
	public String makeMyCommentPublicOrPrivate(@RequestParam("commentId") Long commentIdDelete, @RequestParam("setPrivateCheckbox") Boolean commentBoolean) {
		
		Commentary comment = commentaryService.getCommentaryById(commentIdDelete);
		comment.setPublished(commentBoolean);
		commentaryService.saveCommentary(comment);
		
		return "redirect:/private/myComments";
	}
	
	@PostMapping("/private/submitComment/post")
	public String addComment(@ModelAttribute("comment") Commentary submittedComment, @ModelAttribute("currentlyLoggedUser") User currentlyLoggedUser, Model model) {

		if(currentlyLoggedUser == null) {
			model.addAttribute("ErrorMsg", "Failed to submit comment. User is null, currently logged user binding error...");
			return "redirect:errors/errorView";
		}
		
		if(submittedComment == null) {
			model.addAttribute("ErrorMsg", "Failed to submit comment. Thymeleaf binding error, controller received null Commentary...");
			return "redirect:errors/errorView";
		}
		
		boolean isPublished = submittedComment.getPublished();
		if(isPublished || !isPublished) {
			submittedComment.setPublished(!isPublished);
		}
		
		submittedComment.setAuthor(currentlyLoggedUser.getUsername());
		submittedComment.setUser(currentlyLoggedUser);
		
		commentaryService.saveCommentary(submittedComment);		
		
		return "redirect:/";

	}

	
	/* REDO WHEN TIME AVAILBALE */
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
			
			databaseComment = commentaryService.getCommentaryById(commentId);
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
			
			commentaryService.saveCommentary(databaseComment);
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
			
			commentaryService.deleteCommentaryById(commentId);
			
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
