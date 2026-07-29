package com.java.crv.BibleReaderCommentary.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.java.crv.BibleReaderCommentary.domain.Verse;

public interface VerseRepository extends CrudRepository<Verse, Long>{
	List<Verse> findByChapterId(Long chapterId);
}
