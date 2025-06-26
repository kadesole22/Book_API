package com.Kade.database.controllers;

import com.Kade.database.domain.dto.BookDto;
import com.Kade.database.domain.entities.BookEntity;
import com.Kade.database.mappers.Mapper;
import com.Kade.database.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * REST controller for managing Book resources.
 * Provides CRUD and partial update functionality for books identified by ISBN.
 */
@RestController
public class BookController {


    private Mapper<BookEntity, BookDto> bookMapper;
    private BookService bookService;

    // Constructor for dependency injection
    public BookController(Mapper<BookEntity, BookDto> bookMapper, BookService bookService) {
        this.bookMapper = bookMapper;    // Mapper for converting between entities and DTOs
        this.bookService = bookService;  // Service layer for book operations
    }

    @PutMapping("/books/{isbn}")      // Creates OR updates a book by ISBN
    public ResponseEntity<BookDto> createUpdateBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto){
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);     // Convert DTO to entity
        boolean bookExists = bookService.isExists(isbn);         // Check if book already exists
        BookEntity savedBookEntity = bookService                 // Create or update book
                .createUpdateBook(isbn, bookEntity);
        BookDto savedBookDto = bookMapper.mapTo(savedBookEntity);// Convert entity back to DTO

        // Return appropriate status based on whether it was a create or update
        if(bookExists){
            return new  ResponseEntity<>(savedBookDto, HttpStatus.OK);
        } else {
            return new  ResponseEntity<>(savedBookDto, HttpStatus.CREATED);
        }
    }

    @PatchMapping(path = "/books/{isbn}")// Partially updates a book by ISBN
    public ResponseEntity<BookDto> partialUpdateBook(
            @PathVariable("isbn") String isbn,
            @RequestBody BookDto bookDto
    ){
        boolean bookExists = bookService.isExists(isbn);
        if(!bookService.isExists(isbn)){
            return new  ResponseEntity<>(HttpStatus.NOT_FOUND);     // Book not found
        }
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);       // Convert partial DTO to entity
        BookEntity updatedBookEntity = bookService                // Perform partial update
                .partialUpdate(isbn, bookEntity);
        return new ResponseEntity<>(bookMapper.mapTo(updatedBookEntity), HttpStatus.OK);
    }

    @GetMapping(path = "/books")                    // Retrieves a paginated list of books
        public Page<BookDto> listBooks(Pageable pageable){
        Page<BookEntity> books = bookService.findAll(pageable);     // Fetch paginated books
        return books.map(bookMapper::mapTo);                        // Map each entity to DTO
    }

    @GetMapping(path = "/books/{isbn}")             // Retrieves a single book by its ISBN
    public ResponseEntity<BookDto> getBook(@PathVariable("isbn") String isbn) {
        Optional<BookEntity> foundBook = bookService.findOne(isbn); // Look up book by ISBN

        // If found, map to DTO and return 200 OK; else 404 not found
        return foundBook.map(bookEntity -> {
            BookDto bookDto = bookMapper.mapTo(bookEntity);
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @DeleteMapping(path = "/books/{isbn}")          // Deletes book by its ISBN
    public ResponseEntity<BookDto> deleteBook(@PathVariable("isbn") String isbn) {
        bookService.delete(isbn);                     // Delete book
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Respond with no content
    }
}
