package com.java.crv.BibleReaderCommentary.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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

	@GetMapping("public/submitForm")
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("user", new User());
		return "forms/submitform";
	}

	@PostMapping("public/submitForm")
	public String submitForm(@ModelAttribute("user") @Validated User user, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		/*
		 * Check if BindingResult has stored errors during posting. If yes, simply
		 * display webpage. TODO: Rework this logic
		 */
		if (bindingResult.hasErrors())
			return "forms/submitform";

		/* Check if field username is empty. */
		if (!StringUtils.hasText(user.getUsername())) {
			redirectAttributes.addFlashAttribute("binding", "Username is empty.");
			return "redirect:/";
		}

		/* Check if field password is empty. */
		if (!StringUtils.hasText(user.getPassword())) {
			redirectAttributes.addFlashAttribute("binding", "Password is empty.");
			return "redirect:/";
		}

		/* Validate whether input strings for password match. */
		try {
			String password = user.getPassword().trim();
			String passwordRetype = request.getParameter("password_retype").trim();

			/* Use String.contentEquals(String) for comparing. */
			if (!password.contentEquals(passwordRetype)) {
				redirectAttributes.addFlashAttribute("binding", "Passwords did not match!");
				return "redirect:/";
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		/* Use passwordEncoder bean DI to encrypt the password. */
		try {
			user.setPassword(passwordEncoder.encode(user.getPassword().trim()));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		/*
		 * Set the default role to "User" if admin has not specified it during creation.
		 * This is the default role when guest is creating a user.
		 */
		try {
			if (user.getRole() == null)
				user.setRole(UserRoles.USER);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		/* Save user to the repository. */
		userRepository.save(user);
		return "redirect:/";
	}

}
