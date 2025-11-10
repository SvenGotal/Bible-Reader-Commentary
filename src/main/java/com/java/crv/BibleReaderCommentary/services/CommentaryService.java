package com.java.crv.BibleReaderCommentary.services;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.java.crv.BibleReaderCommentary.domain.Commentary;
import com.java.crv.BibleReaderCommentary.exceptions.CommentaryNotFoundException;
import com.java.crv.BibleReaderCommentary.exceptions.CommentaryParameterBindingException;
import com.java.crv.BibleReaderCommentary.repositories.CommentaryRepository;

@Service
public class CommentaryService {
	
	private CommentaryRepository commentaryRepository;
	
	public CommentaryService(CommentaryRepository commentaryRepository) {
		this.commentaryRepository = commentaryRepository;
	}
	
	/**
	 * Gets literally every Commentary element from the repository.
	 * @return List of all Commentary elements that exist
	 * */
	public List<Commentary> getAllPublicAndPrivateCommentary(){
		return getAllCommentaryFromRepository();
	}

	/**
	 * Gets only public Commentary elements.
	 * @return List of all public Commentary in the database
	 * */
	public List<Commentary> getAllPublicCommentary(){	
		return getAllCommentaryFromRepository().stream().filter( (Commentary comment) -> comment.getPublished()).toList();
	}
	
	/**
	 * Gets only private Commentary elements.
	 * @return List of private user Commentary
	 * */
	public List<Commentary> getAllPrivateCommentary(){
		return getAllCommentaryFromRepository().stream().filter((Commentary comment) -> !comment.getPublished()).toList();
	}
	
	/**
	 * Gets all the private Commentary elements of a specific user.
	 * @return List of private comments
	 * */
	public List<Commentary> getAllUsersPrivateCommentary(Long userId){
		return commentaryRepository.findAllByUserId(userId);		
	}
	
	/**
	 * Gets all public comments and also user's own private comments based on user's id.
	 * @return List of all public and user private Commentary elements
	 * */
	public List<Commentary> getAllUsersPrivateAndAllPublicCommentary(Long userId){
		Predicate<Commentary> isPublished = comment -> comment.getPublished();
		Predicate<Commentary> belongsToUser = comment -> comment.getUser().getId() == userId;
		return getAllCommentaryFromRepository()
				.stream()
				.filter(isPublished.or(belongsToUser))
				.toList();
	}
	
	/**
	 * Filters comments contained by a specific chapter via Predicate.
	 * @return List of comments filtered by one predicate
	 * */
	public List<Commentary> getFilteredCommentary(Long chapterId, Predicate<Commentary> predicate){
		return commentaryRepository.findAllByChapterId(chapterId).stream().filter(predicate).toList();
	}
	
	/**
	 * Filters comments contained by a specific chapter via two predicates.
	 * @return List of filtered comments
	 * */
	public List<Commentary> getFilteredCommentary(Long chapterId, Predicate<Commentary> predicate1, Predicate<Commentary> predicate2){
		return commentaryRepository.findAllByChapterId(chapterId).stream().filter(predicate1.or(predicate2)).toList();
	}
	
	/**
	 * Filters all chapters that specific Book has by it's id. Also picks up comments that contain user's id.
	 * @return List of Commentary
	 * */
	public List<Commentary> getFilteredCommentary(Long bookId, Long userId, Predicate<Commentary> predicate1, Predicate<Commentary> predicate2){
		return getAllCommentaryFromRepositoryByBookId(bookId).stream().filter(predicate1.or(predicate2)).toList();
	}
	
	/**
	 * Gets all Commentary by their user's id
	 * @return List of Commentary
	 * */
	public List<Commentary> getUsersCommentaryById(Long userId){		
		return commentaryRepository.findAllByUserId(userId);		
	}
	
	/**
	 * Gets one specific comment, searches by it's id
	 * @return single Commentary
	 * */
	public Commentary getCommentaryById(Long id) {
		return commentaryRepository.findById(id).orElseThrow(() -> new CommentaryNotFoundException("Comment with id: " + id + " not found..."));
	}
	
	/**
	 * Saves a Commentary into the database.
	 * @return true if Commentary has been saved to the database, false if not.
	 * */
	public Boolean saveCommentary(Commentary cmnt) {
		
		if(!StringUtils.hasText(cmnt.getSubject())) throw new CommentaryParameterBindingException("Comment subject is empty or null...");
		if(!StringUtils.hasText(cmnt.getText())) throw new CommentaryParameterBindingException("Comment content is empty, it contains no text or less than 20 characters...");
		if(!StringUtils.hasText(cmnt.getAuthor())) throw new CommentaryParameterBindingException("Comment author is unknown, user is either null or author field failed to set...");
				
		if(commentaryRepository.save(cmnt) == null){
			return false;
		}		
		return true;
	}
	
	/**
	 * Deletes the Commentary element from the database.
	 * @return false if Commentary persists, true if not found after deletion
	 * */
	public Boolean deleteCommentaryById(Long id) {
		commentaryRepository.deleteById(id);
		
		if(commentaryRepository.findById(id).isPresent()) {
			return false;
		}
		return true;
	}
	
	private List<Commentary> getAllCommentaryFromRepository(){
		return StreamSupport.stream(commentaryRepository.findAll().spliterator(), false).toList();
	}
	
	private List<Commentary> getAllCommentaryFromRepositoryByBookId(Long bookId){
		return StreamSupport.stream(commentaryRepository.findAll().spliterator(), false).filter(comment -> comment.getChapter().getBook().getId() == bookId).toList();
	}
	
}








