package com.java.crv.BibleReaderCommentary.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.java.crv.BibleReaderCommentary.domain.AnnouncementMessage;
import com.java.crv.BibleReaderCommentary.domain.User;
import com.java.crv.BibleReaderCommentary.services.BookService;
import com.java.crv.BibleReaderCommentary.services.ChapterService;
import com.java.crv.BibleReaderCommentary.services.ServerAnnouncementService;

@Controller
public class FormLoaderController {
	
	private BookService bookService;
	private ChapterService chapterService;
	private ServerAnnouncementService serverAnnouncementService;
	
	public FormLoaderController (BookService bookService, ChapterService chapterService, ServerAnnouncementService serverAnnouncementService) {
		this.bookService = bookService;
		this.chapterService = chapterService;
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
