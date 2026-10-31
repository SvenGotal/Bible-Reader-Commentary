package com.java.crv.BibleReaderCommentary.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.java.crv.BibleReaderCommentary.domain.Verse;
import com.java.crv.BibleReaderCommentary.domainDTO.VerseWithChapterAndBookInfoDTO;

public interface VerseRepository extends CrudRepository<Verse, Long>{
	List<Verse> findByChapterId(Long chapterId);
	
	@Query("""
			select new com.java.crv.BibleReaderCommentary.domainDTO.VerseWithChapterAndBookInfoDTO(
				verse.id,
				verse.number,
				verse.text,
				chapter.number,
				book.name
			)
			from Verse verse
			join verse.chapter chapter
			join chapter.book book
			where lower(verse.text) like lower(concat('%',:match,'%'))
			order by book.id, chapter.number, verse.number			
			""")
	List<VerseWithChapterAndBookInfoDTO> findByStringMatch(@Param("match") String match);
		

}
