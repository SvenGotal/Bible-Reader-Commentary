package com.java.crv.BibleReaderCommentary.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.crv.BibleReaderCommentary.domain.Book;
import com.java.crv.BibleReaderCommentary.domain.Chapter;
import com.java.crv.BibleReaderCommentary.domain.Commentary;
import com.java.crv.BibleReaderCommentary.domain.Verse;
import com.java.crv.BibleReaderCommentary.domain.User;
import com.java.crv.BibleReaderCommentary.repositories.BookRepository;
import com.java.crv.BibleReaderCommentary.repositories.ChapterRepository;
import com.java.crv.BibleReaderCommentary.repositories.CommentaryRepository;
import com.java.crv.BibleReaderCommentary.repositories.VerseRepository;

@Controller
public class ApiController {
	
	private BookRepository bookRepository;
	private CommentaryRepository commentaryRepository;
	private ChapterRepository chapterRepository;
	private VerseRepository verseRepository;
	
	public ApiController (BookRepository bookRepository, CommentaryRepository commentaryRepository, ChapterRepository chapterRepository, VerseRepository verseRepository) 
	{
		this.bookRepository = bookRepository;
		this.commentaryRepository = commentaryRepository;
		this.chapterRepository = chapterRepository;
		this.verseRepository = verseRepository;
	}
	
	@GetMapping({"/submitComment/fetchChapters", "/public/fetchChapters"})
    @ResponseBody
    public List<Chapter> fetchChapters(@RequestParam Long bookId, @RequestParam(required = false, defaultValue = "false") Boolean filterCommented) {
        
		try {
			
			if(filterCommented) {
				ArrayList<Chapter> listOfAllChapters = (ArrayList<Chapter>) chapterRepository.findByBookId(bookId);
				ArrayList<Chapter> listOfFilteredChapters = new ArrayList<Chapter>();
				listOfAllChapters.forEach( chapter -> {
					if(!chapter.getComments().isEmpty()) {
						
						final AtomicBoolean containsPublicComments = new AtomicBoolean(false);
						chapter.getComments().forEach(comment -> {
							if(comment.getPublished()) {
								containsPublicComments.set(true);
							}
						});	
						if(containsPublicComments.get()) {
							listOfFilteredChapters.add(chapter);
						}
					}
				});				
				
				return listOfFilteredChapters;
			}
			else {
				ArrayList<Chapter> listOfAllChaptersInSelectedBook = (ArrayList<Chapter>) chapterRepository.findByBookId(bookId);
				return listOfAllChaptersInSelectedBook;
			}
			
		}
		catch(NullPointerException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
		
    }
	
	@GetMapping("/public/fetchVerses")
	@ResponseBody
	public List<Verse> fetchIndexVerses(@RequestParam Long bookId, @RequestParam int chapterNumber){
		
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
	public List<Commentary> fetchPublicComments(
			@RequestParam Long bookId, 
			@RequestParam int chapterNumber,
			@ModelAttribute("currentlyLoggedUser") User currentlyLoggedUser){		
		
		try {
			
			Book selectedBook = bookRepository.findById(bookId).get();
			
			Chapter selectedChapter = selectedBook.getChapterByNumber(chapterNumber);
			
			List<Commentary> getComments = selectedChapter.getComments();
			ArrayList<Commentary> publicComments = new ArrayList<Commentary>();			
			
			for(Commentary comment : getComments) {
				if(comment.getPublished() || comment.getUser().getId() == currentlyLoggedUser.getId()) {
					publicComments.add(comment);
				}
			}
			
			return publicComments;
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		return Collections.emptyList();
	}
	
	@GetMapping("/public/fetchCommentedBooks")
	@ResponseBody
	public LinkedHashSet<Book> fetchCommentedBooks() {
		
		LinkedHashSet<Book> setOfFilteredBooks = new LinkedHashSet<Book>();
		
		try {
			ArrayList<Commentary> listOfAllComments = (ArrayList<Commentary>) commentaryRepository.findAll();
			listOfAllComments.forEach(comment -> {
				if(comment.getPublished()) {
					setOfFilteredBooks.add(comment.getChapter().getBook());
				}
				
			});
		}
		catch(NullPointerException e) {
			e.printStackTrace();
		}
		
		
		return setOfFilteredBooks;
	}
	
	@GetMapping("/public/fetchAllBooks")
	@ResponseBody
	public List<Book> fetchAllBooks () {
		return (List<Book>) bookRepository.findAll();
	}
	
	@GetMapping("/public/fetchFiltered/comments")
	@ResponseBody
	public List<Commentary> fetchFilteredComments(){
		return null;
	}
	
	@GetMapping("/public/fetchAllVerses")
	@ResponseBody
	public List<Verse> fetchFilteredVerses(@RequestParam Long chapterId){
		
		return verseRepository.findByChapterId(chapterId);
		
	}
	

}
