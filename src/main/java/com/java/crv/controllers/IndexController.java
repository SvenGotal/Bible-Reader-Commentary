package com.java.crv.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.java.crv.domain.*;

@Controller
public class IndexController {
	
	
	@GetMapping("/")
	public String setData(Model model) {
		
		model.addAttribute("user",new User());
		
		return "index";
	}

}
