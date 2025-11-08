package com.java.crv.BibleReaderCommentary.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.crv.BibleReaderCommentary.domain.Chapter;
import com.java.crv.BibleReaderCommentary.services.ChapterService;

@Controller
public class ChapterController {
	
	private ChapterService chapterService;
	
	public ChapterController(ChapterService chapterService) {
		this.chapterService = chapterService;
	}

	
	@GetMapping({"/submitComment/fetchChapters", "/public/fetchChapters"})
	@ResponseBody
	public List<Chapter> fetchChapters(@RequestParam Long bookId, @RequestParam(required = false, defaultValue = "false") Boolean filterCommented){
		
		if(filterCommented) {
			return chapterService.getChaptersThatContainComments(bookId);
		}
		
		return chapterService.getChaptersByBookId(bookId);
		
	}
}
