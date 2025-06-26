package com.Kade.database.domain.dto;


import com.Kade.database.domain.entities.AuthorEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Data Transfer Object (DTO) for Book.
 * Used to transfer book data between the client and server or between application layers,
 * without exposing internal entity logic.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {

    private String isbn;

    private String title;

    private AuthorDto author;
}
