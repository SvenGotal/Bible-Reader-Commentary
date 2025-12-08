package com.java.crv.BibleReaderCommentary.services;

import java.util.List;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Service;
import com.java.crv.BibleReaderCommentary.domain.UserFeedback;
import com.java.crv.BibleReaderCommentary.repositories.UserFeedbackRepository;

@Service
public class UserFeedbackService {

	private UserFeedbackRepository userFeedbackRepository;
	
	public UserFeedbackService (UserFeedbackRepository userFeedbackRepository) {
		this.userFeedbackRepository = userFeedbackRepository;
	}
	
	public List<UserFeedback> getAllUserFeedbackInDatabase(){
		return StreamSupport.stream(userFeedbackRepository.findAll().spliterator(), false).toList();
	}
	
	public boolean saveUserFeedback(UserFeedback uf) {
		UserFeedback saved = userFeedbackRepository.save(uf);
		return saved.getId() != null;		
	}
}
