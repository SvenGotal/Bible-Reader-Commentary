package com.java.crv.BibleReaderCommentary.controllers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java.crv.BibleReaderCommentary.domain.User;
import com.java.crv.BibleReaderCommentary.domain.UserRoles;
import com.java.crv.BibleReaderCommentary.repositories.UserRepository;

@Controller
@RequestMapping("/submitForm")
public class SubmitFormController {

	
	private final UserRepository userRepository;
	public SubmitFormController(UserRepository userRepository){
		this.userRepository = userRepository;
	}
	
	@GetMapping
	public String showForm(Model model) {
		model.addAttribute("user", new User());
		return "forms/submitform";
	}
	
	@PostMapping
	public String submitForm(@ModelAttribute("user") @Validated User user, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors())
			return "forms/submitform";
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		user.setRole(UserRoles.USER);
		
		userRepository.save(user);
		return "redirect:/";
	}
	
}
