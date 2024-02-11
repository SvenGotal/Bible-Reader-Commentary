package com.java.crv.BibleReaderCommentary.bootstrap;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.java.crv.BibleReaderCommentary.domain.Bible;
import com.java.crv.BibleReaderCommentary.domain.Chapter;
import com.java.crv.BibleReaderCommentary.domain.Commentary;
import com.java.crv.BibleReaderCommentary.domain.User;
import com.java.crv.BibleReaderCommentary.domain.UserRoles;
import com.java.crv.BibleReaderCommentary.objects.ExcelReader;
import com.java.crv.BibleReaderCommentary.repositories.BibleRepository;
import com.java.crv.BibleReaderCommentary.repositories.ChapterRepository;
import com.java.crv.BibleReaderCommentary.repositories.CommentaryRepository;
import com.java.crv.BibleReaderCommentary.repositories.UserRepository;

@Component
@EnableJpaRepositories
public class BootstrapData implements CommandLineRunner{

	private final UserRepository userRepo;
	private final BibleRepository bibleRepo;
	private final ChapterRepository chapterRepo;
	private final CommentaryRepository commentRepo;
	
	public BootstrapData(UserRepository userRepo, BibleRepository bibleRepo, ChapterRepository chapterRepo, CommentaryRepository commentRepo) {
		this.userRepo = userRepo;
		this.bibleRepo = bibleRepo;
		this.chapterRepo = chapterRepo;
		this.commentRepo = commentRepo;
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
		admin.setComments(new ArrayList<Commentary>());
		
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
		
		//ExcelReader reader = new ExcelReader("C:\\Users\\sgotal\\Downloads\\test.xlsx");
		ExcelReader reader = new ExcelReader("/home/sven/test.xlsx");
		Bible bible = reader.loadBible("WordProject");
		bibleRepo.save(bible);
		
		
		Commentary welcome = new Commentary();
		welcome.setId(1L);
		Optional<Chapter> chpt = chapterRepo.findById(1L);
		Chapter metaChapter = new Chapter();
		metaChapter.setBook(chpt.get().getBook());
		metaChapter.setId(chpt.get().getId());
		metaChapter.setNumber(chpt.get().getNumber());
		metaChapter.setVerses(chpt.get().getVerses());
		welcome.setChapter(metaChapter);
		welcome.setUser(admin);
		welcome.setPublished(true);
		welcome.setText("Dobrodošli! Molimo Vas da se pridržavate pravila o korištenju ove web stranice.....");
		welcome.setSubject("Pravila zajednice");
		
		commentRepo.save(welcome);
		
		admin.getComments().add(welcome);
		
		
	}
}
