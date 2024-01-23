package com.java.crv.BibleReaderCommentary.bootstrap;

import org.springframework.boot.CommandLineRunner;

import com.java.crv.BibleReaderCommentary.domain.User;
import com.java.crv.BibleReaderCommentary.repositories.UserRepository;

public class BootstrapData implements CommandLineRunner{

	private final UserRepository userRepo;
	
	public BootstrapData(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	
	@Override
	public void run(String... args) throws Exception {
		User admin = new User();
		admin.setUsername("admin");
		admin.setPassword("admin");
		userRepo.save(admin);
	}
}
