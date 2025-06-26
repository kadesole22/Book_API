package com.Kade.database.services.impl;


import com.Kade.database.domain.entities.AuthorEntity;
import com.Kade.database.repositories.AuthorRepository;
import com.Kade.database.services.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service implementation for managing Author entities.
 * Provides basic CRUD operations and business logic on Author data.
 */
@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {   // Constructor to inject the AuthorRepository dependency
        this.authorRepository = authorRepository;
    }

    @Override                                                      // Saves a new or existing author entity to DB
    public AuthorEntity save(AuthorEntity authorEntity){
        return authorRepository.save(authorEntity);
    }

    @Override                                                      // Returns all authors from DB
    public List<AuthorEntity> findAll(){
        return StreamSupport.stream(authorRepository
                        .findAll()
                        .spliterator(),
                        false)
                .collect(Collectors.toList());
    }

    @Override                                                      // Retrieves author by ID
    public Optional<AuthorEntity> findOne(Long id){
        return authorRepository.findById(id);
    }

    @Override                                                     // Checks if author exists returns true or false
    public boolean isExists(Long id) {
        return authorRepository.existsById(id);
    }

    @Override                                                    // Partially updates fields of existing author
    public AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity){
        authorEntity.setId(id);

        return authorRepository.findById(id).map(existingAuthor ->{
            Optional.ofNullable(authorEntity.getName()).ifPresent(existingAuthor::setName);
            Optional.ofNullable(authorEntity.getAge()).ifPresent(existingAuthor::setAge);
            return authorRepository.save(existingAuthor);
        }).orElseThrow(() -> new RuntimeException("Author not found!"));
    }

    public void delete(Long id) {                               // Deletes author from DB
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
        }
        // else do nothing, to support 204 response even when ID doesn't exist
    }

}
