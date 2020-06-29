package com.epam.lab.mapper;

import com.epam.lab.dto.entity.AuthorDto;
import com.epam.lab.dto.entity.NewsDto;
import com.epam.lab.dto.entity.TagDto;
import com.epam.lab.model.Author;
import com.epam.lab.model.News;
import com.epam.lab.model.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class NewsMapper implements AbstractMapper<NewsDto, News> {

    private ModelMapper mapper;
    private AuthorMapper authorMapper;
    private TagMapper tagMapper;

    @Autowired
    public NewsMapper(ModelMapper mapper, AuthorMapper authorMapper, TagMapper tagMapper) {
        this.mapper = mapper;
        this.authorMapper = authorMapper;
        this.tagMapper = tagMapper;
    }

    public News toEntity(NewsDto dto) {

        News news = mapper.map(dto, News.class);
        Author author = Objects.isNull(dto.getTagDto()) ? null : authorMapper.toEntity(dto.getAuthorDto());
        news.setAuthor(author);
        Set<Tag> tagList = Objects.isNull(dto.getTagDto()) ? null : dto.getTagDto().stream().map(entity -> tagMapper.toEntity(entity)).collect(Collectors.toSet());
        news.setTag(tagList);
        return news;
    }

    public NewsDto toDto(News entity) {

        NewsDto newsDto = mapper.map(entity, NewsDto.class);
        AuthorDto authorDto = Objects.isNull(entity.getAuthor()) ? null :authorMapper.toDto(entity.getAuthor());
        newsDto.setAuthorDto(authorDto);
        Set<TagDto> tagList = Objects.isNull(entity.getTag()) ? null : entity.getTag().stream().map(tag -> tagMapper.toDto(tag)).collect(Collectors.toSet());
        newsDto.setTagDto(tagList);
        return newsDto;
    }
}
