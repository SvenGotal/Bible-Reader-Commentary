package com.java.crv.BibleReaderCommentary.domain;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Commentary {

	@Id
	private Long id;
	private String text;
	private User commenter;
	private BookAndChapter bookAndChapter;
	
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
	public User getCommenter() {
		return commenter;
	}
	public void setCommenter(User commenter) {
		this.commenter = commenter;
	}
	public BookAndChapter getBookAndChapter() {
		return bookAndChapter;
	}
	public void setBookAndChapter(BookAndChapter bookAndChapter) {
		this.bookAndChapter = bookAndChapter;
	}
	@Override
	public String toString() {
		return bookAndChapter.getBook() + " " + bookAndChapter.getChapter() + "\n\n" + text + "\n\n" + commenter;
	}
	
	
	
}
