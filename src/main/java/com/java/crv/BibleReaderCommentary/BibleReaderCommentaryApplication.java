package com.java.crv.BibleReaderCommentary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.java.crv.BibleReaderCommentary.repositories")
public class BibleReaderCommentaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(BibleReaderCommentaryApplication.class, args);
	}

}
