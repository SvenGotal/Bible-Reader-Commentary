package com.java.crv.BibleReaderCommentary.controllers;

import java.security.Principal;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.java.crv.BibleReaderCommentary.domain.User;
import com.java.crv.BibleReaderCommentary.domain.UserRoles;
import com.java.crv.BibleReaderCommentary.repositories.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class SubmitFormController {

	
	private final UserRepository userRepository;
	public SubmitFormController(UserRepository userRepository){
		this.userRepository = userRepository;
	}
	
	@GetMapping("public/submitForm")
	public String showForm(Model model, Principal princ) {
				
		try {
			
			if(princ != null) {
				
				String username = princ.getName();
				
				User currentUser = userRepository.findByUsername(username);
				
				if(currentUser != null) {
					UserRoles ur = currentUser.getRole();
					model.addAttribute("adminRole", ur.name());
					
				}
				else {
					model.addAttribute("adminRole", "guest");
				}
			}
			else
			{
				model.addAttribute("username", "guest");
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("user", new User());		
		return "forms/submitform";
	}
	
	@PostMapping("public/submitForm")
	public String submitForm(
			@ModelAttribute("user") @Validated User user,
			BindingResult bindingResult, 
			Model model,
			RedirectAttributes redirectAttributes,
			HttpServletRequest request) 
	{
		/* Check if BindingResult has stored errors during posting. If yes, simply display webpage */
		if(bindingResult.hasErrors())
			return "forms/submitform";
		
		/* Check if fields username and password are filled */
		if(user.getUsername() == null || user.getUsername() == "" || user.getPassword() == null || user.getPassword() == "") {
			redirectAttributes.addFlashAttribute("binding", "Failed to insert Data!");
			return "redirect:/";			
		}

		/* Validate whether input strings for password match */
		String password = user.getPassword().trim();
		String passwordRetype = request.getParameter("password_retype").trim();
		/* Use String.contentEquals(String) for comparing */
		if(!password.contentEquals(passwordRetype)) {
			redirectAttributes.addFlashAttribute("binding", "Passwords did not match!");
			System.out.println("Passwords do not match!" + user.getPassword() + " " + request.getParameter("password_retype"));
			return "redirect:/";
		}

		/* Use encoder bean to encrypt the password, if user has no assigned roles then assign
		 * the 'user' role as the default role */
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword().trim()));
		if(user.getRole() == null)
			user.setRole(UserRoles.USER);
		
		userRepository.save(user);
		return "redirect:/";
	}
	
}
