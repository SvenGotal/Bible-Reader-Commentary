package com.java.crv.BibleReaderCommentary.controllers;

import java.security.Principal;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.java.crv.BibleReaderCommentary.domain.User;
import com.java.crv.BibleReaderCommentary.domain.UserRoles;
import com.java.crv.BibleReaderCommentary.repositories.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class SubmitFormController {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public SubmitFormController(UserRepository userRepository, BCryptPasswordEncoder encoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = encoder;
	}
/*
	//@GetMapping("public/submitForm")
	public String showForm(Model model, Principal princ) {
		try {
			if (princ != null) {
				String username = princ.getName();
				User currentUser = userRepository.findByUsername(username);

				if (currentUser != null) {
					UserRoles ur = currentUser.getRole();
					model.addAttribute("adminRole", ur.name());
				} else {
					model.addAttribute("adminRole", "guest");
				}
			} else {
				model.addAttribute("username", "guest");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("user", new User());
		return "forms/submitform";
	}*/

	

}
