package com.java.crv.BibleReaderCommentary.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.crv.BibleReaderCommentary.domain.Chapter;
import com.java.crv.BibleReaderCommentary.domain.User;
import com.java.crv.BibleReaderCommentary.services.ChapterService;

@Controller
public class ChapterController {
	
	private ChapterService chapterService;
	
	public ChapterController(ChapterService chapterService) {
		this.chapterService = chapterService;
	}

	
	@GetMapping({"/submitComment/fetchChapters", "/public/fetchChapters"})
	@ResponseBody
	public List<Chapter> fetchChapters(@ModelAttribute("currentlyLoggedUser") User currentlyLoggedUser, @RequestParam Long bookId, @RequestParam(required = false, defaultValue = "false") Boolean filterCommented){
		

		if(currentlyLoggedUser != null & filterCommented)
		{
			return chapterService.getChaptersThatContainPublicAndAllUsersComments(bookId, currentlyLoggedUser.getId());
		}
		
		return chapterService.getChaptersThatContainOnlyPublicComments(bookId);
		
		
		
	}
}
