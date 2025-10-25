package com.java.crv.BibleReaderCommentary.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "bible_id")
	private Bible bible;
	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
	private List<Chapter> chapters;
	
	
	public Bible getBible() {
		return bible;
	}
	public Book setBible(Bible bible) {
		this.bible = bible;
		return this;
	}
	public Long getId() {
		return id;
	}
	public Book setId(Long id) {
		this.id = id;
		return this;
	}
	public String getName() {
		return name;
	}
	public Book setName(String name) {
		this.name = name;
		return this;
	}
	public List<Chapter> getChapters() {
		return chapters;
	}
	public Book setChapters(List<Chapter> chapters) {
		this.chapters = chapters;
		return this;
	}
	
	public Chapter getChapterByNumber(int number) {
		
		for(Chapter chapter : chapters) {				
			if(chapter.getNumber() == number)
				return chapter;
		}
		return null;
	}
}
