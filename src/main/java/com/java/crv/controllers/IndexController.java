package com.java.crv.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.java.crv.domain.*;
import com.java.crv.repository.UserRepository;

@Controller
public class IndexController {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/")
	public String getIndex(Model model) {	
		model.addAttribute("message", "Hello from getIndex method!");
		model.addAttribute("user", new User());
		return "index";
	}		
}
