package com.java.crv.BibleReaderCommentary.controllers;

import java.util.ArrayList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java.crv.BibleReaderCommentary.domain.Commentary;
import com.java.crv.BibleReaderCommentary.domain.User;
import com.java.crv.BibleReaderCommentary.domain.UserRoles;
import com.java.crv.BibleReaderCommentary.exceptions.UserNotFoundException;
import com.java.crv.BibleReaderCommentary.repositories.CommentaryRepository;
import com.java.crv.BibleReaderCommentary.repositories.UserRepository;

@Controller
public class ControlCenterUserController {
	private UserRepository userService;
	private CommentaryRepository commentaryRepository;

	
	public ControlCenterUserController(UserRepository userService, CommentaryRepository commentaryRepository) {
		this.userService = userService;
		this.commentaryRepository = commentaryRepository;
	}
	
	@GetMapping("/admin/usercontrol")
	public String getControlCenterUsersForm(Model model) {
		
		try {
			ArrayList<User> allUsersInADatabaseList = new ArrayList<User>();
			allUsersInADatabaseList = (ArrayList<User>) userService.findAll();
			
			model.addAttribute("allUsersInADatabaseList", allUsersInADatabaseList);
		}
		catch(NullPointerException e){
			e.printStackTrace();
		}
		
		return "forms/controlcenter_userspage";
	}
	
	@PostMapping("/admin/deleteUserFromDatabase")
	public String deleteUserFromDatabase (@RequestParam Long userId, Model model){

		String errorDeletingUser = "User deletion error!";
		String errorDeletingAdminMessage = "Unable to delete Admin.";
		String errorDeletingUserComments = "Comments could not be deleted.";
		
		User userToDelete = userService.findById(userId).get();
		
		if(userToDelete.getRole() == UserRoles.ADMIN || userToDelete.getRole() == UserRoles.OWNER) {
			
			model.addAttribute("errorDeletingAdminMessage", errorDeletingAdminMessage);
			return "redirect:/admin/usercontrol";
		}
		/* Try deleting all comments before user can be removed from the database */

		if(userToDelete != null) {

			try {

				for(Commentary comment : userToDelete.getComments()) {
					commentaryRepository.deleteById(comment.getId());
				}

			}
			catch(NullPointerException e) {
				e.printStackTrace();
				model.addAttribute("errorDeletingUserComments", errorDeletingUserComments);
				return "redirect:/admin/usercontrol";
			}

			try {
				userService.deleteById(userId);
			}
			catch(UserNotFoundException e) {
				e.printStackTrace();
				model.addAttribute("errorDeletingUser", "User does not exist in the database...");
				return "redirect:/admin/UserControl";
			}
			catch(NullPointerException e) {
				e.printStackTrace();
				model.addAttribute("errorDeletingUser", errorDeletingUser);
				return "redirect:/admin/usercontrol";
			}

		}		
		return "redirect:/admin/usercontrol";
	}

}
