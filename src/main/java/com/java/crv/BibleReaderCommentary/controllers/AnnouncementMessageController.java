package com.java.crv.BibleReaderCommentary.controllers;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.crv.BibleReaderCommentary.domain.AnnouncementMessage;
import com.java.crv.BibleReaderCommentary.repositories.AnnouncementMessageRepository;

@RestController
public class AnnouncementMessageController {
	
	private AnnouncementMessageRepository announcementMessageRepository;
	
	public AnnouncementMessageController(AnnouncementMessageRepository amr) {
		this.announcementMessageRepository = amr;
	}
	
	@PostMapping("/admin/announcementMessage/add")
	public String addNewAnnouncementMessage(@ModelAttribute("announcementMessage") AnnouncementMessage announcementMessage, Model model) {
		
		String successMessage = "Announcement added...";
		String errorMessage = "Error adding announcement...";
		
		try {
			AnnouncementMessage messageToAdd = announcementMessage;
			announcementMessageRepository.save(messageToAdd);
			model.addAttribute("successMessage", successMessage);
		}
		catch(NullPointerException e) {
			e.printStackTrace();
			model.addAttribute("successMessage", errorMessage);
			return "redirect:/admin/control";
		}
		
		
		return "redirect:/admin/control";
	}
	
	//GetMapping("
}
