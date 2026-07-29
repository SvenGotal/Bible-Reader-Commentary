package com.java.crv.BibleReaderCommentary.repositories;

import org.springframework.data.repository.CrudRepository;
import com.java.crv.BibleReaderCommentary.domain.Bible;

public interface BibleRepository extends CrudRepository<Bible, Long>{

}
