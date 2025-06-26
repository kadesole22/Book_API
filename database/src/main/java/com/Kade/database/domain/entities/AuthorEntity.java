package com.Kade.database.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing an Author in the database.
 * This class is mapped to a table that stores author information,
 * including ID, name, and age.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "authors")
public class AuthorEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_id_seq")
    //@SequenceGenerator(name = "author_seq", sequenceName = "author_id_seq", allocationSize = 1)
    private Long id;


    private String name;

    private Integer age;

}
