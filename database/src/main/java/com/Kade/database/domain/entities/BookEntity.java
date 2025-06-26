package com.Kade.database.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a Book in the database.
 * Maps to a table that stores books with a unique ISBN, title,
 * and a reference to its author.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="books")
public class BookEntity {

    @Id
    private String isbn;

    private String title;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    private AuthorEntity author;

}
