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
import com.java.crv.BibleReaderCommentary.domain.UserRoles;
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
	public String getMyCommentsForm(
			Model model, 
			Principal princ,
			RedirectAttributes redirectAttributes) {
		
		/*Retireve the user name from the Principal and retrieve User by username in repo*/
		String username = princ.getName();
		User loggedUser = userRepository.findByUsername(username);
		
		/*Fill the ArrayList with comments, empty if there are no comments found*/
		ArrayList<Commentary> comments = (ArrayList<Commentary>) commentaryRepository.findAllByUserId(loggedUser.getId());
		
		/*User authentication for options visibility in the hamburger menu*/
		UserRoles currentUserRole = loggedUser.getRole();
		Boolean userValidated = loggedUser == null ? false : true;
		
		/*Send all the required data to the mycomments View*/
		model.addAttribute("userRole", currentUserRole.name()); /*Send user role e.g. ADMIN, USER etc...*/
		model.addAttribute("userValidated", userValidated); 	/*Send whether user is validated*/
		if(comments.isEmpty()) {
			model.addAttribute("noCommentsFound", "Trenutno nemate ni jedan komentar!");
		}
		/*In the event that user's comments were found send comments data*/
		else {
			model.addAttribute("comments", comments);
		}
		model.addAttribute("username", loggedUser.getUsername()); /*Send username*/
		
		model.addAttribute("comment", new Commentary());
		
		return "forms/mycomments";
	}
	
	//todo create PostMapping method, also fetch Commentary Id from displayed comments.
	/*@PostMapping("/private/myCommentsEdit")
	public String editMyComment(
			@RequestParam("commentId") Long commentId,
			@RequestParam("editedComment") String editedComment,
			@RequestParam(name="setPrivateCheckbox", required=false) String commentPublished,
			@RequestParam("commentSubject") String commentSubject,
			RedirectAttributes redirectAttributes)
	{
		
		try {
			Commentary comment = commentaryRepository.findById(commentId).get();
			
			comment.setText(editedComment.trim());
			comment.setSubject(commentSubject);
			
			if(commentPublished == null || commentPublished == "") {
				comment.setPublished(false);
			}
			else {
				comment.setPublished(true);
			}
			
			commentaryRepository.save(comment);
			redirectAttributes.addFlashAttribute("message", "Uspješno ste izmijenili svoj komentar!");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return "redirect:/private/myComments";
	}*/
	
	@PostMapping("/private/myCommentsDelete")
	public String deleteMyComment(
			@RequestParam("commentId") Long commentIdDelete, 
			RedirectAttributes redirectAttributes) 
	{
		try {
			
			commentaryRepository.deleteById(commentIdDelete);
			
		}
		catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
				
		return "redirect:/private/myComments";
	}
	
	@PostMapping("/private/myCommentMakePublicOrPrivate")
	public String makeMyCommentPublicOrPrivate(
			@RequestParam("commentId") Long commentIdDelete, 
			@RequestParam("setPrivateCheckbox") Boolean commentBoolean) 
	{
		
		try {
			Commentary comment = commentaryRepository.findById(commentIdDelete).get();
			comment.setPublished(commentBoolean);
			commentaryRepository.save(comment);
		}
		catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		return "redirect:/private/myComments";
	}
}
