package com.java.crv.BibleReaderCommentary.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.crv.BibleReaderCommentary.domain.Verse;
import com.java.crv.BibleReaderCommentary.services.VerseService;

@Controller
public class VerseController {

	private VerseService verseService;
	
	public VerseController(VerseService verseService) {
		this.verseService = verseService;
	}
	
	@GetMapping("/public/fetchChapterVerses")
	@ResponseBody
	public List<Verse> fetchIndexVerses(@RequestParam Long chapterId){
		
		return verseService.getAllChapterVerses(chapterId);
		
	}
}
