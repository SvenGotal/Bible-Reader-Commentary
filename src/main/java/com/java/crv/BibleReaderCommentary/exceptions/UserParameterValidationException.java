package com.java.crv.BibleReaderCommentary.exceptions;

public class UserParameterValidationException extends RuntimeException{
	private static final long serialVersionUID = 6L;
	public UserParameterValidationException(String msg) {
		super(msg);
	}
}
