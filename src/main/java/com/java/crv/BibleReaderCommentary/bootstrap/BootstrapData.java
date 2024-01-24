package com.java.crv.BibleReaderCommentary.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.java.crv.BibleReaderCommentary.domain.User;
import com.java.crv.BibleReaderCommentary.repositories.UserRepository;

@Component
@EnableJpaRepositories
public class BootstrapData implements CommandLineRunner{

	private final UserRepository userRepo;
	
	public BootstrapData(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Commandline runner running....");
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		User admin = new User();
		admin.setId(1000L);
		admin.setUsername("admin");
		admin.setPassword(encoder.encode("admin"));
		System.out.println("Saving data....");
		userRepo.save(admin);
		System.out.println("Number of inserted rows: " + userRepo.count());
		System.out.println(userRepo.findAll().toString()); 
	}
}
