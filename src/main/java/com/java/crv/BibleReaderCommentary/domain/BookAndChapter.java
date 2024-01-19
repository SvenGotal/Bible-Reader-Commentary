package com.java.crv.BibleReaderCommentary.domain;






public class BookAndChapter {

	private Long id;
	
	private String Book;
	private int chapter;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBook() {
		return Book;
	}
	public void setBook(String book) {
		Book = book;
	}
	public int getChapter() {
		return chapter;
	}
	public void setChapter(int chapter) {
		this.chapter = chapter;
	}
	@Override
	public String toString() {
		return "BookAndChapter [Book=" + Book + ", chapter=" + chapter + "]";
	}
	
	
	
}
