package com.java.crv.BibleReaderCommentary.services;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
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
	
	/**
	 * Get all Chapter elements that contain Commentary elements in their Commentary list
	 * @return list of Chapters
	 * */
	public List<Chapter> getAllChaptersThatContainComments(){
		return StreamSupport.stream(chapterRepository.findAll().spliterator(), false).filter(chapter -> !chapter.getComments().isEmpty()).toList();
	}
	
	/**
	 * Gets all chapters by their Book id that contain comments
	 * @return list of Chapters
	 * */
	public List<Chapter> getChaptersThatContainCommentsByBookId(Long bookId){
		
		Set<Chapter> setOfChaptersWithComments = new LinkedHashSet<Chapter>();
		List<Chapter> listOfAllChapters = chapterRepository.findByBookId(bookId).stream().filter(chapter -> !chapter.getComments().isEmpty()).toList();
		listOfAllChapters.forEach(chapter -> setOfChaptersWithComments.add(chapter));
		
		
		return setOfChaptersWithComments.stream().collect(Collectors.toList());
	}
	
	/**
	 * Gets all chapters that are public and if the Chapter contains at least one public comment
	 * @return List of Chapters with at least one public comment
	 * */
	public List<Chapter> getChaptersThatContainOnlyPublicComments(Long bookId){
		return chapterRepository.findByBookId(bookId)
				.stream()
				.filter(chapter -> !chapter.getComments().isEmpty())
				.filter(chapter -> chapter.getComments().stream().anyMatch(comment -> comment.getPublished())).toList();
	}
	
	/**
	 * 
	 * Gets all chapters that contain all public comments and all users private comments
	 * @return List of chapters with public and user's private commentary
	 * */
	public List<Chapter> getChaptersThatContainPublicAndAllUsersComments(Long bookId, Long userId){

		Predicate<Commentary> isPublished = comment -> comment.getPublished();
		Predicate<Commentary> belongsToUser = comment -> comment.getUser().getId() == userId;
		
		Set<Chapter> setOfChapters = commentaryService.getFilteredCommentary(bookId, userId, isPublished, belongsToUser)
				.stream()
				.map(Commentary::getChapter)
				.filter(chapt -> chapt.getBook().getId() == bookId)
				.collect(Collectors.toSet());
								
		return setOfChapters.stream().collect(Collectors.toList());
		
	}
}
