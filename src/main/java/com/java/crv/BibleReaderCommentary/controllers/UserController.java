package com.java.crv.BibleReaderCommentary.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.java.crv.BibleReaderCommentary.domain.User;
import com.java.crv.BibleReaderCommentary.domain.UserRoles;
import com.java.crv.BibleReaderCommentary.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("public/submitForm")
	public String submitForm(@ModelAttribute("user") @Validated User newUser, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		
		if (bindingResult.hasErrors()) {		
			model.addAttribute("ErrorMsg", "binding error");
			return "errors/errorView";
		}				
		
		userService.saveUser(newUser.getUsername(), newUser.getPassword(), newUser.getEmail(), newUser.getRole());
			
		return "redirect:/";
		
		/*
		 * Check if BindingResult has stored errors during posting. If yes, simply
		 * display webpage. TODO: Rework this logic
		 */
		
		/*
		

		
		if (!StringUtils.hasText(user.getUsername())) {
			redirectAttributes.addFlashAttribute("binding", "Username is empty.");
			return "redirect:/";
		}

		
		if (!StringUtils.hasText(user.getPassword())) {
			redirectAttributes.addFlashAttribute("binding", "Password is empty.");
			return "redirect:/";
		}

		
		try {
			String password = user.getPassword().trim();
			String passwordRetype = request.getParameter("password_retype").trim();

	
			if (!password.contentEquals(passwordRetype)) {
				redirectAttributes.addFlashAttribute("binding", "Passwords did not match!");
				return "redirect:/";
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		
		try {
			user.setPassword(passwordEncoder.encode(user.getPassword().trim()));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		
		try {
			if (user.getRole() == null)
				user.setRole(UserRoles.USER);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		
		try {
			if (userRepository.existsByUsername(user.getUsername())) {
				String userAlreadyExistsErrorMessage = "Korisnik sa tim imenom već postoji!";
				model.addAttribute("userAlreadyExistsErrorMessage", userAlreadyExistsErrorMessage);
				return "forms/submitform";
			}
		}
		catch(NullPointerException e) {
			e.printStackTrace();
		}

	
		userRepository.save(user);
		return "redirect:/";*/
	}
}
