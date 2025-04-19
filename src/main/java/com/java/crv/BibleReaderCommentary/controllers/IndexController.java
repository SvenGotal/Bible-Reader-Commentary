package com.java.crv.BibleReaderCommentary.controllers;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.java.crv.BibleReaderCommentary.domain.User;
import com.java.crv.BibleReaderCommentary.domain.UserRoles;
import com.java.crv.BibleReaderCommentary.repositories.BookRepository;
import com.java.crv.BibleReaderCommentary.repositories.CommentaryRepository;
import com.java.crv.BibleReaderCommentary.repositories.UserRepository;

/**Index Controller will send the index View to the browser. It uses Model 
 * and Principal (which will be moved to it's own separate Advice in the future).
 * Book objects will be served to the index but Chapters and Verses are served
 * dynamically by the APIController. 
 * */
@Controller
@RequestMapping("/")
public class IndexController {
		
	private UserRepository userRepository;
	private BookRepository bookRepository;
	
	public IndexController(CommentaryRepository comments, 
			UserRepository userRepository, 
			BookRepository bookRepository
			) 
	{
		this.userRepository = userRepository;
		this.bookRepository = bookRepository;
	}
	
	/**getIndex method will only return index in the near future. Binding will become verseOfTheDay
	 * and Principal and any user authentication will be moved to separate Advice.
	 * */
	@GetMapping("/")
	public String getIndex(@ModelAttribute("binding") String binding,
			Model model,
			Principal princ){	
		
		/*================================= User Authentication =================================*/
		/* Principal will be moved to separate Advice for global visibility and won't be used here 
		 * in the future. User authentication will not be part of this controller.*/
		
		String currentUsername = "guest";
		Boolean userValidated = false;
		/* Take current logged user to adjust options visibility */
		if(princ != null) {
			if((currentUsername = princ.getName()) != null) {
				User currentUser = userRepository.findByUsername(currentUsername);	
				if(currentUser != null) {
					UserRoles currentUserRole = currentUser.getRole();
					userValidated = currentUserRole.name() != null ? true : false;
					model.addAttribute("userRole", currentUserRole.name());
					model.addAttribute("username", currentUser.getUsername());
				}
			}

		}
		else {
			model.addAttribute("userRole", currentUsername);	
			model.addAttribute("username", "guest");
		}
		model.addAttribute("userValidated", userValidated);
		/*================================= User Authentication =================================*/
		
		/* Currently this is the only part of the code that might remain for the time being. Since
		 * Books are being statically served in two places (index.html and submitcomment.html) it
		 * is be likely that this code will be moved to it's own Advice. */
		model.addAttribute("books", bookRepository.findAll());
		
		return "index";
	}
}
