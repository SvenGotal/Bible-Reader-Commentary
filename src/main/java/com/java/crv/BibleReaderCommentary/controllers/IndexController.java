package com.java.crv.BibleReaderCommentary.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java.crv.BibleReaderCommentary.domain.User;


@Controller
@RequestMapping("/")
public class IndexController {
		
	@GetMapping
	public String getIndex(Model model) {	
		model.addAttribute("message", "Hello from getIndex method!");
		model.addAttribute("user", new User());
		return "index";
	}		
}
