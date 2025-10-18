package com.java.crv.BibleReaderCommentary.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FormLoaderController {
	
	@GetMapping("/private/myHomepage")
	public String loadUserHomepage() {
		return "forms/user_homepage.html";
	}

}
