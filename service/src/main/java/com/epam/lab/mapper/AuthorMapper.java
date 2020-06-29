package com.epam.lab.mapper;

import com.epam.lab.dto.entity.AuthorDto;
import com.epam.lab.model.Author;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AuthorMapper implements AbstractMapper<AuthorDto, Author> {

    private ModelMapper mapper;

    @Autowired
    public AuthorMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Author toEntity(AuthorDto dto) {
        return Objects.isNull(dto) ? null : mapper.map(dto, Author.class);
    }


    public AuthorDto toDto(Author entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, AuthorDto.class); }
}
