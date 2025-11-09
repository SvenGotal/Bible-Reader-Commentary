package com.java.crv.BibleReaderCommentary.controllers;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.crv.BibleReaderCommentary.domain.Book;
import com.java.crv.BibleReaderCommentary.domain.User;
import com.java.crv.BibleReaderCommentary.services.BookService;

@Controller
public class BookController {
	
	private BookService bookService;
	
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}
	
	@GetMapping("/public/fetchAllBooks")
	@ResponseBody
	public List<Book> fetchAllBooks () {
		return (List<Book>) bookService.getAllBookData();
	}
	
	@GetMapping("/public/fetchCommentedBooks")
	@ResponseBody
	public List<Book> fetchCommentedBooks(@ModelAttribute("currentlyLoggedUser") User currentlyLoggedUser) {
				
		// TODO: when unsigned user searches only commented books, if book contains one private comment, it is shown in the select.
		return bookService.getBooksThatContainComments(currentlyLoggedUser.getId());
	}	
}
