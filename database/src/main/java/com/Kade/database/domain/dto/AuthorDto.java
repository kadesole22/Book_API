package com.Kade.database.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for Author.
 * This class is used to transfer author data between the client and server,
 * or between different layers of the application without exposing internal entities.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorDto {

    private Long id;

    private String name;

    private Integer age;

}
