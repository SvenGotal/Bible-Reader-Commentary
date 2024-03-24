package com.java.crv.BibleReaderCommentary.objects;

import java.io.FileInputStream;
import java.util.ArrayList;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.java.crv.BibleReaderCommentary.domain.Bible;
import com.java.crv.BibleReaderCommentary.domain.Book;
import com.java.crv.BibleReaderCommentary.domain.Chapter;
import com.java.crv.BibleReaderCommentary.domain.Verse;


public class BibleImporter {

	private String filename;
	
	public BibleImporter(String filename) {
		
		//todo check validity of file path
		this.filename = filename;			
	}
	
	public Bible loadBible(Integer worksheet) {
		
		Bible bible = new Bible();
		bible.setBooks(new ArrayList<Book>());
				
		/* Create domains */
		Book book = new Book();				
		Chapter chapter = null;
		Verse verse = null;
		
		try(FileInputStream inputStream = new FileInputStream(filename)){
			Workbook workbook = new XSSFWorkbook(filename);
			Sheet sheet = workbook.getSheetAt(worksheet);
			bible.setTranslation(sheet.getSheetName());
			
			System.out.println("Loading translation: " + bible.getTranslation() + "...");
			
			for(Row row : sheet) {
				
				Cell bookCell = row.getCell(0);
				Cell chapterCell = row.getCell(1);
				Cell verseCell = row.getCell(2);
				Cell verseNumberCell = row.getCell(3);
				
				if(bookCell != null ) {
					if(bookCell.toString() != "") {
						
						if(book.getName() != bookCell.getStringCellValue()) {
							/* Create new book if entry is found and assign fields, Bible adds book */
							book = new Book();
							book.setName(bookCell.toString());
							book.setChapters(new ArrayList<Chapter>());								
							book.setBible(bible);
							bible.getBooks().add(book);

							System.out.println("Loading book: " + book.getName());
						}
						/* Create new chapter, assign  */	
						chapter = new Chapter();
						chapter.setVerses(new ArrayList<Verse>());
						int ch = (int)chapterCell.getNumericCellValue();
						chapter.setNumber(ch);
						chapter.setBook(book);
						book.getChapters().add(chapter);

						System.out.println("Load chapter: " + chapter.getNumber());
						
					}
				}	

				/* Create new verse, assign fields from table*/
				verse = new Verse();
				//verse.setText(verseCell.toString().replaceAll("\\b\\d+(\\.\\d+)?\\b", "").trim());
				String verseText = verseCell.toString();
				verseText = verseText.replaceAll("\\([^()]*\\)", "");
				verseText = verseText.replaceAll("[^a-zA-Z\\sŠšĐđČčĆćŽž!.,;:<>\"]", "").trim();
				
				verse.setText(verseText);
				verse.setNumber((int)verseNumberCell.getNumericCellValue());
				verse.setChapter(chapter);
				System.out.println("Verse: " + verse.getNumber());
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





