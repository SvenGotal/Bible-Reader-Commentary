package com.java.crv.BibleReaderCommentary.domain;

import jakarta.persistence.Entity;

@Entity
public class BookAndChapter {

	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	private String Book;
	private int chapter;
	
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
