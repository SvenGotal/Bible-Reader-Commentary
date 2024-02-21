package com.java.crv.BibleReaderCommentary.controllers;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	//todo create PostMapping method, also fetch Commentary Id from displayed comments.
	@PostMapping("/private/myCommentsEdit")
	public String editMyComment(
			@RequestParam("commentId") Long commentId,
			@RequestParam("editedComment") String editedComment,
			RedirectAttributes redirectAttributes
			)
	{
		
		try {
			Commentary comment = commentaryRepository.findById(commentId).get();
			
			comment.setText(editedComment);
			commentaryRepository.save(comment);
			redirectAttributes.addFlashAttribute("message", "Uspješno ste izmijenili svoj komentar!");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return "redirect:/private/myComments";
	}
	
	@PostMapping("/private/myCommentsDelete")
	public String deleteMyComment(@RequestParam("commentIdDelete") Long commentIdDelete, RedirectAttributes redirectAttributes) {
		
		try {
			
			commentaryRepository.deleteById(commentIdDelete);
			
		}
		catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
				
		return "redirect:/private/myComments";
	}
}
