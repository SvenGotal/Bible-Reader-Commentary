package com.java.crv.BibleReaderCommentary.controllers;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.h2.tools.RunScript;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.java.crv.BibleReaderCommentary.domain.Bible;
import com.java.crv.BibleReaderCommentary.objects.BibleImporter;
import com.java.crv.BibleReaderCommentary.objects.CommentBroker;
import com.java.crv.BibleReaderCommentary.objects.UserBroker;
import com.java.crv.BibleReaderCommentary.repositories.BibleRepository;
import com.java.crv.BibleReaderCommentary.repositories.ChapterRepository;
import com.java.crv.BibleReaderCommentary.repositories.CommentaryRepository;
import com.java.crv.BibleReaderCommentary.repositories.UserRepository;

@Controller
public class ControlCenterController {

	private CommentaryRepository commentaryRepository;
	private UserRepository userRepository;
	private ChapterRepository chapterRepository;
	private BibleRepository bibleRepository;
	private static final String UPLOAD_DIRECTORY = "/home/sven/uploads1"; //prepare path before deployment.
	
	public ControlCenterController(
		CommentaryRepository commentaryRepository, 
		BibleRepository bibleRepository, 
		UserRepository userRepository, 
		ChapterRepository chapterRepository
		) 
	{
		this.commentaryRepository = commentaryRepository;
		this.bibleRepository = bibleRepository;
		this.userRepository = userRepository;
		this.chapterRepository = chapterRepository;
	}
	
	@GetMapping("/admin/controlCenter")
	public String getCCForm() {
		
		return "forms/controlcenter";
		
	}
	
	@PostMapping ("/admin/uploadBible")
	public String uploadBible(@RequestPart("fileInput") MultipartFile file) {
		
		//todo use StorageService in later versions: https://spring.io/guides/gs/uploading-files . Current setup is not ideal.
		
		try {
		
			System.out.println(System.getProperty("os.name"));
			File directory = new File(UPLOAD_DIRECTORY);
			if(!directory.exists()) {
				System.out.println("Creating directory " + UPLOAD_DIRECTORY);
				directory.mkdir();
			}
			
			
			String filePath = UPLOAD_DIRECTORY + File.separator + file.getOriginalFilename();
			File destination = new File(filePath);
			
			System.out.println("Uploading file to " + destination.toString());
			file.transferTo(destination);
			
			BibleImporter bl = new BibleImporter(destination.toString());
			Bible bibleForLoading = bl.loadBible(0);
			bibleRepository.save(bibleForLoading);
				
		}
		catch (NullPointerException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return "redirect:/";
	}
	
	@PostMapping("/admin/uploadComments")
	public String uploadComments(@RequestPart("fileInput") MultipartFile file) {
		
		try {
			System.out.println(System.getProperty("os.name"));
			File directory = new File(UPLOAD_DIRECTORY);
			if(!directory.exists()) {
				System.out.println("Creating directory " + UPLOAD_DIRECTORY);
				directory.mkdir();
			}
			
			
			String filePath = UPLOAD_DIRECTORY + File.separator + file.getOriginalFilename();
			File destination = new File(filePath);
			
			System.out.println("Uploading file to " + destination.toString());
			file.transferTo(destination);
			
			System.out.println("Starting comment broker...");
			CommentBroker cb = new CommentBroker(commentaryRepository, userRepository, chapterRepository, destination.toString());
			System.out.println("Comment broker started...");
			cb.importCommentary();
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return "redirect:/";
		
	}
	
	@GetMapping("/admin/downloadComments")
	public ResponseEntity<byte[]> exportComments() throws IOException {
		
		CommentBroker commentBroker = new CommentBroker(commentaryRepository);
		
		byte[] excelComments = commentBroker.exportComments();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
		headers.setContentDispositionFormData("attachment", "comments_download.xlsx");
		
		return new ResponseEntity<>(excelComments, headers, HttpStatus.OK);
		
	}
	
	@PostMapping("/admin/uploadUsers")
	public String importUsers(@RequestPart("fileInput") MultipartFile file) {
		
		try {
			System.out.println(System.getProperty("os.name"));
			File directory = new File(UPLOAD_DIRECTORY);
			if(!directory.exists()) {
				System.out.println("Creating directory " + UPLOAD_DIRECTORY);
				directory.mkdir();
			}
			
			
			String filePath = UPLOAD_DIRECTORY + File.separator + file.getOriginalFilename();
			File destination = new File(filePath);
			
			System.out.println("Uploading file to " + destination.toString());
			file.transferTo(destination);
			
			System.out.println("Starting user broker...");
			UserBroker ub = new UserBroker(userRepository, commentaryRepository, destination.toString());
			System.out.println("User broker started...");
			ub.importUsers();
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return "redirect:/";
	}
	
	//todo: implement restoreDatabase
	@PostMapping("/admin/restoreDatabase")
	public String restoreDatabase(@RequestPart("fileInput") MultipartFile file) throws SQLException {
	
		/* change paths before production launch, make them programmable */
		final String jdbcUrl = "jdbc:h2:file:";
		
		
		try {
			System.out.println(System.getProperty("os.name"));
			File directory = new File(UPLOAD_DIRECTORY);
			if(!directory.exists()) {
				System.out.println("Creating directory " + UPLOAD_DIRECTORY);
				directory.mkdir();
			}
			
			
			String filePath = UPLOAD_DIRECTORY + File.separator + file.getOriginalFilename();
			File destination = new File(filePath);
			
			System.out.println("Uploading file to " + destination.toString());
			file.transferTo(destination);
			
			try(Connection conn = DriverManager.getConnection(jdbcUrl+filePath)){
				RunScript.execute(conn, new FileReader(filePath));
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
			catch(FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return "redirect:/";
		
	}
}

	
	
	
	
	