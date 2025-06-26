package com.Kade.database.services;

import com.Kade.database.domain.entities.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing Book entities.
 * Defines operations for creating, updating, retrieving, and deleting books.
 */
public interface BookService {

    BookEntity createUpdateBook(String isbn, BookEntity book);

    List<BookEntity> findAll();

    Page<BookEntity> findAll(Pageable pageable);

    Optional<BookEntity> findOne(String isbn);

    boolean isExists(String isbn);

    BookEntity partialUpdate(String isbn, BookEntity bookEntity);

    void delete(String isbn);
}

