package com.java.crv.BibleReaderCommentary.advices;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.java.crv.BibleReaderCommentary.exceptions.AnnouncementMessageNotFoundException;
import com.java.crv.BibleReaderCommentary.exceptions.BookNotFoundException;
import com.java.crv.BibleReaderCommentary.exceptions.CommentaryNotFoundException;
import com.java.crv.BibleReaderCommentary.exceptions.UserNotFoundException;

@ControllerAdvice
public class CustomExceptionHandlerAdvice {

	@ExceptionHandler(BookNotFoundException.class)
	public String handleBookNotFound(BookNotFoundException e, Model model){
		model.addAttribute("ErrorMsg", e.getMessage());
		return "errors/errorView";
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public String handleUserNotFound(UserNotFoundException e, Model model){
		model.addAttribute("ErrorMsg", e.getMessage());
		return "errors/errorView";
	}
	
	@ExceptionHandler(AnnouncementMessageNotFoundException.class)
	public String handleAnnouncementMessageNotFound(AnnouncementMessageNotFoundException e, Model model) {
		model.addAttribute("ErrorMsg", e.getMessage());
		return "errors/errorView";
	}
	
	@ExceptionHandler(CommentaryNotFoundException.class)
	public String handleCommentaryNotFound(CommentaryNotFoundException e, Model model) {
		model.addAttribute("ErrorMsg", e.getMessage());
		return "errors/errorView";
	}
	
}
