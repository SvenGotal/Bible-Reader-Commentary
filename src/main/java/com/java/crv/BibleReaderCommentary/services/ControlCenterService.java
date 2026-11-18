package com.java.crv.BibleReaderCommentary.services;

import org.springframework.security.crypto.password.PasswordEncoder;

public class ControlCenterService {
	private UserService userService;
	private CommentaryService commentaryService;
	private PasswordEncoder passwordEncoder;
	
	public ControlCenterService (UserService userService, CommentaryService commentaryService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.commentaryService = commentaryService;
		this.passwordEncoder = passwordEncoder;
	}

	public void adminChangeUserPassword(Long id) {
		userService.updatePassword(id);
	}
	
}
