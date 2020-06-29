package com.epam.lab.mapper;

import com.epam.lab.criteria.NewsCriteria;
import com.epam.lab.dto.criteria.NewsCriteriaDto;
import com.epam.lab.dto.entity.AuthorDto;
import com.epam.lab.model.Author;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class NewsCriteriaMapper {

    private ModelMapper mapper;
    private AuthorMapper authorMapper;

    @Autowired
    public NewsCriteriaMapper(ModelMapper mapper, AuthorMapper authorMapper) {
        this.mapper = mapper;
        this.authorMapper = authorMapper;
    }

    public NewsCriteria toEntity(NewsCriteriaDto dto) {
        NewsCriteria newsCriteria = mapper.map(dto, NewsCriteria.class);
        Author author = Objects.isNull(dto.getAuthorDto()) ? null : authorMapper.toEntity(dto.getAuthorDto());
        newsCriteria.setAuthor(author);
        return newsCriteria;
    }

    public NewsCriteriaDto toDto(NewsCriteria entity) {
        NewsCriteriaDto newsCriteriaDto = mapper.map(entity, NewsCriteriaDto.class);
        AuthorDto authorDto = Objects.isNull(entity.getAuthor()) ? null :authorMapper.toDto(entity.getAuthor());
        newsCriteriaDto.setAuthorDto(authorDto);
        return newsCriteriaDto;
    }

}
