package com.java.crv.BibleReaderCommentary.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Chapter {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private int number;
	@ManyToOne
	@JoinColumn( name = "book_id")
	private Book book;
	@OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL)
	private List<Verse> verses;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public List<Verse> getVerses() {
		return verses;
	}
	public void setVerses(List<Verse> verses) {
		this.verses = verses;
	}
	
	
}
