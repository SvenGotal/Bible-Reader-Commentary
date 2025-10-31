package com.java.crv.BibleReaderCommentary.exceptions;

public class UserNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 3L;
	public UserNotFoundException(String message) {
		super(message);
	}
}
