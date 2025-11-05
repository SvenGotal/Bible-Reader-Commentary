package com.java.crv.BibleReaderCommentary.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.java.crv.BibleReaderCommentary.domain.Chapter;
import com.java.crv.BibleReaderCommentary.exceptions.ChapterNotFoundException;
import com.java.crv.BibleReaderCommentary.repositories.ChapterRepository;

@Service
public class ChapterService {
	
	private ChapterRepository chapterRepository;
	
	public ChapterService(ChapterRepository chapterRepository) {
		
		this.chapterRepository = chapterRepository;
	}

	public Chapter getChapterById (Long id){
		
		return chapterRepository.findById(id).orElseThrow(() -> new ChapterNotFoundException("Chapter with id: " + id + " not found."));
	}
	
	
}
