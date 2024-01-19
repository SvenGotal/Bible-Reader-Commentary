package com.java.crv.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.java.crv.domain.*;

@Controller
public class IndexController {
	
	
	@GetMapping("/")
	public String getIndex(Model model) {
		
		model.addAttribute("user",new User());
		
		return "index";
	}
	
	@PostMapping("/")
	public String setData(@ModelAttribute("user") User user, Model model) {
		model.addAttribute("message","Data succesfully submitted!");
		return "redirect:/";
	}

}
