package com.epam.lab.service.impl;

import com.epam.lab.dto.criteria.NewsCriteriaDto;
import com.epam.lab.dto.entity.NewsDto;
import com.epam.lab.mapper.NewsCriteriaMapper;
import com.epam.lab.mapper.NewsMapper;
import com.epam.lab.exception.ServiceException;
import com.epam.lab.model.Author;
import com.epam.lab.model.News;
import com.epam.lab.model.Tag;
import com.epam.lab.repository.EntityRepository;
import com.epam.lab.criteria.NewsCriteria;
import com.epam.lab.repository.NewsRepository;
import com.epam.lab.service.NewsService;
import com.epam.lab.specification.author.AuthorByFullNameSpecification;
import com.epam.lab.specification.author.AuthorByIdSpecification;
import com.epam.lab.specification.news.*;
import com.epam.lab.specification.tag.TagByIdSpecification;
import com.epam.lab.specification.tag.TagByNameSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class NewsServiceImpl implements NewsService {

    private NewsRepository newsRepository;
    private NewsMapper newsMapper;
    private NewsCriteriaMapper newsCriteriaMapper;
    private EntityRepository<Author> authorRepository;
    private EntityRepository<Tag> tagEntityRepository;

    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository, EntityRepository<Author> authorRepository,
                           NewsCriteriaMapper newsCriteriaMapper, NewsMapper newsMapper, EntityRepository<Tag> tagEntityRepository) {

        this.newsRepository = newsRepository;
        this.authorRepository = authorRepository;
        this.newsCriteriaMapper = newsCriteriaMapper;
        this.newsMapper = newsMapper;
        this.tagEntityRepository = tagEntityRepository;
    }

    @Override
    public NewsDto add(NewsDto newsDto) {
        if (newsDto.getId() == null) {
            newsDto.setDate(LocalDate.now());
            News news = createNewsEntity(newsDto);
            newsRepository.addEntity(news);
            return newsMapper.toDto(news);
        } else {
            throw new ServiceException("add shouldn't contain id");
        }
    }

    @Override
    public NewsDto update(NewsDto newsDto) {
        isNewsExist(newsDto.getId());
        News news = createNewsEntity(newsDto);
        newsRepository.updateEntity(news);
        return newsMapper.toDto(news);
    }

    private void isNewsExist(long id){
        NewsByIdSpecification newsByIdSpecification = new NewsByIdSpecification(id);
        List<News> listOfNews = newsRepository.query(newsByIdSpecification);
        if (listOfNews.isEmpty()) {
            throw new ServiceException("unexpected news id");
        }
    }

    private News createNewsEntity(NewsDto newsDto) {
        newsDto.setModificationDate(LocalDate.now());
        News news = newsMapper.toEntity(newsDto);
        if(newsDto.getTagDto() != null) {
            isTagExist(news.getTag());
        }
        isAuthorExist(news.getAuthor());
        return news;
    }

    private void isTagExist(Set<Tag> tagList) {
        tagList.forEach(tag -> {
            if (tag.getId() == 0) {
                checkForExistingTagName(tag.getName());
            } else {
                checkForExistingTagId(tag);
            }
        });
    }

    private void checkForExistingTagName(String name) {
        TagByNameSpecification tagByNameSpecification = new TagByNameSpecification(name);
        List<Tag> listOfTags = tagEntityRepository.query(tagByNameSpecification);
        if (!listOfTags.isEmpty()) {
            throw new ServiceException("you try ro create tag existing in database, choose from existing tags");
        }
    }

    private void checkForExistingTagId(Tag tag) {
        TagByIdSpecification tagByIdSpecification = new TagByIdSpecification(tag.getId());
        List<Tag> listOfTags = tagEntityRepository.query(tagByIdSpecification);
        if (listOfTags.isEmpty() || !listOfTags.get(0).equals(tag)) {
            throw new ServiceException("unexpected tag id");
        }
    }

    private void isAuthorExist(Author author) {
        if (author.getId() == 0) {
            checkForExistingAuthor(author);
        } else {
            checkForExistingAuthorId(author.getId());
        }
    }

    private void checkForExistingAuthor(Author author) {
        AuthorByFullNameSpecification authorByFullNameSpecification = new AuthorByFullNameSpecification(author);
        List<Author> authorList = authorRepository.query(authorByFullNameSpecification);
        if (!authorList.isEmpty()) {
            throw new ServiceException("author is already exist");
        }
    }

    private void checkForExistingAuthorId(long id) {
        AuthorByIdSpecification authorByIdSpecification = new AuthorByIdSpecification(id);
        List<Author> authorList = authorRepository.query(authorByIdSpecification);
        if (authorList.isEmpty()) {
            throw new ServiceException("unexpected author id");
        }
    }

    @Override
    public void delete(NewsDto newsDto) {
        News news = newsMapper.toEntity(newsDto);
        newsRepository.removeEntity(news);
    }

    @Override
    public List<NewsDto> findByName(NewsDto newsDto) {
        NewsByTitleSpecification newsByTitleSpecification = new NewsByTitleSpecification(newsDto.getTitle());
        List<News> listNews = newsRepository.query(newsByTitleSpecification);
        return convertListNewsToDto(listNews);
    }

    @Override
    public NewsDto findById(NewsDto newsDto) {
        NewsByIdSpecification newsByIdSpecification = new NewsByIdSpecification(newsDto.getId());
        List<News> listNews = newsRepository.query(newsByIdSpecification);
        if (listNews.size() == 1) {
            News news = listNews.get(0);
            return newsMapper.toDto(news);
        } else {
            throw new ServiceException("no news with id" + newsDto.getId());
        }
    }

    public long findCount() {
        return newsRepository.findCount();
    }

    public long findCount(NewsCriteriaDto newsCriteria) {
        NewsCriteria criteria = newsCriteriaMapper.toEntity(newsCriteria);
        return newsRepository.findCount(criteria);
    }

    public List<NewsDto> findAllNewsByCriteria(NewsCriteriaDto newsCriteria) {
        NewsCriteria criteria = newsCriteriaMapper.toEntity(newsCriteria);
        NewsByCriteriaSpecification newsByCriteriaSpecification = new NewsByCriteriaSpecification(criteria);
        List<News> newsList = newsRepository.query(newsByCriteriaSpecification);
        return convertListNewsToDto(newsList);
    }

    private List<NewsDto> convertListNewsToDto(List<News> newsList) {
        return newsList.stream().filter(Objects::nonNull)
                .map(newsMapper::toDto).collect(Collectors.toList());
    }
}
