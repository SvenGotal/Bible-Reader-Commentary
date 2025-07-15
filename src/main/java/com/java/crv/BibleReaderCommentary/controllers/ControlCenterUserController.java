package com.java.crv.BibleReaderCommentary.controllers;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java.crv.BibleReaderCommentary.domain.Commentary;
import com.java.crv.BibleReaderCommentary.domain.User;
import com.java.crv.BibleReaderCommentary.repositories.CommentaryRepository;
import com.java.crv.BibleReaderCommentary.repositories.UserRepository;

@Controller
public class ControlCenterUserController {
	private UserRepository userRepository;
	private CommentaryRepository commentaryRepository;

	
	public ControlCenterUserController(UserRepository userRepository, CommentaryRepository commentaryRepository) {
		this.userRepository = userRepository;
		this.commentaryRepository = commentaryRepository;
	}
	
	@GetMapping("/admin/usercontrol")
	public String getControlCenterUsersForm(Model model) {
		
		try {
			ArrayList<User> allUsersInADatabaseList = new ArrayList<User>();
			allUsersInADatabaseList = (ArrayList<User>) userRepository.findAll();
			
			model.addAttribute("allUsersInADatabaseList", allUsersInADatabaseList);
		}
		catch(NullPointerException e){
			e.printStackTrace();
		}
		
		return "forms/controlcenter_userspage";
	}
	
	@PostMapping("/admin/deleteUserFromDatabase")
	public String deleteUserFromDatabase (@RequestParam Long userId, Model model){

		User userToDelete = userRepository.findById(userId).get();
		/* Try deleting all comments before user can be removed from the database */

		if(userToDelete != null) {

			try {

				for(Commentary comment : userToDelete.getComments()) {
					commentaryRepository.deleteById(comment.getId());
				}

			}
			catch(NullPointerException e) {
				e.printStackTrace();
			}

			try {
				userRepository.deleteById(userId);
			}
			catch(NullPointerException e) {
				e.printStackTrace();
			}

		}
		else {
			String errorDeletingUser = "User deletion error!";
			model.addAttribute("errorDeletingUser", errorDeletingUser);
		}
		

		return "redirect:/admin/usercontrol";
	}

}
