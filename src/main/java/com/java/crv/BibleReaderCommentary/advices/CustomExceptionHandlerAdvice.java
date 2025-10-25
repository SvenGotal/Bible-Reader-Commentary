package com.java.crv.BibleReaderCommentary.advices;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.java.crv.BibleReaderCommentary.exceptions.BookNotFoundException;

@ControllerAdvice
public class CustomExceptionHandlerAdvice {

	@ExceptionHandler(BookNotFoundException.class)
	public void handleBookNotFound(BookNotFoundException e, Model model){
		model.addAttribute("ErrorMsg", e.getMessage());
	}
}
