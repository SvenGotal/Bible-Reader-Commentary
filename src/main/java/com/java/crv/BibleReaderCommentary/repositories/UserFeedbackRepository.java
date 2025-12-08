package com.java.crv.BibleReaderCommentary.repositories;

import org.springframework.stereotype.Repository;

import com.java.crv.BibleReaderCommentary.domain.UserFeedback;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface UserFeedbackRepository extends CrudRepository<UserFeedback, Long>{

}
