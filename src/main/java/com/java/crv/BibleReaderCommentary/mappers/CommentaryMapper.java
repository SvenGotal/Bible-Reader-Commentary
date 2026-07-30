package com.java.crv.BibleReaderCommentary.mappers;

import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.java.crv.BibleReaderCommentary.domain.Commentary;
import com.java.crv.BibleReaderCommentary.domainDTO.CommentaryDTO;

@Component
public class CommentaryMapper {

	private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/uuuu");
	
	public CommentaryDTO toDto(Commentary entity) {
		if(entity == null)
			return null;
		
		CommentaryDTO cdto = new CommentaryDTO();
		cdto.setId(entity.getId());
		cdto.setSubject(entity.getSubject());
		cdto.setPublished(entity.getPublished());
		cdto.setText(entity.getText());
		cdto.setDateOfCreation(entity.getDateOfCreation().format(format));
		cdto.setAuthor(entity.getAuthor());
		cdto.setUserId(entity.getUser().getId());
		cdto.setChapterId(entity.getChapter().getId());
		
		return cdto;
	}
	
	public Commentary toEntity(CommentaryDTO cdto) {
		//TODO: stub
		return null;
	}
}
