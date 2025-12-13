package com.java.crv.BibleReaderCommentary.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.java.crv.BibleReaderCommentary.domain.UserFeedback;
import com.java.crv.BibleReaderCommentary.services.UserFeedbackService;

@Controller
public class UserFeedbackController {

	private UserFeedbackService userFeedbackService;
	
	public UserFeedbackController(UserFeedbackService userFeedbackService) {
		this.userFeedbackService = userFeedbackService;
	}
	
	@GetMapping("/public/getUserFeedback")
	@ResponseBody
	public List<UserFeedback> getAllUserFeedback(){
		return userFeedbackService.getAllUserFeedbackInDatabase();
	}
	
	@PostMapping("/public/addNewUserFeedback")
	public String addNewUserFeedback(@ModelAttribute("userFeedback") UserFeedback userFeedback,BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		/*ErrorMsg*/
		if(bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("ErrorMsg", "Binding failure...");
			return "redirect:/errors/errorView";
		}
		if(userFeedback.getUserInfo().isEmpty() || userFeedback.getFeedbackText().isEmpty()){
			redirectAttributes.addFlashAttribute("ErrorMsg", "Name or feedback text is missing...");
			return "redirect:/errors/errorView";
		}
		
		if(!userFeedbackService.saveUserFeedback(userFeedback)) {
			redirectAttributes.addFlashAttribute("ErrorMsg", "Feedback wasn't stored succesfully...");
			return "redirect:/errors/errorView";
		}
		
		redirectAttributes.addFlashAttribute("SuccessMsg", "Vaš komentar je uspješno pohranjen!");
		return "redirect:/";
	}
}








