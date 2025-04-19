package com.java.crv.BibleReaderCommentary.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/logout")
public class LogoutController {
	
	public String logoutUser() {
		return "redirect:/";
	}	
}
