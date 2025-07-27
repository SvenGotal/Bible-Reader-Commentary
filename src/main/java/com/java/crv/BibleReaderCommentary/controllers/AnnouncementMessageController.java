package com.java.crv.BibleReaderCommentary.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java.crv.BibleReaderCommentary.domain.AnnouncementMessage;
import com.java.crv.BibleReaderCommentary.repositories.AnnouncementMessageRepository;

@Controller
public class AnnouncementMessageController {
	
	private AnnouncementMessageRepository announcementMessageRepository;
	
	public AnnouncementMessageController(AnnouncementMessageRepository amr) {
		this.announcementMessageRepository = amr;
	}
	
	@PostMapping("/admin/announcementMessage/add")
	public String addNewAnnouncementMessage(@ModelAttribute("newAnnouncementMessage") AnnouncementMessage announcementMessage, Model model) {
		
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
			return "redirect:/admin/controlCenter";
		}
		
		
		return "redirect:/admin/controlCenter";
	}
	
	@PostMapping("/admin/AnnouncementMessage/delete")
	public String deleteAnnouncementMessageFromDatabase(@RequestParam Long announcementId) {
		
		try {
			announcementMessageRepository.deleteById(announcementId);
			return "redirect:/admin/controlCenter";
		}
		catch(NullPointerException e) {
			e.printStackTrace();
		}
		return "redirect:/admin/controlCenter";
	}
	
	@PostMapping("/admin/AnnouncementMessage/flipStatus")
	public String flipAnnouncementMessageActivityStatus(@RequestParam Long announcementId, Model model) {
				
		String errorMessageFailedToFindInDB = "system error: announcement does not exist in database...";
		
		try {
			AnnouncementMessage msg = announcementMessageRepository.findById(announcementId).get();
			if(msg.getAnnouncementIsActive()) {
				msg.setAnnouncementIsActive(false);
			}
			else {
				msg.setAnnouncementIsActive(true);
			}
			
			announcementMessageRepository.save(msg);
		}
		catch(NullPointerException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", errorMessageFailedToFindInDB);
			return "redirect:/admin/controlCenter";
		}
		
		return "redirect:/admin/controlCenter";
	}
	
}
