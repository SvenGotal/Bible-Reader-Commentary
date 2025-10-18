package com.java.crv.BibleReaderCommentary.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

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
	public List<Verse> fetchIndexVerses(@RequestParam Long chapterNumber){
		
		try {
			return chapterRepository.findById(chapterNumber).get().getVerses();
		}
		catch(NullPointerException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}
	
	@GetMapping("/public/fetchPublicComments")
	@ResponseBody
	public List<Commentary> fetchAllPublicAndUsersComments(
			@RequestParam Long chapterId,
			@ModelAttribute("currentlyLoggedUser") User currentlyLoggedUser){		
		
		try {
			
			Predicate<Commentary> isPublished = comment -> comment.getPublished();
			Predicate<Commentary> belongsToLoggedUser = comment -> comment.getUser().getId() == currentlyLoggedUser.getId();	

			
			return commentaryRepository.findAllByChapterId(chapterId)
					.stream()
					.filter(isPublished.or(belongsToLoggedUser))
					.sorted(Comparator.comparing(Commentary::getId).reversed())
					.toList();
					
		}
		catch(NullPointerException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
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
	
	@GetMapping("/public/fetchAllChapters")
	@ResponseBody
	public List<Chapter> fetchFilteredComments(@RequestParam Long chapterId){
		return chapterRepository.findAllById(chapterId);
	}
	
	@GetMapping("/public/fetchChapterVerses")
	@ResponseBody
	public List<Verse> fetchChapterVerses(@RequestParam Long chapterId){
		
		return verseRepository.findByChapterId(chapterId);
		
	}
	

}
