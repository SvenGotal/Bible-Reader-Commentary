package com.java.crv.BibleReaderCommentary.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.java.crv.BibleReaderCommentary.domain.User;
import com.java.crv.BibleReaderCommentary.domain.UserRoles;
import com.java.crv.BibleReaderCommentary.services.BookService;
import com.java.crv.BibleReaderCommentary.services.ServerAnnouncementService;

@Controller
public class FormLoaderController {
	
	private BookService bookService;
	private ServerAnnouncementService serverAnnouncementService;
	
	public FormLoaderController (BookService bookService, ServerAnnouncementService serverAnnouncementService) {
		this.bookService = bookService;
		this.serverAnnouncementService = serverAnnouncementService;
	}
	
	@GetMapping("/")
	public String loadIndexPage(Model model, @ModelAttribute("currentlyLoggedUser") User currentUser) {
		
		boolean userValidated = true;
		
		if(currentUser == null) {
			
			currentUser = new User();
			currentUser.setId(0L).setRole(UserRoles.GUEST).setUsername("guest");					
			userValidated = false;
		}
		
		model.addAttribute("userValidated", userValidated);
		model.addAttribute("userRole", currentUser.getRole().name());	
		model.addAttribute("username", currentUser.getUsername());		
		model.addAttribute("books", bookService.getAllBookData());
		model.addAttribute("announcements", serverAnnouncementService.getAllActiveServerAnnouncementMessages());
		
		return "index";
	}
	
	@GetMapping("/private/myHomepage")
	public String loadUserHomepage() {
		return "forms/user_homepage.html";
	}

}
