package com.java.crv.BibleReaderCommentary.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.crv.BibleReaderCommentary.domain.Book;
import com.java.crv.BibleReaderCommentary.domain.Chapter;
import com.java.crv.BibleReaderCommentary.domain.User;
import com.java.crv.BibleReaderCommentary.domain.UserRoles;
import com.java.crv.BibleReaderCommentary.domain.Verse;
import com.java.crv.BibleReaderCommentary.repositories.BookRepository;
import com.java.crv.BibleReaderCommentary.repositories.CommentaryRepository;
import com.java.crv.BibleReaderCommentary.repositories.UserRepository;



@Controller
@RequestMapping("/")
public class IndexController {
		
	private CommentaryRepository comments;
	private UserRepository userRepository;
	private BookRepository bookRepository;
	
	public IndexController(CommentaryRepository comments, 
			UserRepository userRepository, 
			BookRepository bookRepository
			) 
	{
		this.comments = comments;
		this.userRepository = userRepository;
		this.bookRepository = bookRepository;
	}
	
	@GetMapping
	public String getIndex(@ModelAttribute("binding") String binding,
			Model model,
			Principal princ) 
	{	
		
		String currentUsername;
		Boolean userValidated = false;
		/* Take current logged user to adjust options visibility */
		if(princ != null) {
			if(princ.getName() != null) {
				currentUsername = princ.getName();
				User currentUser = userRepository.findByUsername(currentUsername);		
				UserRoles currentUserRole = currentUser.getRole();
				userValidated = currentUserRole.name() != null ? true : false;
				model.addAttribute("userRole", currentUserRole.name());
				
			}
		}
		model.addAttribute("userValidated", userValidated);
		model.addAttribute("binding", binding);
		model.addAttribute("comments", comments.findCommentaryByPublished(true));
		model.addAttribute("books", bookRepository.findAll());
		
		return "/index";
	}
	
	@GetMapping("/login")
	public String loginUser() {
		return "redirect:/";
	}
	
	@GetMapping("/fetchVerses")
	@ResponseBody
	public List<Verse> fetchVerses(@RequestParam Long bookId){
		
		/* Modify controller to fetch verses from book->chapter->verses */
		
		Optional<Book> bk = bookRepository.findById(bookId);
		ArrayList<Chapter> chapters;
		
		if(bk.isPresent()) {
			List<Chapter> list = bk.get().getChapters(); 
			chapters = new ArrayList<Chapter>();
			
			for(Chapter ch : list) {
				
				Chapter chapt = new Chapter();
				chapt.setId(ch.getId());
				chapt.setNumber(ch.getNumber());
				chapters.add(chapt);
			}			
		}
		
		return Collections.emptyList();
	}
	
	
}
