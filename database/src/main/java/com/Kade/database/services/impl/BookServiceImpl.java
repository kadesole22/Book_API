package com.Kade.database.services.impl;

import com.Kade.database.domain.entities.BookEntity;
import com.Kade.database.repositories.BookRepository;
import com.Kade.database.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service implementation for managing Book entities.
 * Provides basic CRUD operations and business logic on Book data.
 */
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {   // Constructor to inject the BookRepository dependency
        this.bookRepository = bookRepository;
    }

    @Override                                                 // Creates or updates a book with the given ISBN
    public BookEntity createUpdateBook(String isbn, BookEntity book) {
        book.setIsbn(isbn);
        return bookRepository.save(book);
    }

    @Override                                                 // Returns all books from DB
    public List<BookEntity> findAll() {
        return StreamSupport
                .stream(bookRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override                                                 // Returns paginated list of books
    public Page<BookEntity> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override                                                 // Retrieves book by ISBN
    public Optional<BookEntity> findOne(String isbn) {
        return bookRepository.findById(isbn);
    }

    @Override                                                 // Checks if book exists by ISBN
    public boolean isExists(String isbn) {
        return bookRepository.existsById(isbn);
    }

    @Override                                                 // Partially updates fields of existing book
    public BookEntity partialUpdate(String isbn, BookEntity bookEntity) {
        bookEntity.setIsbn(isbn);

        return bookRepository.findById(isbn).map(existingBook -> {
            Optional.ofNullable(bookEntity.getTitle()).ifPresent(existingBook::setTitle);
            return bookRepository.save(existingBook);
        }).orElseThrow(() -> new RuntimeException("Book does not exist"));
    }

    @Override                                                 // Deletes book by ISBN
    public void delete(String isbn) {
        bookRepository.deleteById(isbn);
    }
}