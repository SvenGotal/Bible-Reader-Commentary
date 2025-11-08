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
	
	public List<Commentary> getAllPublicAndPrivateCommentary(){
		return getAllCommentaryFromRepository();
	}

	public List<Commentary> getAllPublicCommentary(){	
		return getAllCommentaryFromRepository().stream().filter( (Commentary comment) -> comment.getPublished()).toList();
	}
	
	public List<Commentary> getAllPrivateCommentary(){
		return getAllCommentaryFromRepository().stream().filter((Commentary comment) -> !comment.getPublished()).toList();
	}
	
	public List<Commentary> getFilteredCommentary(Long chapterId, Predicate<Commentary> predicate){
		return commentaryRepository.findAllByChapterId(chapterId).stream().filter(predicate).toList();
	}
	
	public List<Commentary> getFilteredCommentary(Long chapterId, Predicate<Commentary> predicate1, Predicate<Commentary> predicate2){
		return commentaryRepository.findAllByChapterId(chapterId).stream().filter(predicate1.or(predicate2)).toList();
	}
	
	public List<Commentary> getUsersCommentaryById(Long userId){		
		return commentaryRepository.findAllByUserId(userId);		
	}
	
	public Commentary getCommentaryById(Long id) {
		return commentaryRepository.findById(id).orElseThrow(() -> new CommentaryNotFoundException("Comment with id: " + id + " not found..."));
	}
	
	
	public void saveCommentary(Commentary cmnt) {
		
		if(!StringUtils.hasText(cmnt.getSubject())) throw new CommentaryParameterBindingException("Comment subject is empty or null...");
		if(!StringUtils.hasText(cmnt.getText())) throw new CommentaryParameterBindingException("Comment content is empty, it contains no text or less than 20 characters...");
		if(!StringUtils.hasText(cmnt.getAuthor())) throw new CommentaryParameterBindingException("Comment author is unknown, user is either null or author field failed to set...");
		
		
		commentaryRepository.save(cmnt);
	}
	
	public void deleteCommentaryById(Long id) {
		commentaryRepository.deleteById(id);
	}
	
	private List<Commentary> getAllCommentaryFromRepository(){
		return StreamSupport.stream(commentaryRepository.findAll().spliterator(), false).toList();
	}
	
}








