package com.java.crv.BibleReaderCommentary.controllers;

import java.security.Principal;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.java.crv.BibleReaderCommentary.domain.Book;
import com.java.crv.BibleReaderCommentary.domain.Chapter;
import com.java.crv.BibleReaderCommentary.domain.Commentary;
import com.java.crv.BibleReaderCommentary.domain.User;
import com.java.crv.BibleReaderCommentary.domain.UserRoles;
import com.java.crv.BibleReaderCommentary.repositories.BookRepository;
import com.java.crv.BibleReaderCommentary.repositories.ChapterRepository;
import com.java.crv.BibleReaderCommentary.repositories.CommentaryRepository;
import com.java.crv.BibleReaderCommentary.repositories.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class SubmitCommentController {
	
	private CommentaryRepository commentaryRepository;
	private UserRepository userRepository;
	private BookRepository bookRepository;
	private ChapterRepository chapterRepository;
	
	public SubmitCommentController(
			CommentaryRepository commentaryRepository, 
			UserRepository userRepository, 
			BookRepository bookRepository,
			ChapterRepository chapterRepository
			) 
	{
		this.commentaryRepository = commentaryRepository;
		this.userRepository = userRepository;
		this.bookRepository = bookRepository;
		this.chapterRepository = chapterRepository;
	}
	
	/**Sets up the commentary form by loading all books from the book repo and creates a new Commentary object
	 * and sends it to the model to the frontend.
	 * */
	@GetMapping("/private/submitComment")
	public String getCommentForm(
		Model model, 
		Principal princ, 
		@RequestParam(name="bookId", defaultValue="0") Long bookId,
		@RequestParam(name="chapterId", defaultValue="0") Long chapterId) 
	{
		/* Get bookId and chapterId from URL - if found thymeleaf will load script. */
		if(bookId != 0 && chapterId != 0) {
			Chapter defaultChapter = chapterRepository.findById(chapterId).get();
			Book defaultBook = bookRepository.findById(bookId).get();
			
			model.addAttribute("defaultBook", defaultBook);
			model.addAttribute("defaultChapter", defaultChapter);
			
		}

		/*Fetch all Books and post them to the frontend*/
		model.addAttribute("books", bookRepository.findAll());

		
		/*Create new Commentary, this object will be used for user input and stored in the database.*/
		model.addAttribute("comment", new Commentary());
		
		
		/*User validation for the hamburger menu*/
		/*Retireve the user name from the Principal and retrieve User by username in repo*/
		String username = princ.getName();
		User loggedUser = userRepository.findByUsername(username);
		UserRoles currentUserRole = loggedUser.getRole();
		Boolean userValidated = loggedUser == null ? false : true;
		
		/*Send all the required data to the mycomments View*/
		model.addAttribute("userRole", currentUserRole.name()); /*Send user role e.g. ADMIN, USER etc...*/
		model.addAttribute("userValidated", userValidated); 	/*Send whether user is validated*/
		
		System.out.println("Controller fired..." + bookId + " : " + chapterId);
		
		return "forms/submitcomment";
	}
	
	
	@PostMapping("/private/submitComment/post")
	public String addComment(
			@ModelAttribute("comment") Commentary comment,
			@RequestParam(name="setPrivateCheckbox", required=false) String setPrivateCheckbox,
			HttpServletRequest request,
			BindingResult validation, 
			RedirectAttributes redirectAttributes, 
			Principal princ, 
			Model model) 
	{	
		/* Fetch all books to populate user selection */
		model.addAttribute("books", bookRepository.findAll());
		
		
		// Validate if there are binding issues
		if(validation.hasErrors()) {
			redirectAttributes.addFlashAttribute("binding", "Failed to insert Data!");		
			return "forms/submitComment";
		}	
		
		// Set comment to a specific user
		comment.setUser(userRepository.findByUsername(princ.getName()));
		if(comment.getUser()==null) {
			redirectAttributes.addFlashAttribute("binding", "User not found!");
			return "redirect:/";
		}
		
		/* Find Book with specified id. Book's numbers (1,2,3, etc) 
		 * correspond to their Id's in the database */
		Book selectedBook = new Book();
		if(request.getParameter("selectedBook") != null) {
			Optional<Book> book = bookRepository.findById(Long.parseLong(request.getParameter("selectedBook")));
			selectedBook = book.get();
		}
		else {
			redirectAttributes.addFlashAttribute("binding", "Morate odabrati knjigu!");
			return "redirect:/";
		}

		/* Search for Chapter by number property in the Book object from the persistence */
		Chapter selectedChapter = new Chapter();
		

		if(request.getParameter("selectedChapter") != null) {
			int chapterNumber = Integer.parseInt(request.getParameter("selectedChapter"));
			selectedChapter = selectedBook.getChapterByNumber(chapterNumber);
			/* Set found Chapter in the Book to the Commentary object 'comment' */
			comment.setChapter(selectedChapter);
		}
		else {
			redirectAttributes.addFlashAttribute("binding", "Morate odabrati poglavlje!");
			return "redirect:/";
		}
		
		/* Check requested parameter value of String
		 * If user wants their comment to remain private, they will check the checkbox so Published: false */
		Boolean published = false;
		if(setPrivateCheckbox == null || setPrivateCheckbox == "")
			published = true;
		
		/* Set comment to published/not published from the checkbox value */
		comment.setPublished(published);
		
		/* Check if the contents have been fully submitted */
		if(comment.getSubject() == null || comment.getSubject() == "") {
			redirectAttributes.addFlashAttribute("binding", "Komentar mora imati naslov!");
			return "redirect:/";
		}
		if(comment.getText().length() < 20 || comment.getText() == null) {
			redirectAttributes.addFlashAttribute("binding", "Komentar kraći od 20 slova neće biti unesen. ");
			return "redirect:/";
		}
		
		comment.setAuthor();
		/* Store comment into persistence */
		commentaryRepository.save(comment);	
		
		/* Send message to the index that saving was successful */
		redirectAttributes.addFlashAttribute("binding", "Uspješno ste dodali komentar!");
		
		return "redirect:/";
	}	
	
}
