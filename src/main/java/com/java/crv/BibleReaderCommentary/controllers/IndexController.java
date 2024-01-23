package com.java.crv.BibleReaderCommentary.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/")
public class IndexController {
		
	@GetMapping
	public String getIndex(@ModelAttribute("binding") String binding, Model model) {	
		
		model.addAttribute("binding", binding);
		
		return "index";
	}		
}
