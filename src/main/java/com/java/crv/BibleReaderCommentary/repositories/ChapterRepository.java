package com.java.crv.BibleReaderCommentary.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.java.crv.BibleReaderCommentary.domain.Chapter;

public interface ChapterRepository extends CrudRepository<Chapter, Long>{
	List<Chapter> findByBookId(Long bookId);
	List<Chapter> findAllById(Long chapterId);
	Optional<Chapter> findById(Long chapterId);
	//List<Chapter> findAllByOrderByNumberAsc(); /*This logic is done in the service layer for this entity*/
}
