package com.java.crv.domain;

public class Commentary {

	private int id;
	private String text;
	private User commenter;
	private BookAndChapter bookAndChapter;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
