package com.java.crv.BibleReaderCommentary.domainDTO;

import java.time.LocalDate;

public record CommentaryRecord(
		Long id, 
		String text, 
		String subject, 
		LocalDate dateOfCreation, 
		String authorName, 
		int chapterNumber, 
		String bookName) 
{

}
