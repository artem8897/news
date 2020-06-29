package com.epam.lab.repository.impl;

import com.epam.lab.config.TestRepositoryConfig;
import com.epam.lab.criteria.NewsCriteria;
import com.epam.lab.exception.RepositoryException;
import com.epam.lab.model.Author;
import com.epam.lab.model.News;
import com.epam.lab.model.Tag;
import com.epam.lab.repository.EntityRepository;
import com.epam.lab.repository.NewsRepository;
import com.epam.lab.specification.news.NewsByCriteriaSpecification;
import com.epam.lab.specification.news.NewsByIdSpecification;
import com.epam.lab.specification.news.NewsByTitleSpecification;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestRepositoryConfig.class})

public class NewsRepositoryTest {

    @Autowired
    NewsRepository newsRepository;

    Author author = new Author(1L, "Artsiom", "Kuzmik");
    Set<Tag> expectedTagList = new HashSet<>();
    Tag tag = new Tag(9L, "queue");
    LocalDate createDate = LocalDate.of(2020, 2,24);
    LocalDate modificateDate = LocalDate.of(2020, 2,24);
    News expected = new News(author, expectedTagList, "Hello", "some", "smoething", createDate, modificateDate);

    @Before
    public void initializing() {
        expected.setId(1L);
        expectedTagList.add(tag);
    }

    @Test
    public void count(){
        long actual = newsRepository.findCount(new NewsCriteria());
        long expected = 10;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findNewsByTag() {
        Set<Tag> expectedTagList = new HashSet<>();
        Tag tag = new Tag(1L, "science");
        expectedTagList.add(tag);
        expected.setTag(expectedTagList);
        List<String> expectedTagNameList = new ArrayList<>();
        expectedTagNameList.add("science");
        NewsCriteria newsCriteria = new NewsCriteria();
        newsCriteria.setTagNameList(expectedTagNameList);
        List<News> newsList = newsRepository.query(new NewsByCriteriaSpecification(newsCriteria));
        News actual = newsList.get(0);

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void addEntity() {
        Set<Tag> tagList = new HashSet<>();
        LocalDate createDate = LocalDate.now();
        LocalDate modificateDate = LocalDate.now();
        tagList.add(tag);
        News expected = new News(author, tagList, "title", "short", "looong", createDate, modificateDate);
        expected.setId(12L);
        News actual = newsRepository.addEntity(expected);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void removeEntity() {
        News news = new News();
        news.setId(3L);
        newsRepository.removeEntity(news);

        Assert.assertTrue(true);
    }

    @Test(expected = RepositoryException.class)
    public void removeEntityWithException() {
        News news = new News();
        news.setId(11111L);
        newsRepository.removeEntity(news);

        Assert.assertTrue(true);
    }

    @Test
    public void updateEntity() {
        Set<Tag> tagList = new HashSet<>();
        LocalDate createDate = LocalDate.now();
        LocalDate modificateDate = LocalDate.now();
        tagList.add(tag);
        News expected = new News(author, tagList, "title", "short", "looong", createDate, modificateDate);
        expected.setId(2L);
        News actual = newsRepository.updateEntity(expected);

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RepositoryException.class)
    public void updateEntityWithException() {
        Set<Tag> tagList = new HashSet<>();
        LocalDate createDate = LocalDate.now();
        LocalDate modificateDate = LocalDate.now();
        tagList.add(tag);
        News expected = new News(author, tagList, "title", "short", "looong", createDate, modificateDate);
        expected.setId(11111L);
        News actual = newsRepository.updateEntity(expected);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findNewsById() {
        Set<Tag> expectedTagList = new HashSet<>();
        Tag tag = new Tag(1L, "science");
        expectedTagList.add(tag);
        expected.setTag(expectedTagList);
        List<News> newsList = newsRepository.query(new NewsByIdSpecification(1L));
        News actual = newsList.get(0);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findNewsByName() throws RepositoryException {
        Set<Tag> expectedTagList = new HashSet<>();
        Tag tag = new Tag(1L, "science");
        expectedTagList.add(tag);
        expected.setTag(expectedTagList);
        List<News> newsList = newsRepository.query(new NewsByTitleSpecification("Hello"));
        News actual = newsList.get(0);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findNewsByAuthorAndTag() throws RepositoryException {
        List<String> expectedTagNameList = new ArrayList<>();
        expectedTagNameList.add("science");
        NewsCriteria newsCriteria = new NewsCriteria();
        newsCriteria.setAuthor(author);
        newsCriteria.setTagNameList(expectedTagNameList);
        List<News> newsList = newsRepository.query(new NewsByCriteriaSpecification(newsCriteria));
        News actual = newsList.get(0);
        Set<Tag> expectedTagList = new HashSet<>();
        Tag tag = new Tag(1L, "science");
        expectedTagList.add(tag);
        expected.setTag(expectedTagList);
        Assert.assertEquals(actual, expected);
    }



    @Test
    public void findNewsByAuthor() throws RepositoryException {
        Set<Tag> expectedTagList = new HashSet<>();
        Tag tag = new Tag(1L, "science");
        expectedTagList.add(tag);
        expected.setTag(expectedTagList);
        NewsCriteria newsCriteria = new NewsCriteria();
        newsCriteria.setAuthor(author);
        List<News> newsList = newsRepository.query(new NewsByCriteriaSpecification(newsCriteria));
        News actual = newsList.get(0);

        Assert.assertEquals(actual, expected);
    }


}
