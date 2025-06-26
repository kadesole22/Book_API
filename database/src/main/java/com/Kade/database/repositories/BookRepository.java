package com.Kade.database.repositories;


import com.Kade.database.domain.entities.BookEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing BookEntity persistence.
 * Extends CrudRepository for basic CRUD operations and
 * PagingAndSortingRepository to support pagination and sorting capabilities.
 */
@Repository
public interface BookRepository extends CrudRepository<BookEntity, String>,
        PagingAndSortingRepository<BookEntity, String> {
}
