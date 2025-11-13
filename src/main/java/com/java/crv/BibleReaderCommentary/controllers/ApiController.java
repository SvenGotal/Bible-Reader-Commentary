package com.java.crv.BibleReaderCommentary.controllers;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.crv.BibleReaderCommentary.domain.Chapter;
import com.java.crv.BibleReaderCommentary.repositories.ChapterRepository;
import com.java.crv.BibleReaderCommentary.repositories.VerseRepository;

@Controller
public class ApiController {
	

	private ChapterRepository chapterRepository;

	
	public ApiController (ChapterRepository chapterRepository, VerseRepository verseRepository) 
	{

		this.chapterRepository = chapterRepository;

	}	
	
	
				
	
	@GetMapping("/public/fetchAllChapters")
	@ResponseBody
	public List<Chapter> fetchFilteredComments(@RequestParam Long chapterId){
		return chapterRepository.findAllById(chapterId);
	}
	
	
}
