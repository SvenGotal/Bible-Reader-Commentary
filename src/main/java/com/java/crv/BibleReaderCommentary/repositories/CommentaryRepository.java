package com.java.crv.BibleReaderCommentary.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.java.crv.BibleReaderCommentary.domain.Commentary;
import com.java.crv.BibleReaderCommentary.domainDTO.CommentaryRecord;

public interface CommentaryRepository extends CrudRepository<Commentary, Long> {
	List<Commentary> findAllByPublished(Boolean published);
	List<Commentary> findAllCommentaryById(Long commentaryId);
	List<Commentary> findAllByUserId(Long userId);
	List<Commentary> findAllByChapterId(Long chapterId);
	void deleteByUser_Id(Long userId);
	
	@Query("""
			select new com.java.crv.BibleReaderCommentary.domainDTO.CommentaryRecord(
				cmnt.id,
				cmnt.text,
				cmnt.subject,
				cmnt.dateOfCreation,
				usr.username,
				chpt.number,
				bk.name
			)
			from Commentary cmnt
			join cmnt.chapter chpt
			join chpt.book bk
			join cmnt.user usr
			where cmnt.published=true and bk.id=:bookId
			order by cmnt.dateOfCreation
			""")
	List<CommentaryRecord> getAllOnlyPublicCommentaryRecordsByBookId(@Param("bookId") Long bookId);
	
}
