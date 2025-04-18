package com.java.crv.BibleReaderCommentary.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.crv.BibleReaderCommentary.domain.Book;
import com.java.crv.BibleReaderCommentary.domain.Chapter;
import com.java.crv.BibleReaderCommentary.domain.Commentary;
import com.java.crv.BibleReaderCommentary.domain.Verse;
import com.java.crv.BibleReaderCommentary.repositories.BookRepository;
import com.java.crv.BibleReaderCommentary.repositories.CommentaryRepository;

@Controller
public class ApiController {
	
	private BookRepository bookRepository;
	public ApiController (
			BookRepository bookRepository,
			CommentaryRepository commentRepository) 
	{
		this.bookRepository = bookRepository;
	}
	
	@GetMapping({"/submitComment/fetchChapters", "/public/fetchChapters"})
    @ResponseBody
    public List<Chapter> fetchChapters(@RequestParam Long bookId) {
        
		Optional<Book> bk = bookRepository.findById(bookId);
		ArrayList<Chapter> chapters;
		
		if(bk.isPresent()) {
			List<Chapter> list = bk.get().getChapters(); 
			chapters = new ArrayList<Chapter>();
			
			for(Chapter ch : list) {
				
				Chapter chapt = new Chapter();
				chapt.setId(ch.getId());
				chapt.setNumber(ch.getNumber());
				chapters.add(chapt);
			}
			
			
			return chapters;
		}
		else       
			return Collections.emptyList();
    }
	
	@GetMapping("/public/fetchVerses")
	@ResponseBody
	public List<Verse> fetchVerses(@RequestParam Long bookId, @RequestParam int chapterNumber){
		
		/* Find Book by id */
		Optional<Book> bk = bookRepository.findById(bookId);
		Chapter selectedChapter = new Chapter();
		
		if(bk.isPresent()) {
			List<Chapter> chpt = bk.get().getChapters();
			
			/* Find Chapter by it's number */
			for(Chapter ch : chpt) {

				if(ch.getNumber() == chapterNumber) {
					selectedChapter = ch;
					break;
				}
			}	
			/* Fetch Verses from chapter into a list */
			List<Verse> foundVerses = selectedChapter.getVerses();
			return foundVerses;
		}		
		return Collections.emptyList();
	}
	
	@GetMapping("/public/fetchPublicComments")
	@ResponseBody
	public List<Commentary> fetchPublicComments(@RequestParam Long bookId, @RequestParam int chapterNumber){
		
		//todo separate only public and Book-Chapter related comments.
		
		try {
			
			Book selectedBook = bookRepository.findById(bookId).get();
			
			Chapter selectedChapter = selectedBook.getChapterByNumber(chapterNumber);
			
			List<Commentary> getComments = selectedChapter.getComments();
			ArrayList<Commentary> publicComments = new ArrayList<Commentary>();
			
			for(Commentary comment : getComments) {
				if(comment.getPublished()) {
					publicComments.add(comment);
				}
			}
			
			return publicComments;
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		return Collections.emptyList();
	}
	

}
