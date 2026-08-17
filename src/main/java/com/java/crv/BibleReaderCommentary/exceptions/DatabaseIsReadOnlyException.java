package com.java.crv.BibleReaderCommentary.exceptions;

public class DatabaseIsReadOnlyException extends RuntimeException{
	private static final long serialVersionUID = 9L;
	public DatabaseIsReadOnlyException(String msg) {
		super(msg);
	}
}
