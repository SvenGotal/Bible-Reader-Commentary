package com.java.crv.BibleReaderCommentary.objects;

import java.io.FileInputStream;
import java.util.NoSuchElementException;

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
		
		if(checkFilename(filename)){
			this.filename = filename;
		}
		else {
			System.out.println("Filename missing or corrupted...");
		}
		
	}
	
	public void ImportCommentary() {
		
		try (FileInputStream inputStream = new FileInputStream(filename)){
			
			//System.out.println("Starting import...");
			
			Workbook workbook = new XSSFWorkbook(filename);
			
			/* Get the first sheet in the excel file (required) */
			Sheet sheet = workbook.getSheetAt(0);
			
			for(Row row : sheet) {
				
				/* Skip first row (header row) */
				if(row.getRowNum() == 0) {
					continue;
				}
				//System.out.println("getting cells...");
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
				System.out.println(user.getUsername());
				Chapter chapter = chapterRepository.findById((long)comment_chapter_id.getNumericCellValue()).get();
				System.out.println(chapter.getNumber());
				Commentary comment = new Commentary();
				
				comment.setId((long)comment_id.getNumericCellValue());
				comment.setSubject(comment_subject.getStringCellValue());
				comment.setPublished(comment_published.getBooleanCellValue());
				comment.setText(comment_text.getStringCellValue());
				System.out.println("pass setting text value...");
				comment.setTimestamp(comment_timestamp.getDateCellValue().toString());
				System.out.println("pass setting timestamp value...");
				comment.setUser(user);
				comment.setChapter(chapter);
				
				System.out.println(comment.toString());
				
				user = userRepository.save(user);
			    chapter = chapterRepository.save(chapter);

			    comment.setUser(user);
			    comment.setChapter(chapter);

			    commentaryRepository.save(comment);
				
			}
			
			workbook.close();
			
		}
		catch (NoSuchElementException e) {
			System.out.println("Bible is not loaded...");
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	private Boolean checkFilename(String filename) {
		if(filename == null){
			return false;
		}
		if(filename.isEmpty()) {
			return false;
		}
		
		return true;
	}
}
