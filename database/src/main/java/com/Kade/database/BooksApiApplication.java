package com.Kade.database;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Entry point for the Books API Spring Boot application.
 * This class bootstraps the application using {@link SpringApplication}.
 */
@SpringBootApplication
@Log
public class BooksApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooksApiApplication.class, args);
	}

}