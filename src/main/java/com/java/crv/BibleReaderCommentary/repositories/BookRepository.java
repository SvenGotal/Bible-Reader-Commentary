package com.java.crv.BibleReaderCommentary.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.java.crv.BibleReaderCommentary.domain.Book;

public interface BookRepository extends CrudRepository<Book, Long>{
	public List<Book> findAll();
}
