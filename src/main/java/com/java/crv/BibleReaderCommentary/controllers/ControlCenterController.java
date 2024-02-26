package com.java.crv.BibleReaderCommentary.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.java.crv.BibleReaderCommentary.domain.Bible;
import com.java.crv.BibleReaderCommentary.objects.BibleLoader;
import com.java.crv.BibleReaderCommentary.repositories.BibleRepository;
import com.java.crv.BibleReaderCommentary.repositories.CommentaryRepository;

@Controller
public class ControlCenterController {

	private CommentaryRepository commentaryRepository;
	private BibleRepository bibleRepository;
	private static final String UPLOAD_DIRECTORY = "/home/sven/uploads1"; //prepare path before deployment.
	private final ResourceLoader resourceLoader;
	
	public ControlCenterController(CommentaryRepository commentaryRepository, BibleRepository bibleRepository, ResourceLoader resourceLoader) {
		this.commentaryRepository = commentaryRepository;
		this.bibleRepository = bibleRepository;
		this.resourceLoader = resourceLoader;
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
			
			BibleLoader bl = new BibleLoader(destination.toString());
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
	
}
