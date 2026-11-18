package com.java.crv.BibleReaderCommentary.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.java.crv.BibleReaderCommentary.domain.Verse;
import com.java.crv.BibleReaderCommentary.domainDTO.VerseWithChapterAndBookInfoDTO;
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
	
	public List<VerseWithChapterAndBookInfoDTO> getAllVersesThatMatchString(String match){
		return verseRepository.findByStringMatch(match);
	}
	
	/**
	 * Finds a random verse for the date and returns a single verse
	 * */
	public VerseWithChapterAndBookInfoDTO getVerseOfTheDay(Long id) {
		
		Optional<VerseWithChapterAndBookInfoDTO> optVerseOfTheDay = verseRepository.findVerseOfTheDay(id);
		if(optVerseOfTheDay.isEmpty()) {
			
		}
		return optVerseOfTheDay.get();
	}
}
