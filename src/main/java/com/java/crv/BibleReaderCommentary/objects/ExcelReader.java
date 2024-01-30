package com.java.crv.BibleReaderCommentary.objects;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	private String filename;
	
	public ExcelReader(String filename) {
		
		//todo check validity of file path
		this.filename = filename;			
	}
	
	public void read() {
		
		try(FileInputStream inputStream = new FileInputStream(filename)){
			Workbook workbook = new XSSFWorkbook(filename);
			Sheet sheet = workbook.getSheetAt(0);
			
			for(Row row : sheet) {
				
				;
				for(Cell cell : row) {
					
				}
				
				Cell book = row.getCell(0);
				Cell chapter = row.getCell(1);
				Cell verseText = row.getCell(2);
				Cell verseNumber = row.getCell(3);
				
				if(book != null)
					System.out.println(book.toString() + " " + chapter.toString());
						
				String vt = verseText.toString().replaceAll("\\b\\d+(\\.\\d+)?\\b", "");
				String vn = verseNumber.toString().replaceAll("\\.0\\b", "");
				System.out.println(vn + " " + vt);
//				for(Cell cell : row) {
//					System.out.print(cell.toString());
//					System.out.print(" ");
//					
//				}
				
			}
			
			workbook.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
	}
	
}





