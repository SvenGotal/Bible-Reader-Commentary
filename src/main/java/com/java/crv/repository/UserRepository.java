package com.java.crv.repository;

import org.springframework.data.repository.CrudRepository;
import com.java.crv.domain.User;

public interface UserRepository extends CrudRepository<User, Integer>{

}
