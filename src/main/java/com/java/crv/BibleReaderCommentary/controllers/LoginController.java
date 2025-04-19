package com.java.crv.BibleReaderCommentary.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**This is a custom controller for custom login page for the app.*/
@Controller
public class LoginController {

	@GetMapping("/login")
	public String loginUser() {
		return "forms/login";
	}
}
