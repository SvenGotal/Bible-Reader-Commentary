package com.java.crv.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.java.crv.domain.User;

@Controller
public class SubmitFormController {

	@PostMapping("/submitForm")
	public String submitForm(@ModelAttribute("user") @Validated User user, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors())
			return "submitform";
		
		model.addAttribute("message", "User successfully saved!");
		return "redirect:/";
	}
	
}
