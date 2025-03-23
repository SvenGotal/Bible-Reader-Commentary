package com.java.crv.BibleReaderCommentary.objects;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
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
	
	public CommentBroker(CommentaryRepository commentaryRepository) {
		this.commentaryRepository = commentaryRepository;
	}
	
	public void importCommentary() {
		
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
				Cell comment_author = row.getCell(6);
				Cell comment_chapter_id = row.getCell(7);
				
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
				comment.setTimestamp(comment_timestamp.getStringCellValue());
				System.out.println("pass setting timestamp value...");
				comment.setAuthor(comment_author.getStringCellValue());
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
	
	public byte[] exportComments() throws IOException {
		
		try (Workbook workbook = new XSSFWorkbook()){
			
			
			/* create worksheet and populate List for comments */
			Sheet worksheet = workbook.createSheet("Comments");
			Iterable<Commentary> repoComments = commentaryRepository.findAll();
			
			Integer rowCount = 1;
			
			/* create header row and set headers */
			Row headerRow = worksheet.createRow(0);
			headerRow.createCell(0).setCellValue("comment_id");
			headerRow.createCell(1).setCellValue("comment_subject");
			headerRow.createCell(2).setCellValue("comment_published");
			headerRow.createCell(3).setCellValue("comment_text");
			headerRow.createCell(4).setCellValue("comment_timestamp");
			headerRow.createCell(5).setCellValue("comment_user_id");
			headerRow.createCell(6).setCellValue("comment_author");
			headerRow.createCell(7).setCellValue("comment_chapter_id");
			
			/* populating excel file with data from CommentaryRepository */
			for(Commentary cmnt : repoComments) {
								
				Row currentRow = worksheet.createRow(rowCount);

				currentRow.createCell(0).setCellValue(cmnt.getId());
				currentRow.createCell(1).setCellValue(cmnt.getSubject());
				currentRow.createCell(2).setCellValue(cmnt.getPublished());
				currentRow.createCell(3).setCellValue(cmnt.getText());
				currentRow.createCell(4).setCellValue(cmnt.getTimestamp());
				currentRow.createCell(5).setCellValue(cmnt.getUser().getId());
				currentRow.createCell(6).setCellValue(cmnt.getAuthor());
				currentRow.createCell(7).setCellValue(cmnt.getChapter().getId());
				
				++rowCount;
			}
			
			try(ByteArrayOutputStream output = new ByteArrayOutputStream()){
				workbook.write(output);
				return output.toByteArray();
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
		}
		
		return null;
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
