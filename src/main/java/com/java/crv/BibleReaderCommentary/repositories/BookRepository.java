package com.java.crv.BibleReaderCommentary.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.java.crv.BibleReaderCommentary.domain.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long>{

}
