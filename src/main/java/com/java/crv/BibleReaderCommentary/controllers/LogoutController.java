package com.java.crv.BibleReaderCommentary.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**This controller is currently unused since Spring Security manages login
 * and logout processes. This is a stub for future custom made logout form.
 * */
@Controller
@RequestMapping("/logout")
public class LogoutController {
	
	public String logoutUser() {
		return "redirect:/";
	}	
}
