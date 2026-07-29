package com.java.crv.BibleReaderCommentary.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.java.crv.BibleReaderCommentary.domain.AnnouncementMessage;

public interface AnnouncementMessageRepository extends CrudRepository<AnnouncementMessage, Long>{
	public List<AnnouncementMessage> findAllByAnnouncementIsActive(Boolean announcementIsActive);
}
