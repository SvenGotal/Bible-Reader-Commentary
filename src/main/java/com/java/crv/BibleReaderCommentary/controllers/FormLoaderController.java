package com.java.crv.BibleReaderCommentary.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
	public String loadIndexPage(Model model) {				
	
		model.addAttribute("books", bookService.getAllBookData());
		model.addAttribute("announcements", serverAnnouncementService.getAllActiveServerAnnouncementMessages());
		
		return "index";
	}
	
	@GetMapping("/private/myHomepage")
	public String loadUserHomepage() {
		return "forms/user_homepage.html";
	}

}
