package com.java.crv.BibleReaderCommentary.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.java.crv.BibleReaderCommentary.domain.*;

public interface UserRepository extends CrudRepository<User, Long>{
	User findByUsername(String username);
	Boolean existsByUsername(String username);
	User findByEmail(String email);
	Boolean existsByEmail(String email);
	Optional<User> findById(Long id);
}
