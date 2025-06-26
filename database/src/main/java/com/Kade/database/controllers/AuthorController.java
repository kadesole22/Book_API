package com.Kade.database.controllers;

import com.Kade.database.domain.dto.AuthorDto;
import com.Kade.database.domain.entities.AuthorEntity;
import com.Kade.database.mappers.Mapper;
import com.Kade.database.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * REST controller for managing Author resources.
 * Provides CRUD endpoints for creating, retrieving, updating, and deleting authors.
 */
@RestController
public class AuthorController {

    private AuthorService authorService;
    private Mapper<AuthorEntity, AuthorDto> authorMapper;

    // Constructor for dependency injection
    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> authorMapper) {
        this.authorService = authorService;     // Service layer for author operations
        this.authorMapper = authorMapper;       // Mapper for converting between entities and DTOs
    }

    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author){
        AuthorEntity authorEntity = authorMapper.mapFrom(author);           // Convert DTO to entity
        AuthorEntity savedAuthorEntity = authorService.save(authorEntity);  // Save entity to database
        return new ResponseEntity<>(authorMapper                            // Return saved entity mapped back to DTO
                .mapTo(savedAuthorEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/authors")        // Lists all authors
    public List<AuthorDto> listAuthors(){
        List<AuthorEntity> authors = authorService.findAll();
        return authors.stream()             // Convert list of entities to DTOs using a stream
                .map(authorMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/authors/{id}")     // Retrieves a single author by ID
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable("id") Long id){
        Optional<AuthorEntity> foundAuthor = authorService.findOne(id);

        return foundAuthor.map(authorEntity -> {    // If author is found map to DTO and return with 200 OK
            AuthorDto authorDto = authorMapper.mapTo(authorEntity);
            return new  ResponseEntity<>(authorDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));  // If not found return 404
    }

    @PutMapping(path = "/authors/{id}")        // Fully updates an existing author
    public ResponseEntity<AuthorDto> fullUpdateAuthor(@PathVariable("id") Long id, @RequestBody AuthorDto authorDto){
        if(!authorService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        authorDto.setId(id);                     // Set ID to ensure correct update

        AuthorEntity authorEntity = authorMapper
                .mapFrom(authorDto);            // Convert DTO to entity, save, then return updated DTO
        AuthorEntity savedAuthorEntity = authorService.save(authorEntity);
        return new ResponseEntity<>(
                authorMapper.mapTo(savedAuthorEntity),
                HttpStatus.OK);
    }

    @PatchMapping(path = "/authors/{id}")   //Partially updates existing author
    public ResponseEntity<AuthorDto> partialUpdate(
            @PathVariable("id") Long id,
            @RequestBody AuthorDto authorDto
    ){
        if(!authorService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AuthorEntity authorEntity = authorMapper.
                mapFrom(authorDto);             // Map partial data to entity and perform update
        AuthorEntity updatedAuthor = authorService.partialUpdate(id, authorEntity);
        return new ResponseEntity<>(
            authorMapper.mapTo(updatedAuthor),
                HttpStatus.OK);
    }

    @DeleteMapping(path = "/authors/{id}")   // Deletes author by ID
    public ResponseEntity<Void> deleteAuthor(@PathVariable("id") Long id) {
        authorService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
