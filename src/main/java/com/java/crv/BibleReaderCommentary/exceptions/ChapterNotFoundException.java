package com.java.crv.BibleReaderCommentary.exceptions;

public class ChapterNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 5L;
	public ChapterNotFoundException (String msg) {
		super(msg);
	}

}
