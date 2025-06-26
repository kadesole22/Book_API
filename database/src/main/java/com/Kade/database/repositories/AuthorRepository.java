package com.Kade.database.repositories;

import com.Kade.database.domain.entities.AuthorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing CRUD operations on AuthorEntity.
 * Extends CrudRepository to inherit basic CRUD functionality.
 * Includes custom query methods for filtering authors by age.
 */
@Repository
public interface AuthorRepository extends CrudRepository<AuthorEntity,Long> {


    Iterable<AuthorEntity> ageLessThan(int age);


    @Query("SELECT a from AuthorEntity a where a.age > ?1")
    Iterable<AuthorEntity> findAuthorsWithAgeGreaterThan(int age);
}
