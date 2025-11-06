package com.java.crv.BibleReaderCommentary.exceptions;

public class UserAlreadyExistsException extends RuntimeException{
	private static final long serialVersionUID = 7L;
	public UserAlreadyExistsException(String msg) {
		super(msg);
	}

}
