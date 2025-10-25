package com.java.crv.BibleReaderCommentary.services;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.java.crv.BibleReaderCommentary.domain.Commentary;
import com.java.crv.BibleReaderCommentary.repositories.CommentaryRepository;

@Service
public class CommentaryService {
	
	private CommentaryRepository commentaryRepository;
	
	public CommentaryService(CommentaryRepository commentaryRepository) {
		this.commentaryRepository = commentaryRepository;
	}

	public List<Commentary> getAllPublicCommentary(){	
		return fetchAllCommentsFromRepository();
	}
	
	public List<Commentary> getFilteredCommentary(Predicate<Commentary> predicate){
		return fetchAllCommentsFromRepository().stream().filter(predicate).toList();
	}
	
	public List<Commentary> getFilteredCommentary(Predicate<Commentary> predicate1, Predicate<Commentary> predicate2){
		return fetchAllCommentsFromRepository().stream().filter(predicate1.or(predicate2)).toList();
	}
	
	private List<Commentary> fetchAllCommentsFromRepository(){
		return StreamSupport.stream(commentaryRepository.findAll().spliterator(), false).toList();
	}
}








