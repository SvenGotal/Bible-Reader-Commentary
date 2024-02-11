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
				UserRoles ur = currentUser.getRole();
				model.addAttribute("adminRole", ur.name());

			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("user", new User());
		model.addAttribute("adminRole", "GUEST");
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
		if(bindingResult.hasErrors())
			return "forms/submitform";
		
		/* Check if fields username and password are filled */
		if(user.getUsername() == null || user.getUsername() == "" || user.getPassword() == null || user.getPassword() == "") {
			redirectAttributes.addFlashAttribute("binding", "Failed to insert Data!");
			return "redirect:/";			
		}
		
		/* Chech if user password and retyped password match */
		/*if(user.getPassword().trim() != request.getParameter("password_retype").trim()) {
			redirectAttributes.addFlashAttribute("binding", "Passwords did not match!");
			return "redirect:/";
		}*/
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword().trim()));
		if(user.getRole() == null || user.getRole().name() == "")
			user.setRole(UserRoles.USER);
		
		userRepository.save(user);
		return "redirect:/";
	}
	
}
