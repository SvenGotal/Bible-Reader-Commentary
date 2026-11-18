package com.java.crv.BibleReaderCommentary.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.java.crv.BibleReaderCommentary.domain.User;


public interface UserRepository extends CrudRepository<User, Long>{
	User findByUsername(String username);
	Boolean existsByUsername(String username);
	User findByEmail(String email);
	Boolean existsByEmail(String email);
	Optional<User> findById(Long id);
	
	@Modifying
	@Query("UPDATE User u SET u.password = :newPassword WHERE u.id = :userId")
	int updatePasswordById(@Param("userId") Long userId, @Param("newPassword") String newPassword);
}
