package com.java.crv.BibleReaderCommentary.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.java.crv.BibleReaderCommentary.domain.User;
import com.java.crv.BibleReaderCommentary.domain.UserRoles;
import com.java.crv.BibleReaderCommentary.objects.XMLReader;
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
		
		//todo remove all except owner for production purposes
		
		System.out.println("Commandline runner running....");
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		User owner = new User();
		owner.setId(2000L);
		owner.setUsername("owner");
		owner.setEmail("sven.gotal@gmail.com");
		owner.setPassword(encoder.encode("sveng"));
		owner.setRole(UserRoles.OWNER);
		
		User admin = new User();
		admin.setId(1000L);
		admin.setUsername("admin");
		admin.setPassword(encoder.encode("admin"));
		admin.setRole(UserRoles.ADMIN);
		
		User simple = new User();
		simple.setId(109L);
		simple.setUsername("simpleuser");
		simple.setPassword(encoder.encode("password"));
		simple.setRole(UserRoles.USER);
		
		System.out.println("Saving data....");		
		userRepo.save(owner);
		userRepo.save(admin);
		userRepo.save(simple);
		System.out.println("Number of inserted rows: " + userRepo.count());
		System.out.println(userRepo.findAll().toString()); 
		
	
	}
}
