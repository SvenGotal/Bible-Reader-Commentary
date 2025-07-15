package com.java.crv.BibleReaderCommentary.controllers;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.java.crv.BibleReaderCommentary.domain.User;
import com.java.crv.BibleReaderCommentary.repositories.UserRepository;

@Controller
public class ControlCenterUserController {
	private UserRepository userRepository;
	
	public ControlCenterUserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@GetMapping("/admin/usercontrol")
	public String getControlCenterUsersForm(Model model) {
		
		try {
			ArrayList<User> allUsersInADatabaseList = new ArrayList<User>();
			allUsersInADatabaseList = (ArrayList<User>) userRepository.findAll();
			
			model.addAttribute("allUsersInADatabaseList", allUsersInADatabaseList);
		}
		catch(NullPointerException e){
			e.printStackTrace();
		}
		
		return "forms/controlcenter_userspage";
	}
}
