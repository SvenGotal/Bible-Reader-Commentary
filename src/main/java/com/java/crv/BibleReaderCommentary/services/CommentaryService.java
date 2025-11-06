package com.java.crv.BibleReaderCommentary.services;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.java.crv.BibleReaderCommentary.domain.Commentary;
import com.java.crv.BibleReaderCommentary.exceptions.CommentaryNotFoundException;
import com.java.crv.BibleReaderCommentary.repositories.CommentaryRepository;

@Service
public class CommentaryService {
	
	private CommentaryRepository commentaryRepository;
	
	public CommentaryService(CommentaryRepository commentaryRepository) {
		this.commentaryRepository = commentaryRepository;
	}

	public List<Commentary> getAllPublicCommentary(){	
		return getAllCommentaryFromRepository();
	}
	
	public List<Commentary> getFilteredCommentary(Predicate<Commentary> predicate){
		return getAllCommentaryFromRepository().stream().filter(predicate).toList();
	}
	
	public List<Commentary> getFilteredCommentary(Predicate<Commentary> predicate1, Predicate<Commentary> predicate2){
		return getAllCommentaryFromRepository().stream().filter(predicate1.or(predicate2)).toList();
	}
	
	public List<Commentary> getUsersCommentaryById(Long userId){		
		return commentaryRepository.findAllByUserId(userId);		
	}
	
	public Commentary getCommentaryById(Long id) {
		return commentaryRepository.findById(id).orElseThrow(() -> new CommentaryNotFoundException("Comment with id: " + id + " not found..."));
	}
	
	
	public void saveCommentary(Commentary cmnt) {
		commentaryRepository.save(cmnt);
	}
	
	public void deleteCommentaryById(Long id) {
		commentaryRepository.deleteById(id);
	}
	
	private List<Commentary> getAllCommentaryFromRepository(){
		return StreamSupport.stream(commentaryRepository.findAll().spliterator(), false).toList();
	}
	
}








