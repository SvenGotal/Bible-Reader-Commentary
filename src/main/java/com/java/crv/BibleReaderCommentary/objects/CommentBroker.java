package com.java.crv.BibleReaderCommentary.objects;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.java.crv.BibleReaderCommentary.domain.Chapter;
import com.java.crv.BibleReaderCommentary.domain.Commentary;
import com.java.crv.BibleReaderCommentary.domain.User;
import com.java.crv.BibleReaderCommentary.repositories.ChapterRepository;
import com.java.crv.BibleReaderCommentary.repositories.CommentaryRepository;
import com.java.crv.BibleReaderCommentary.repositories.UserRepository;

public class CommentBroker {
	//todo write a class that will export comments into a file with a format that can be used to upload comments.
	
	private CommentaryRepository commentaryRepository;
	private UserRepository userRepository;
	private ChapterRepository chapterRepository;
	private String filename;
	
	public CommentBroker(
			CommentaryRepository commentaryRepository,
			UserRepository userRepository, 
			ChapterRepository chapterRepository, 
			String filename
			) 
	{
		this.commentaryRepository = commentaryRepository;
		this.userRepository = userRepository;
		this.chapterRepository = chapterRepository;

		if(checkFilename()){
			this.filename = filename;
		}
		
	}
	
	public void ImportCommentary() {
		
		try (FileInputStream inputStream = new FileInputStream(filename)){
			
			Workbook workbook = new XSSFWorkbook(filename);
			
			/* Get the first sheet in the excel file (required) */
			Sheet sheet = workbook.getSheetAt(0);
			
			for(Row row : sheet) {
				
				/* Skip first row (header row) */
				if(row.getRowNum() == 0) {
					continue;
				}
				
				/* Get cells */
				Cell comment_id = row.getCell(0);
				Cell comment_subject = row.getCell(1);
				Cell comment_published = row.getCell(2);
				Cell comment_text = row.getCell(3);
				Cell comment_timestamp = row.getCell(4);
				Cell comment_user_id = row.getCell(5);
				Cell comment_chapter_id = row.getCell(6);
				
				/* Get models required to bind the comment */
				User user = userRepository.findById((long) comment_user_id.getNumericCellValue()).get();
				Chapter chapter = chapterRepository.findById((long)comment_chapter_id.getNumericCellValue()).get();
				Commentary comment = new Commentary();
				
				comment.setId((long)comment_id.getNumericCellValue());
				comment.setSubject(comment_subject.getStringCellValue());
				comment.setPublished(comment_published.getBooleanCellValue());
				comment.setText(comment_text.getStringCellValue());
				comment.setTimestamp(comment_timestamp.getStringCellValue());
				comment.setUser(user);
				comment.setChapter(chapter);
				
				user.getComments().add(comment);
				chapter.getComments().add(comment);
				commentaryRepository.save(comment);
				
			}
			
			workbook.close();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private Boolean checkFilename() {
		if(this.filename.isEmpty()) {
			return false;
		}
		if(this.filename == null) {
			return false;
		}
		
		return true;
	}
}
