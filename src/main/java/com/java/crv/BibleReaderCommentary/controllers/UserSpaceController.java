package com.java.crv.BibleReaderCommentary.controllers;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.java.crv.BibleReaderCommentary.domain.Commentary;
import com.java.crv.BibleReaderCommentary.domain.User;
import com.java.crv.BibleReaderCommentary.domain.UserRoles;
import com.java.crv.BibleReaderCommentary.repositories.CommentaryRepository;
import com.java.crv.BibleReaderCommentary.repositories.UserRepository;

@Controller
public class UserSpaceController {
	
	private CommentaryRepository commentaryRepository;
	private UserRepository userRepository;
	
	public UserSpaceController(CommentaryRepository commentaryRepository, UserRepository userRepository) {
		this.commentaryRepository = commentaryRepository; 
		this.userRepository = userRepository;
	}
	
	
	
	
	
	
}
