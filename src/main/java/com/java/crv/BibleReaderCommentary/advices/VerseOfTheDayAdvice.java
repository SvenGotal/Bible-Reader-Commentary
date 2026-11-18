package com.java.crv.BibleReaderCommentary.advices;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.java.crv.BibleReaderCommentary.domainDTO.VerseWithChapterAndBookInfoDTO;
import com.java.crv.BibleReaderCommentary.services.VerseService;

@ControllerAdvice
public class VerseOfTheDayAdvice {
	
	private VerseService verseService;
	
	public VerseOfTheDayAdvice(VerseService verseService) {
		this.verseService = verseService;
	}

	@ModelAttribute
	public void displayVerseOfTheDay(Model model) {
		
		LocalDate currentDate = LocalDate.now(ZoneId.of("Europe/Zagreb"));
		Long verseIndex = new Random(currentDate.toEpochDay()).nextLong(31099l);
		
		VerseWithChapterAndBookInfoDTO verseOfTheDay = verseService.getVerseOfTheDay(verseIndex);
		
		model.addAttribute("verseOfTheDay", verseOfTheDay);
	}
}
