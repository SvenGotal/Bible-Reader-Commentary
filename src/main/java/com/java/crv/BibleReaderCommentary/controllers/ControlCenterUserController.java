package com.java.crv.BibleReaderCommentary.controllers;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java.crv.BibleReaderCommentary.domain.Commentary;
import com.java.crv.BibleReaderCommentary.domain.User;
import com.java.crv.BibleReaderCommentary.domain.UserRoles;
import com.java.crv.BibleReaderCommentary.exceptions.UserNotFoundException;
import com.java.crv.BibleReaderCommentary.repositories.CommentaryRepository;
import com.java.crv.BibleReaderCommentary.repositories.UserRepository;

@Controller
public class ControlCenterUserController {
	private UserRepository userService;
	private CommentaryRepository commentaryRepository;

	
	public ControlCenterUserController(UserRepository userService, CommentaryRepository commentaryRepository) {
		this.userService = userService;
		this.commentaryRepository = commentaryRepository;
	}
	
	
	
	
	
}
