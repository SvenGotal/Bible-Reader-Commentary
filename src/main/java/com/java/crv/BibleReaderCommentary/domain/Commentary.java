package com.java.crv.BibleReaderCommentary.domain;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;



@Entity
public class Commentary {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) /* DB sama brine o generiranju ID-eva (AUTO) property */
	private Long id;
	private String text;
	private String timestamp;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Commentary() {		
		
		LocalDateTime ldt = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		this.timestamp = ldt.format(format);				
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
	
	@Override
	public String toString() {
		return "Commentary [id=" + id + ", text=" + text + "]";
	}
	
			
}
