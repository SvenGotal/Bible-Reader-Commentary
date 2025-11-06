package com.java.crv.BibleReaderCommentary.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.java.crv.BibleReaderCommentary.domain.AnnouncementMessage;
import com.java.crv.BibleReaderCommentary.domain.Commentary;
import com.java.crv.BibleReaderCommentary.domain.User;
import com.java.crv.BibleReaderCommentary.domain.UserRoles;
import com.java.crv.BibleReaderCommentary.services.BookService;
import com.java.crv.BibleReaderCommentary.services.CommentaryService;
import com.java.crv.BibleReaderCommentary.services.ServerAnnouncementService;

@Controller
public class FormLoaderController {
	
	private BookService bookService;
	private CommentaryService commentaryService;
	private ServerAnnouncementService serverAnnouncementService;
	
	public FormLoaderController (BookService bookService, CommentaryService commentaryService, ServerAnnouncementService serverAnnouncementService) {
		this.bookService = bookService;
		this.commentaryService = commentaryService;
		this.serverAnnouncementService = serverAnnouncementService;
	}
	
	
	/**
	 * Index loader
	 * */
	@GetMapping("/")
	public String loadIndexPage(Model model) {				
	
		model.addAttribute("books", bookService.getAllBookData());
		model.addAttribute("announcements", serverAnnouncementService.getAllActiveServerAnnouncementMessages());
		
		return "index";
	}
	
	/**
	 * User creation form loader
	 * */
	@GetMapping("public/submitForm")
	public String loadUserCreationForm(Model model, @ModelAttribute("currentlyLoggedUser") User currentlyLoggedUser) {
		if(currentlyLoggedUser != null) {
			if(currentlyLoggedUser.getRole() != null) {
				model.addAttribute("adminRole", currentlyLoggedUser.getRole().name());
			}
			else {
				model.addAttribute("adminRole", "GUEST");
			}
		}
		
		model.addAttribute("user", new User());
		return "forms/submitform";
	}
	
	/**
	 * User's comments page loader
	 * */
	@GetMapping("/private/myComments")
	public String getMyCommentsForm(Model model, @ModelAttribute("currentlyLoggedUser") User currentlyLoggedUser) {
					
		if(currentlyLoggedUser == null || currentlyLoggedUser.getId() == null) {
			model.addAttribute("serverMessage", "Something went wrong, user is null...");
			return "redirect/mycomments";
		}
		
		UserRoles userRole = currentlyLoggedUser.getRole();
		Long userId = currentlyLoggedUser.getId();
		
		model.addAttribute("userRole", userRole.name()); /*Send user role e.g. ADMIN, USER etc...*/

		List<Commentary> usersRepoComments = commentaryService.getUsersCommentaryById(userId);
		
		if(usersRepoComments.isEmpty()) {
			model.addAttribute("noCommentsFound", "Trenutno nemate ni jedan komentar!");
		}
		else {
			model.addAttribute("comments", usersRepoComments);
		}	
		model.addAttribute("comment", new Commentary());
		
		return "forms/mycomments";
	}
	
	/**
	 * Control Center page loader
	 * */
	@GetMapping("/admin/controlCenter")
	public String getCCForm(Model model) {
		
		model.addAttribute("newAnnouncementMessage", new AnnouncementMessage());
		return "forms/controlcenter";
		
	}
	
	/**
	 * User Homepage loader
	 * */
	@GetMapping("/private/myHomepage")
	public String loadUserHomepage() {
		return "forms/user_homepage.html";
	}
	
	/**
	 * Login form loader
	 * */
	@GetMapping("/login")
	public String loginUser() {
		return "forms/login";
	}
	
	/**
	 * Logout form loader
	 * */
	@GetMapping("/logout")
	public String logoutUser() {
		return "forms/logout";
	}	

}
