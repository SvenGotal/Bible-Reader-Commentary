package com.java.crv.BibleReaderCommentary.exceptions;

public class UserIsAdminException extends RuntimeException{
	private static final long serialVersionUID = 10L;
	public UserIsAdminException(String msg) {
		super(msg);
	}
}
