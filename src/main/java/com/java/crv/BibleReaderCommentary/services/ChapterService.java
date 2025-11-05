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

	/**
	 * Get Chapter by it's id (Long)
	 * @return single Chapter if found
	 * */
	public Chapter getChapterById (Long id){	
		return chapterRepository.findById(id).orElseThrow(() -> new ChapterNotFoundException("Chapter with id: " + id + " not found."));
	}
	
	/**
	 * Get all Chapters that contain id of a single Book (Long)
	 * @return list of Chapters
	 * */
	public List<Chapter> getChaptersByBookId(Long id){	
		return chapterRepository.findByBookId(id);
	}
}
