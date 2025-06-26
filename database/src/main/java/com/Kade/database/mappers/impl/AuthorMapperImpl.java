package com.Kade.database.mappers.impl;

import com.Kade.database.domain.dto.AuthorDto;
import com.Kade.database.domain.entities.AuthorEntity;
import com.Kade.database.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Repository interface for AuthorEntity.
 * Extends CrudRepository to provide basic CRUD operations.
 * Contains custom query methods to find authors by age criteria.
 */
@Component
public class AuthorMapperImpl implements Mapper<AuthorEntity, AuthorDto> {

    private ModelMapper modelMapper;

    public AuthorMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AuthorDto mapTo(AuthorEntity authorEntity) {
        return modelMapper.map(authorEntity, AuthorDto.class);
    }

    @Override
    public AuthorEntity mapFrom(AuthorDto authorDto) {
        return modelMapper.map(authorDto, AuthorEntity.class);
    }
}
