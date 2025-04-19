package com.java.crv.BibleReaderCommentary.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**This controller is currently unused since Spring Security manages login
 * and logout processes. This is a stub for future custom made logout form.
 * */
@Controller
public class LogoutController {
	
	@GetMapping("/logout")
	public String logoutUser() {
		return "forms/logout";
	}	
}
