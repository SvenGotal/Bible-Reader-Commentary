package com.java.crv.BibleReaderCommentary.advices;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.java.crv.BibleReaderCommentary.repositories.CommentaryRepository;

@ControllerAdvice
public class AllCommentsInTheDatabaseAdvice {
	
	private CommentaryRepository commentaryRepository;
	
	public AllCommentsInTheDatabaseAdvice(CommentaryRepository commentaryRepository) {
		this.commentaryRepository = commentaryRepository;
	}
	
	@ModelAttribute
	public void displayAllWrittenComments(Model model) {
		
		try {
			Long numberOfWrittenCommentsInTheDatabase = commentaryRepository.count();
			model.addAttribute("numberOfWrittenCommentsInTheDatabase", numberOfWrittenCommentsInTheDatabase);
		}
		catch(NullPointerException e) {
			e.printStackTrace();
		}
		
	}

}
