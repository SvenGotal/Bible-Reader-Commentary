package com.java.crv.BibleReaderCommentary.services;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.java.crv.BibleReaderCommentary.domain.AnnouncementMessage;
import com.java.crv.BibleReaderCommentary.exceptions.AnnouncementMessageNotFoundException;
import com.java.crv.BibleReaderCommentary.repositories.AnnouncementMessageRepository;

@Service
public class ServerAnnouncementService {

	private AnnouncementMessageRepository announcementRepository;
	
	public ServerAnnouncementService(AnnouncementMessageRepository announcementRepository) {
		this.announcementRepository = announcementRepository;
	}
	
	public List<AnnouncementMessage> getAllServerAnnouncementMessages(){
		return StreamSupport
				.stream(announcementRepository.findAll().spliterator(), false)
				.toList();
	}
	
	public List<AnnouncementMessage> getAllActiveServerAnnouncementMessages(){
		return StreamSupport
				.stream(announcementRepository.findAll().spliterator(), false)
				.filter((AnnouncementMessage msg) ->Boolean.TRUE.equals(msg.getAnnouncementIsActive()))
				.toList();
	}
	
	public AnnouncementMessage getAnnouncementMessageById(Long id) {
		return announcementRepository.findById(id).orElseThrow(() -> new AnnouncementMessageNotFoundException("Announcement not found: " + id));
	}
	
	public void saveAnnouncementMessage(AnnouncementMessage msg) {
		announcementRepository.save(msg);
	}
	
	public void deleteAnnouncementMessage(Long id) {
		announcementRepository.deleteById(id);
	}
	
}
