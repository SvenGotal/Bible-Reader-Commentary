package com.java.crv.BibleReaderCommentary.services;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;
import com.java.crv.BibleReaderCommentary.domain.Book;
import com.java.crv.BibleReaderCommentary.domain.Chapter;
import com.java.crv.BibleReaderCommentary.domain.Commentary;
import com.java.crv.BibleReaderCommentary.exceptions.BookNotFoundException;
import com.java.crv.BibleReaderCommentary.repositories.BookRepository;

@Service
public class BookService {

	private BookRepository bookRepository;
	private CommentaryService commentaryService;
	private ChapterService chapterService;
	
	public BookService(BookRepository bookRepository, CommentaryService commentaryService, ChapterService chapterService) {
		this.bookRepository = bookRepository;
		this.commentaryService = commentaryService;
		this.chapterService = chapterService;
	}
	/**
	 * Gets all the book objects in the BookRepository, unfiltered.
	 * @return List<Book>
	 * */
	public List<Book> getAllBookData(){
		return StreamSupport.stream(bookRepository.findAll().spliterator(), false).toList();
	}
	
	/**
	 * Returns the Book object from the repository by it's id. If not found throws Book [id] not found
	 * @return Book
	 * @throws BookNotFoundException*/
	public Book getBookById(Long id) {
		return bookRepository
				.findById(id)
				.orElseThrow(() -> new BookNotFoundException("Book with id: " + id + " not found..."));
	}
	
	/**
	 * Returns a List of books that contain published and private comments that
	 * belong to a specific user.
	 * @return List of books
	 * @param
	 */
	public List<Book> getBooksThatContainComments(Long userId){
		
		Set<Book> setOfBooksThatContainComments = new HashSet<Book>();		
		List<Chapter> allChaptersWithComments = chapterService.getAllChaptersThatContainComments().stream().filter( chapter -> !chapter.getComments().isEmpty()).toList();		
		allChaptersWithComments.forEach(chapter -> setOfBooksThatContainComments.add(chapter.getBook()));
		
		return setOfBooksThatContainComments.stream().collect(Collectors.toList());
		
		
	}
	
	
	
	
	
	
}







