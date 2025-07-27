package com.java.crv.BibleReaderCommentary.advices;

import java.util.ArrayList;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.java.crv.BibleReaderCommentary.domain.AnnouncementMessage;
import com.java.crv.BibleReaderCommentary.repositories.AnnouncementMessageRepository;

@ControllerAdvice
public class AnnouncementMessageAdvice {

	private AnnouncementMessageRepository announcementMessageRepository;
	
	public AnnouncementMessageAdvice(AnnouncementMessageRepository announcementMessageRepository) {
		this.announcementMessageRepository = announcementMessageRepository;
	}
	
	@ModelAttribute
	public void displayAllExistingAnnouncementMessages(Model model) {
		
		ArrayList<AnnouncementMessage> listOfAllAnnouncements = new ArrayList<AnnouncementMessage>();
		
		try {
			
			announcementMessageRepository.findAll().forEach(announcement -> listOfAllAnnouncements.add(announcement));
			model.addAttribute("listOfAllAnnouncements", listOfAllAnnouncements);
		}
		catch(NullPointerException e) {
			e.printStackTrace();
			AnnouncementMessage am = new AnnouncementMessage();
			am.setId(666l);
			am.setAnnouncementIsActive(false);
			am.setAnnouncementText("system error: error in announcement retrieval...");
			listOfAllAnnouncements.add(am);
			
		}
	}	
}
