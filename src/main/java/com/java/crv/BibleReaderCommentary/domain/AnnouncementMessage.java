package com.java.crv.BibleReaderCommentary.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name= "`ANNOUNCEMENT`")
public class AnnouncementMessage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Lob
	private String announcementText;
	private Boolean announcementIsActive;
	
	public AnnouncementMessage(Long id, String announcementText) {
		this.id = id;
		this.announcementText = announcementText;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setAnnouncementIsActive(Boolean isActive) {
		this.announcementIsActive = isActive;
	}
	
	public void setAnnouncementText(String announcementText) {
		this.announcementText = announcementText;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public String getAnnouncementText() {
		return this.announcementText;
	}
	
	public Boolean getAnnouncementIsActive() {
		return this.announcementIsActive;
	}

}








