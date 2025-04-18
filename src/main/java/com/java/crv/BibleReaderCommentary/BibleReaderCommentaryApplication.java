package com.java.crv.BibleReaderCommentary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// + todo connect User with Commentary in their relation and in persistence
//todo refactor names of views
//todo refactor URL endpoints
//todo allow only anonymous and guests to create their users
//todo existing users should access their homepage rather than new user creation form
//todo implement sharing of comments between users both public and private.
//todo proof user creation, disallow username duplicates.
//todo non-users cannot view comment creation but only user creation forms
//todo make custom made user login form.
//todo use preloaded library of cuss words and sift comments via regex, disallow such comments.
//todo implement try->catch blocks where needed.
//todo use SLF4J logging instead of System.out.println

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.java.crv.BibleReaderCommentary.repositories")
public class BibleReaderCommentaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(BibleReaderCommentaryApplication.class, args);
	}

}
