package com.java.crv.BibleReaderCommentary.services;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;
import com.java.crv.BibleReaderCommentary.domain.Book;
import com.java.crv.BibleReaderCommentary.domain.Commentary;
import com.java.crv.BibleReaderCommentary.exceptions.BookNotFoundException;
import com.java.crv.BibleReaderCommentary.repositories.BookRepository;

@Service
public class BookService {

	private BookRepository bookRepository;
	private CommentaryService commentaryService;
	
	public BookService(BookRepository bookRepository, CommentaryService commentaryService) {
		this.bookRepository = bookRepository;
		this.commentaryService = commentaryService;
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
	 * Returns a unique set of books that contain published comments and returns private comments that
	 * belong to a specific user.
	 * @return LikedHashSet<Book>
	 */
	public Set<Book> getBooksThatContainComments(Long userId){
		Set<Book> setOfFilteredBooks = new LinkedHashSet<Book>();
		
		List<Commentary> listOfAllPublishedComments = commentaryService
				.getFilteredCommentary(comment -> Boolean.TRUE.equals(comment.getPublished()),  comment -> comment.getUser().getId().equals(userId));
		
		listOfAllPublishedComments.forEach(comment -> {
			setOfFilteredBooks.add(comment.getChapter().getBook());
		});
		
		return setOfFilteredBooks;				
	}
	
	
	
	
	
	
}







