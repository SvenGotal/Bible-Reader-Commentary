package com.java.crv.BibleReaderCommentary.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
	public Chapter getChapterById (Long chapterId){	
		return chapterRepository.findById(chapterId).orElseThrow(() -> new ChapterNotFoundException("Chapter with id: " + chapterId + " not found."));
	}
	
	/**
	 * Get all Chapters that contain id of a single Book (Long)
	 * @return list of Chapters
	 * */
	public List<Chapter> getChaptersByBookId(Long bookId){	
		return chapterRepository.findByBookId(bookId);
	}
	
	public List<Chapter> getAllChaptersThatContainComments(){
		return StreamSupport.stream(chapterRepository.findAll().spliterator(), false).filter(chapter -> !chapter.getComments().isEmpty()).toList();
	}
	
	public List<Chapter> getChaptersThatContainComments(Long bookId){
		
		Set<Chapter> setOfChaptersWithComments = new HashSet<Chapter>();
		List<Chapter> listOfAllChapters = chapterRepository.findByBookId(bookId);
		listOfAllChapters.forEach(chapter -> setOfChaptersWithComments.add(chapter));
		
		
		return setOfChaptersWithComments.stream().collect(Collectors.toList());
	}
	

}
