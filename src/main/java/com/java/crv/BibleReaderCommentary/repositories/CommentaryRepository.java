package com.java.crv.BibleReaderCommentary.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.java.crv.BibleReaderCommentary.domain.Commentary;

@Repository
public interface CommentaryRepository extends CrudRepository<Commentary, Long> {

}
