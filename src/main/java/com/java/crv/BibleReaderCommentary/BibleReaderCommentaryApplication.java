package com.java.crv.BibleReaderCommentary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.java.crv.BibleReaderCommentary", "com,java.crv.BibleReaderCommentary.repositories", "com,java.crv.BibleReaderCommentary.domain", "com,java.crv.BibleReaderCommentary.controllers"})

public class BibleReaderCommentaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(BibleReaderCommentaryApplication.class, args);
	}

}
