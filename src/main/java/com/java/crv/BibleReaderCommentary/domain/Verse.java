package com.java.crv.BibleReaderCommentary.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Verse {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private int number;
	/* The Book of Esther8:9 is the longest verse by character count up to 487 chars including white space.
	 * Added at least 500 to make sure that the verse will fit into the persistence. */
	@Column(length = 500)
	private String text;
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "chapter_id")
	private Chapter chapter;
	
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
	public Chapter getChapter() {
		return chapter;
	}
	public void setChapter(Chapter chapter) {
		this.chapter = chapter;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
	
}
