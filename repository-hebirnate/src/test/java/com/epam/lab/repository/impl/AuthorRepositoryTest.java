package com.epam.lab.repository.impl;

import com.epam.lab.config.TestRepositoryConfig;
import com.epam.lab.exception.RepositoryException;
import com.epam.lab.model.Author;
import com.epam.lab.repository.EntityRepository;
import com.epam.lab.specification.author.AuthorByFullNameSpecification;
import com.epam.lab.specification.author.AuthorByIdSpecification;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestRepositoryConfig.class})

public class AuthorRepositoryTest {

    @Autowired
    EntityRepository<Author> authorRepository;

    @Test
    public void count(){
        long actual = authorRepository.findCount();
        long expected = 6;
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void addEntity() {

        Author expected = new Author(7L,"author", "author");
        Author actual = new Author();
        actual.setName("author");
        actual.setSurname("author");
        actual = authorRepository.addEntity(actual);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void removeEntity() {
        Author author = new Author(6L,"Max", "author");
        authorRepository.removeEntity(author);
        Assert.assertTrue(true);
    }

    @Test(expected = RepositoryException.class)
    public void removeWithException() {
        authorRepository.removeEntity(new Author(1111L,"asdh","laskjd"));
    }

    @Test
    public void updateEntity() {
        Author expected = new Author(2L,"author", "author");
        Author actual = authorRepository.updateEntity(new Author(2L,"author", "author"));
        Assert.assertEquals(expected, actual);

    }

    @Test(expected = RepositoryException.class)
    public void updateWithException() {
        authorRepository.updateEntity(new Author(1111L,"asdh","laskjd"));
    }

    @Test
    public void queryForId() {
        List<Author> authorList = authorRepository.query(new AuthorByIdSpecification(1L));
        Author actual = authorList.get(0);
        Author expected = new Author(1,"Artsiom", "Kuzmik");
        Assert.assertEquals(expected,actual);
    }
    @Test
    public void queryForFullName() {
        Author expected = new Author(5,"Ksenya", "Perpechina");
        List<Author> authorList = authorRepository.query(new AuthorByFullNameSpecification(expected));
        System.out.println(authorList);
        Author actual = authorList.get(0);
        Assert.assertEquals(actual, expected);
    }



}
