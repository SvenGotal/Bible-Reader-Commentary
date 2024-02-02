package com.java.crv.BibleReaderCommentary.objects;

import java.io.FileInputStream;
import java.util.ArrayList;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.java.crv.BibleReaderCommentary.domain.Bible;
import com.java.crv.BibleReaderCommentary.domain.Book;
import com.java.crv.BibleReaderCommentary.domain.Chapter;
import com.java.crv.BibleReaderCommentary.domain.Verse;


public class ExcelReader {

	private String filename;
	
	public ExcelReader(String filename) {
		
		//todo check validity of file path
		this.filename = filename;			
	}
	
	public Bible loadBible(String translation) {
		
		Bible bible = new Bible();
		bible.setTranslation(translation);
		bible.setBooks(new ArrayList<Book>());
		
		int countVerse = 1;
		int countBookandChapter = 1;
		
		/* Create domains */
		Book book = null;				
		Chapter chapter = null;
		Verse verse = null;
		
		try(FileInputStream inputStream = new FileInputStream(filename)){
			Workbook workbook = new XSSFWorkbook(filename);
			Sheet sheet = workbook.getSheetAt(0);
			
			for(Row row : sheet) {
				
				
				
				Cell bookCell = row.getCell(0);
				Cell chapterCell = row.getCell(1);
				Cell verseCell = row.getCell(2);
				Cell verseNumberCell = row.getCell(3);
				if(bookCell != null ) {
					if(bookCell.toString() != "") {
						
						if(!bible.getBooks().contains(book)) {
							/* Create new book if entry is found and assign fields, Bible adds book */
							book = new Book();
							book.setChapters(new ArrayList<Chapter>());
							book.setName(bookCell.toString());	
							book.setBible(bible);
							bible.getBooks().add(book);

							System.out.println("Pass book: " + countBookandChapter);
						}
						/* Create new chapter, assign  */	
						chapter = new Chapter();
						chapter.setVerses(new ArrayList<Verse>());
						int ch = (int)chapterCell.getNumericCellValue();
						chapter.setNumber(ch);
						chapter.setBook(book);
						book.getChapters().add(chapter);

						System.out.println("Pass chapter: " + countBookandChapter);
						++countBookandChapter;
					}
				}	

				/* Create new verse, assign fields from table*/
				verse = new Verse();
				verse.setText(verseCell.toString().replaceAll("\\b\\d+(\\.\\d+)?\\b", "").trim());
				verse.setNumber((int)verseNumberCell.getNumericCellValue());
				verse.setChapter(chapter);
				
				System.out.println("Pass verse: " + countVerse);
				
				++countVerse;
				
				
				if(chapter != null)
					chapter.getVerses().add(verse);
			}
			
			workbook.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
			
		}			
		return bible;
	}
	
}





