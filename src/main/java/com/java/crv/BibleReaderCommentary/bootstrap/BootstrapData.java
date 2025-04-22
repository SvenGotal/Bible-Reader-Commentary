package com.java.crv.BibleReaderCommentary.bootstrap;

import java.io.File;
import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.java.crv.BibleReaderCommentary.domain.Bible;
import com.java.crv.BibleReaderCommentary.domain.Commentary;
import com.java.crv.BibleReaderCommentary.domain.User;
import com.java.crv.BibleReaderCommentary.domain.UserRoles;
import com.java.crv.BibleReaderCommentary.objects.BibleImporter;
import com.java.crv.BibleReaderCommentary.repositories.BibleRepository;
import com.java.crv.BibleReaderCommentary.repositories.ChapterRepository;
import com.java.crv.BibleReaderCommentary.repositories.CommentaryRepository;
import com.java.crv.BibleReaderCommentary.repositories.UserRepository;

@Component
@EnableJpaRepositories
public class BootstrapData implements CommandLineRunner{

	private final UserRepository userRepo;
	private final BibleRepository bibleRepo;
	private final BCryptPasswordEncoder encoder;
	
	public BootstrapData(UserRepository userRepo, BibleRepository bibleRepo, ChapterRepository chapterRepo, CommentaryRepository commentRepo, BCryptPasswordEncoder encoder) {
		this.userRepo = userRepo;
		this.bibleRepo = bibleRepo;
		this.encoder = encoder;
		
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		//todo remove all except owner for production purposes
		
		//check working dir
		String working_dir = System.getProperty("user.dir");
		System.out.println("Working directory: " + working_dir);
		
		System.out.println("Commandline runner running....");
		
		
		String fileForBibleReader = "/home/sven/WordProject_Bible.xlsx";
		File filepath = new File(fileForBibleReader);
		
		if(!filepath.exists()) {
			fileForBibleReader = "/home/sveng/WordProject_Bible.xlsx";
			filepath = new File(fileForBibleReader);
			
		}
		
		BibleImporter reader = null;
		Bible bible;
		
		if(bibleRepo.count() == 0) {
			reader = new BibleImporter(filepath.toString());
			bible = reader.loadBible(0);
			bibleRepo.save(bible);
		}
		
		if(userRepo.count() == 0) {
			User admin = new User();
			admin.setId(1000L);
			admin.setUsername("admin");
			admin.setPassword(encoder.encode("admin"));
			admin.setRole(UserRoles.ADMIN);
			admin.setComments(new ArrayList<Commentary>());						
			
			System.out.println("Saving data....");		
			userRepo.save(admin);
			System.out.println("Number of inserted rows: " + userRepo.count());
			System.out.println(userRepo.findAll().toString()); 
	
		}
		
		/* Add simple user for testing */
		if(userRepo.findByUsername("user") == null) {
			User user = new User();
			//user.setId(2000L);
			user.setUsername("user");
			user.setPassword(encoder.encode("user"));
			user.setRole(UserRoles.USER);
			user.setComments(new ArrayList<Commentary>());
			userRepo.save(user);
		}
		
				
	}
}
