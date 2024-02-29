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
		BibleImporter reader = new BibleImporter("/home/sven/WordProject_Bible.xlsx");
		Bible bible = reader.loadBible(0);
		bibleRepo.save(bible);
		
		{
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
		{
			Commentary welcome = new Commentary();
			welcome.setId(2L);
			Optional<Chapter> chpt = chapterRepo.findById(1L);
			Chapter metaChapter = new Chapter();
			metaChapter.setBook(chpt.get().getBook());
			metaChapter.setId(chpt.get().getId());
			metaChapter.setNumber(chpt.get().getNumber());
			metaChapter.setVerses(chpt.get().getVerses());
			welcome.setChapter(metaChapter);
			welcome.setUser(admin);
			welcome.setPublished(true);
			welcome.setText("Radio buttons are normally presented in radio groups (a collection of radio buttons describing a set of related options)."
					+ " Only one radio button in a group can be selected at the same time.\n"
					+ "Note: The radio group must share the same name (the value of the name attribute) to be treated as a group. Once the radio group is created"
					+ ", selecting any radio button in that group automatically deselects any other selected radio button in the same group. You can have as many"
					+ " radio groups on a page as you want, as long as each group has its own name.");
			welcome.setSubject("Radio buttons");

			commentRepo.save(welcome);

			admin.getComments().add(welcome);
		}
		{
			Commentary welcome = new Commentary();
			welcome.setId(3L);
			Optional<Chapter> chpt = chapterRepo.findById(1L);
			Chapter metaChapter = new Chapter();
			metaChapter.setBook(chpt.get().getBook());
			metaChapter.setId(chpt.get().getId());
			metaChapter.setNumber(chpt.get().getNumber());
			metaChapter.setVerses(chpt.get().getVerses());
			welcome.setChapter(metaChapter);
			welcome.setUser(admin);
			welcome.setPublished(true);
			welcome.setText("When a browser requests a service from a web server, an error might occur, and the server might return an error code like \"404 Not Found\".\n"
					+ "It is common to name these errors HTML error messages.\n"
					+ "But these messages are something called HTTP status messages. In fact, the server always returns a message for every request. The most common message is 200 OK.\n"
					+ "Below is a list of HTTP status messages that might be returned:");
			welcome.setSubject("HTML Error Messages");

			commentRepo.save(welcome);

			admin.getComments().add(welcome);
			
		}
		
	}
}
