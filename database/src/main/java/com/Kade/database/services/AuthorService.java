package com.Kade.database.services;

import com.Kade.database.domain.entities.AuthorEntity;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing Author entities.
 * Defines the contract for CRUD operations and partial updates on authors.
 */
public interface AuthorService {

    AuthorEntity save(AuthorEntity author);

    List<AuthorEntity> findAll();

    Optional<AuthorEntity> findOne(Long id);

    boolean isExists(Long id);

    AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity);

    void delete(Long id);
}
