package com.java.crv.BibleReaderCommentary.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.java.crv.BibleReaderCommentary.domain.Verse;
import com.java.crv.BibleReaderCommentary.repositories.VerseRepository;

@Service
public class VerseService {

	private VerseRepository verseRepository;
	
	public VerseService(VerseRepository verseRepository) {
		this.verseRepository = verseRepository;
	}
	
	/**
	 * Gets all verses contained within the verse repository by Chapter id.
	 * @return List of Verse elements from the chapter
	 * */
	public List<Verse> getAllChapterVerses(Long chapterId){
		return verseRepository.findByChapterId(chapterId);
	}
}
