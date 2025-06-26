package com.Kade.database.mappers.impl;

import com.Kade.database.domain.dto.BookDto;
import com.Kade.database.domain.entities.BookEntity;
import com.Kade.database.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
/**
 * Implementation of the Mapper interface for BookEntity and BookDto.
 * Uses ModelMapper to convert between the entity and DTO objects.
 */
@Component
public class BookMapperImpl implements Mapper<BookEntity, BookDto> {

    private ModelMapper modelMapper;

    public BookMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BookDto mapTo(BookEntity bookEntity) {
        return modelMapper.map(bookEntity, BookDto.class);
    }

    @Override
    public BookEntity mapFrom(BookDto bookDto) {
        return modelMapper.map(bookDto, BookEntity.class);
    }
}
