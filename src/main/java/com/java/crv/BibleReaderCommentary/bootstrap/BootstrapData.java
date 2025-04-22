package com.java.crv.BibleReaderCommentary.bootstrap;

import java.io.File;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
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
	
	/*Fetches the initial data from application.properties file*/
	@Value("${app.seedUser.username}")
	private String seedAdminUsername;
	
	@Value("${app.seedUser.password}")
	private String seedAdminPassword;
	
	@Value("${app.bibleFile.path}")
	private String bibleFilePath;
	/*---------------------------------------------------------*/
	
	public BootstrapData(UserRepository userRepo, BibleRepository bibleRepo, ChapterRepository chapterRepo, CommentaryRepository commentRepo, BCryptPasswordEncoder encoder) {
		this.userRepo = userRepo;
		this.bibleRepo = bibleRepo;
		this.encoder = encoder;
		
	}
	
	@Override
	public void run(String... args) throws Exception {
				
		//check working dir
		String working_dir = System.getProperty("user.dir");
		System.out.println("Bible directory: " + working_dir);
		
		System.out.println("Commandline runner running....");
		
		String fileForBibleReader = this.bibleFilePath;
		System.out.println("Filepath string initialized...");
		File filepath = new File(fileForBibleReader);
		
		if(!filepath.exists()) {
			System.out.println("File found...");
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
		
		/*Insert the first admin user*/
		if(userRepo.count() == 0) {
			System.out.println("User database is empty...");
			User admin = new User();
			admin.setUsername(this.seedAdminUsername);
			admin.setPassword(encoder.encode(this.seedAdminPassword));
			admin.setRole(UserRoles.ADMIN);
			admin.setComments(new ArrayList<Commentary>());						
			userRepo.save(admin);
			System.out.println("Inserting seed user...");
		}
		
		/* Add simple user for testing */
		if(userRepo.findByUsername("tester") == null) {
			User user = new User();
			user.setUsername("tester");
			user.setPassword(encoder.encode("tester"));
			user.setRole(UserRoles.USER);
			user.setComments(new ArrayList<Commentary>());
			userRepo.save(user);
		}
		
				
	}
}
