package com.java.crv.BibleReaderCommentary.repositories;

import java.util.List;

import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.java.crv.BibleReaderCommentary.domain.Verse;

@Repository
@Table
public interface VerseRepository extends CrudRepository<Verse, Long>{
	List<Verse> findByChapterId(Long chapterId);
}
