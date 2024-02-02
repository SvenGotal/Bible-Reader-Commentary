package com.java.crv.BibleReaderCommentary.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.java.crv.BibleReaderCommentary.domain.Book;
import com.java.crv.BibleReaderCommentary.domain.Chapter;
import com.java.crv.BibleReaderCommentary.domain.Commentary;
import com.java.crv.BibleReaderCommentary.repositories.BookRepository;
import com.java.crv.BibleReaderCommentary.repositories.ChapterRepository;
import com.java.crv.BibleReaderCommentary.repositories.CommentaryRepository;
import com.java.crv.BibleReaderCommentary.repositories.UserRepository;

@Controller
@RequestMapping("/submitComment")
public class SubmitCommentController {
	
	private CommentaryRepository commentaryRepository;
	private UserRepository userRepository;
	private BookRepository bookRepository;
	private ChapterRepository chapterRepository;
	
	public SubmitCommentController(
			CommentaryRepository commentaryRepository, 
			UserRepository userRepository, 
			BookRepository bookRepository, 
			ChapterRepository chapterRepository) 
	{
		this.commentaryRepository = commentaryRepository;
		this.userRepository = userRepository;
		this.bookRepository = bookRepository;
		this.chapterRepository = chapterRepository;
	}
	
	
	
	@GetMapping
	public String getCommentForm(Model model) {
		model.addAttribute("comment", new Commentary());
		model.addAttribute("books", bookRepository.findAll());
		return "forms/submitcomment";
	}
	
	
	
	@PostMapping
	public String addComment(@ModelAttribute("comment") Commentary comment, BindingResult validation, RedirectAttributes redirectAttributes, Principal princ, Model model) {		
		
		model.addAttribute("books", bookRepository.findAll());
		
		if(validation.hasErrors()) {
			redirectAttributes.addFlashAttribute("binding", "Failed to insert Data!");		
			return "forms/submitComment";
		}
		System.out.println("Looking for user...");
		comment.setUser(userRepository.findByUsername(princ.getName()));
		if(comment.getUser()==null)
			System.out.println("User not found!");
		
		System.out.println("User: " + comment.getUser().getUsername() + " found!");
				
		
		for(Book book : bookRepository.findAll()) {
			System.out.println(book.getName());
		}
		
		commentaryRepository.save(comment);		
		redirectAttributes.addFlashAttribute("binding", "Data succesfully stored!");
		
		return "redirect:/";
	}	
	
	@GetMapping("/fetchChapters")
    @ResponseBody
    public List<Chapter> fetchChapters(@RequestParam Long bookId) {
        // Replace this with your actual logic to fetch chapters based on the bookId
        // This is just a placeholder example
		       
        return chapterRepository.findByBookId(bookId);
    }
	
	
}
