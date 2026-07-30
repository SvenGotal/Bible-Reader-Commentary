package com.java.crv.BibleReaderCommentary.domainDTO;

public class CommentaryDTO {

	
	private Long id;
	private String subject;
	private Boolean published;
	private String text;
	private String dateOfCreation;
	private String author;
	
	private Long user;
	private Long chapter;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Boolean getPublished() {
		return published;
	}
	public void setPublished(Boolean published) {
		this.published = published;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getDateOfCreation() {
		return dateOfCreation;
	}
	public void setDateOfCreation(String dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Long getUser() {
		return user;
	}
	public void setUserId(Long userId) {
		this.user = userId;
	}
	public Long getChapter() {
		return chapter;
	}
	public void setChapterId(Long chapterId) {
		this.chapter = chapterId;
	}
	
}
