package com.java.crv.BibleReaderCommentary.exceptions;

public class BookNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 2L;
	public BookNotFoundException(String message) {
		super(message);
	}
}
