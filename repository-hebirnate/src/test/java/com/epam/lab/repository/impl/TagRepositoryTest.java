package com.epam.lab.repository.impl;

import com.epam.lab.config.TestRepositoryConfig;
import com.epam.lab.exception.RepositoryException;
import com.epam.lab.model.Tag;
import com.epam.lab.repository.EntityRepository;
import com.epam.lab.specification.tag.TagByIdSpecification;
import com.epam.lab.specification.tag.TagByNameSpecification;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestRepositoryConfig.class})

public class TagRepositoryTest {

    @Autowired
    EntityRepository<Tag> tagRepository;

    @Test
    public void count(){
        long actual = tagRepository.findCount();
        int expected = 10;
        Assert.assertEquals(expected, actual);
    }
    @Test
    public void addEntity() throws RepositoryException {
        Tag expected = new Tag(10L, "sciencea");
        Tag tag = new Tag();
        tag.setName("sciencea");
        Tag actual = tagRepository.addEntity(tag);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RepositoryException.class)
    public void removeWithException(){
        Tag tag = new Tag(1111111L, "science");
        tagRepository.removeEntity(tag);
    }

    @Test
    public void updateEntity() throws RepositoryException {

        Tag expected = new Tag(1L, "science");
        Tag actual = tagRepository.updateEntity(new Tag(1L, "science"));
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RepositoryException.class)
    public void updateWithException(){
        Tag tag = new Tag(1111111L, "science");
        tagRepository.removeEntity(tag);
    }

    @Test
    public void queryForName() throws RepositoryException {

        List<Tag> tagList = tagRepository.query(new TagByNameSpecification("science"));
        Tag actual = tagList.get(0);
        Tag expected = new Tag(1L,"science");
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void queryForId() throws RepositoryException {

        List<Tag> tagList = tagRepository.query(new TagByIdSpecification(1L));
        Tag actual = tagList.get(0);
        Tag expected = new Tag(1L,"science");
        Assert.assertEquals(expected,actual);
    }

}
