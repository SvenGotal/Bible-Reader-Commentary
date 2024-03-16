package com.java.crv.BibleReaderCommentary.domain;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;



@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Commentary {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) /* DB sama brine o generiranju ID-eva (AUTO) property */
	private Long id;
	
	private String subject;	
	private Boolean published;
	@Column(length = 2000)
	private String text;
	private String timestamp;
	private String author;

	@ManyToOne
	@JsonProperty("user")
	@JsonManagedReference
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "chapter_id")
	private Chapter chapter;

	public Commentary() {		
		
		LocalDateTime ldt = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		this.timestamp = ldt.format(format);
		
	}
	
	
	
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}


	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
			
	public Boolean getPublished() {
		return published;
	}

	public void setPublished(Boolean published) {
		this.published = published;
	}



	public Chapter getChapter() {
		return chapter;
	}



	public void setChapter(Chapter chapter) {
		this.chapter = chapter;
	}



	@Override
	public String toString() {
		return text + "\n\n" + user.getUsername() + " at: " + timestamp;
	}
	public String getAuthor() {
		return author;
	}


	public void setAuthor() {
		this.author = user.getUsername();
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
			
}
