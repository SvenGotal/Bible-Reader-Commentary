package com.java.crv.BibleReaderCommentary.exceptions;

public class BookNotFoundException extends RuntimeException {
	public BookNotFoundException(String message) {
		super(message);
	}
}
