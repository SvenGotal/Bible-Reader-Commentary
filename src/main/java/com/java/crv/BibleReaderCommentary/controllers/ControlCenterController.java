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

import com.java.crv.BibleReaderCommentary.repositories.CommentaryRepository;

@Controller
public class ControlCenterController {

	private CommentaryRepository comments;
	private static final String UPLOAD_DIRECTORY = "uploads";
	private final ResourceLoader resourceLoader;
	
	public ControlCenterController(CommentaryRepository comments, ResourceLoader resourceLoader) {
		this.comments = comments;
		this.resourceLoader = resourceLoader;
	}
	
	@GetMapping("/admin/controlCenter")
	public String getCCForm() {
		
		return "forms/controlcenter";
		
	}
	
	@PostMapping ("/admin/uploadBible")
	public String uploadBible(@RequestPart("fileInput") MultipartFile file) {
		
		//todo use https://spring.io/guides/gs/uploading-files guid. Current setup is not working
		
		try {
			ClassPathResource classPathResource = new ClassPathResource("uploads");
			//Resource resource = resourceLoader.getResource("classpath:/uploads");
			//System.out.println(classPathResource.getFile().getAbsolutePath());
			
			if(!classPathResource.exists()) {
				System.out.println("resource not found!");
				return "redirect:/";
			}
			
			//File directory = resource.getFile();
			File directory = classPathResource.getFile();
			
			
			
			if(!directory.exists()) {
				Files.createDirectories(directory.toPath());
			}
			
			
			
			String filepath = directory.getAbsolutePath() + File.separator + file.getName();
			
			
			File destination = new File(filepath);
			
			
			file.transferTo(destination);
			System.out.println(new ClassPathResource("").getFile().getAbsolutePath());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return "redirect:/";
	}
	
}
