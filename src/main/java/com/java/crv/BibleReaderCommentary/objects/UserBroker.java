package com.java.crv.BibleReaderCommentary.objects;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.java.crv.BibleReaderCommentary.domain.Commentary;
import com.java.crv.BibleReaderCommentary.domain.User;
import com.java.crv.BibleReaderCommentary.domain.UserRoles;
import com.java.crv.BibleReaderCommentary.repositories.CommentaryRepository;
import com.java.crv.BibleReaderCommentary.repositories.UserRepository;

public class UserBroker {

	
	private UserRepository userRepository;
	private CommentaryRepository commentaryRepository;
	private String filename;
	
	
	
	public UserBroker(UserRepository userRepository,CommentaryRepository commentaryRepository, String filename) {
		
		this.userRepository = userRepository;
		this.commentaryRepository = commentaryRepository;
		this.filename = filename;
		
	}
	
	public UserBroker(UserRepository userRepository) {
		
		this.userRepository = userRepository;
		
	}
	
	public void importUsers() {
		
		try(FileInputStream inputStream = new FileInputStream(filename)){
			
			
			Workbook workbook = new XSSFWorkbook(filename);
			Sheet sheet = workbook.getSheetAt(0);
			
			for(Row row : sheet) {
				
				/* skip header row */
				if(row.getRowNum() == 0) {
					continue;
				}
				
				Cell user_id = row.getCell(0);
				Cell user_password = row.getCell(1);
				Cell user_username = row.getCell(2);
				Cell user_email = row.getCell(3);
				Cell user_role = row.getCell(4);
				Cell user_comment_id = row.getCell(5);
				
				Commentary comment = commentaryRepository.findById( (long) user_comment_id.getNumericCellValue() ).get();
				User importedUser = new User();
				importedUser.setId( (long) user_id.getNumericCellValue());
				importedUser.setPassword(user_password.getStringCellValue());
				importedUser.setUsername(user_username.getStringCellValue());
				importedUser.setEmail(user_email.getStringCellValue());
				
				UserRoles ur;
				switch((int)user_role.getNumericCellValue()) {
					case 1:
						ur = UserRoles.USER;
						break;
					case 2:
						ur = UserRoles.ADMIN;
						break;
					case 3:
						ur = UserRoles.OWNER;
						break;
					default:
						ur = UserRoles.USER;
				}
				
				importedUser.setRole(ur);
				importedUser.getComments().add(comment);
				userRepository.save(importedUser);
			}
			
			workbook.close();
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
