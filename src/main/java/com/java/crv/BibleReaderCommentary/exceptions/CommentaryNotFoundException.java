package com.java.crv.BibleReaderCommentary.exceptions;

public class CommentaryNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 4L;
	public CommentaryNotFoundException(String msg) {
		super(msg);
	}

}
