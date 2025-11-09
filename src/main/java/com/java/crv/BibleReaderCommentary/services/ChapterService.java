package com.java.crv.BibleReaderCommentary.services;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.java.crv.BibleReaderCommentary.domain.Chapter;
import com.java.crv.BibleReaderCommentary.domain.Commentary;
import com.java.crv.BibleReaderCommentary.exceptions.ChapterNotFoundException;
import com.java.crv.BibleReaderCommentary.repositories.ChapterRepository;

@Service
public class ChapterService {
	
	private ChapterRepository chapterRepository;
	private CommentaryService commentaryService;
	
	public ChapterService(ChapterRepository chapterRepository, CommentaryService commentaryService) {
		
		this.chapterRepository = chapterRepository;
		this.commentaryService = commentaryService;
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
	public List<Chapter> getAllChaptersByBookId(Long bookId){	
		return chapterRepository.findByBookId(bookId);
	}
	
	public List<Chapter> getAllChaptersThatContainComments(){
		return StreamSupport.stream(chapterRepository.findAll().spliterator(), false).filter(chapter -> !chapter.getComments().isEmpty()).toList();
	}
	
	public List<Chapter> getChaptersThatContainCommentsByBookId(Long bookId){
		
		Set<Chapter> setOfChaptersWithComments = new LinkedHashSet<Chapter>();
		List<Chapter> listOfAllChapters = chapterRepository.findByBookId(bookId).stream().filter(chapter -> !chapter.getComments().isEmpty()).toList();
		listOfAllChapters.forEach(chapter -> setOfChaptersWithComments.add(chapter));
		
		
		return setOfChaptersWithComments.stream().collect(Collectors.toList());
	}
	
	public List<Chapter> getChaptersThatContainOnlyPublicComments(Long bookId){
		return chapterRepository.findByBookId(bookId)
				.stream()
				.filter(chapter -> !chapter.getComments().isEmpty())
				.filter(chapter -> chapter.getComments().stream().anyMatch(comment -> comment.getPublished())).toList();
	}
	
	public List<Chapter> getChaptersThatContainPublicAndAllUsersComments(Long bookId, Long userId){
		
		List<Commentary> listOfAllUsersComments = commentaryService.getAllUsersPrivateAndAllPublicCommentary(userId);
		Set<Chapter> setOfChapters = listOfAllUsersComments
				.stream()
				.map(Commentary::getChapter)
				.filter(chapt -> chapt.getBook().getId() == bookId)
				.collect(Collectors.toSet());   // new LinkedHashSet<Chapter>();
								
		return setOfChapters.stream().collect(Collectors.toList());
		
	}
}
