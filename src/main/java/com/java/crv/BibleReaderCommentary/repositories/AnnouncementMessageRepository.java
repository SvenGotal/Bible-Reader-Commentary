package com.java.crv.BibleReaderCommentary.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.java.crv.BibleReaderCommentary.domain.AnnouncementMessage;

@Repository
public interface AnnouncementMessageRepository extends CrudRepository<AnnouncementMessage, Long>{
	public List<AnnouncementMessage> findAllByAnnouncementIsActive(Boolean announcementIsActive);
}
