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
	public void handleBookNotFound(BookNotFoundException e, Model model){
		model.addAttribute("ErrorMsg", e.getMessage());
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public void handleUserNotFound(UserNotFoundException e, Model model){
		model.addAttribute("ErrorMsg", e.getMessage());
	}
	
	@ExceptionHandler(AnnouncementMessageNotFoundException.class)
	public void handleAnnouncementMessageNotFound(AnnouncementMessageNotFoundException e, Model model) {
		model.addAttribute("ErrorMsg", e.getMessage());
	}
	
	@ExceptionHandler(CommentaryNotFoundException.class)
	public void handleCommentaryNotFound(CommentaryNotFoundException e, Model model) {
		model.addAttribute("ErrorMsg", e.getMessage());
	}
	
}
