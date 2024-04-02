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
	//private final ChapterRepository chapterRepo;
	//private final CommentaryRepository commentRepo;
	
	public BootstrapData(UserRepository userRepo, BibleRepository bibleRepo, ChapterRepository chapterRepo, CommentaryRepository commentRepo) {
		this.userRepo = userRepo;
		this.bibleRepo = bibleRepo;
		//this.chapterRepo = chapterRepo;
		//this.commentRepo = commentRepo;
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		//todo remove all except owner for production purposes
		
		System.out.println("Commandline runner running....");
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		String fileForBibleReader = "/home/sven/WordProject_Bible.xlsx";
		File filepath = new File(fileForBibleReader);
		
		if(!filepath.exists()) {
			fileForBibleReader = "/home/sveng/WordProject_Bible.xlsx";
			filepath = new File(fileForBibleReader);
			
			if(!filepath.exists()) {
				fileForBibleReader = "C:\\Users\\sgotal\\Downloads\\WordProject_Bible.xlsx";
				filepath = new File(fileForBibleReader);
			}
		}
		
		BibleImporter reader = new BibleImporter(filepath.toString());
		Bible bible = reader.loadBible(0);
		bibleRepo.save(bible);
		
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
				
	}
}
