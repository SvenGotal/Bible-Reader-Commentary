package com.java.crv.BibleReaderCommentary.repositories;

import java.util.List;

import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.java.crv.BibleReaderCommentary.domain.Commentary;

@Repository
@Table
public interface CommentaryRepository extends CrudRepository<Commentary, Long> {
	List<Commentary> findAllByPublished(Boolean published);
	List<Commentary> findCommentaryById(Long commentaryId);
}
