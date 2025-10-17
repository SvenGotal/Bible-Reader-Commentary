package com.java.crv.BibleReaderCommentary.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.java.crv.BibleReaderCommentary.domain.Chapter;

@Repository
public interface ChapterRepository extends CrudRepository<Chapter, Long>{
	List<Chapter> findByBookId(Long bookId);
	List<Chapter> findAllById(Long chapterId);
}
