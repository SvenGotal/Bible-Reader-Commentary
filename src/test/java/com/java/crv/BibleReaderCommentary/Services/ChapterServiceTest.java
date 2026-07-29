package com.java.crv.BibleReaderCommentary.Services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.java.crv.BibleReaderCommentary.domain.Chapter;
import com.java.crv.BibleReaderCommentary.domain.Commentary;
import com.java.crv.BibleReaderCommentary.repositories.ChapterRepository;
import com.java.crv.BibleReaderCommentary.services.ChapterService;

@ExtendWith(MockitoExtension.class)
class ChapterServiceTest {

	@Mock
	private ChapterRepository chapterRepository;
	
	@InjectMocks
	private ChapterService chapterService;
	
	@Test
	void listShouldReturnOnlySortedChapters() {
		
		Commentary cmnt1 = new Commentary();
		cmnt1.setId(0l);
		
		Chapter ch1 = new Chapter();
		ch1.setId(1l);
		ch1.setNumber(4);
		ch1.setComments(List.of(cmnt1));	
		
		Chapter ch2 = new Chapter();
		ch2.setId(2l);
		ch2.setNumber(2);
		ch2.setComments(List.of(cmnt1));
		
		Chapter ch3 = new Chapter();
		ch3.setId(3l);
		ch3.setNumber(1);
		ch3.setComments(List.of(cmnt1));
		
		when(chapterRepository.findAll()).thenReturn(List.of(ch1,ch2,ch3));
		
		List<Chapter> result = chapterService.getAllChaptersThatContainComments();
		
		assertEquals(3, result.size());
		assertEquals(1, result.get(0).getNumber());
		assertEquals(2, result.get(1).getNumber());
		assertEquals(4, result.get(2).getNumber());
		
		verify(chapterRepository).findAll();
		
	}

}
