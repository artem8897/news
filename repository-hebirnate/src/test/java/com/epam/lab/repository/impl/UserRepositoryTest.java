package com.epam.lab.repository.impl;

import com.epam.lab.config.TestRepositoryConfig;
import com.epam.lab.exception.RepositoryException;
import com.epam.lab.model.User;
import com.epam.lab.repository.EntityRepository;
import com.epam.lab.specification.user.UserByIdSpecification;
import com.epam.lab.specification.user.UserByNameSpecification;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestRepositoryConfig.class})

public class UserRepositoryTest {

    @Autowired
    EntityRepository<User> userRepository;

    @Test
    public void count(){
        long actual = userRepository.findCount();
        int expected = 6;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addEntity() {
        User expected = new User();
        expected.setName("Artsiom");
        expected.setSurname("Kuzmik");
        expected.setLogin("artaskuz");
        expected.setPassword("1997");
        expected.setRole("admin");
        User actual = userRepository.addEntity(expected);
        Assert.assertEquals(expected, actual);
    }
    @Test
    public void removeEntity() {

        User user = new User(3L, "artkuz","1997","Kuzmik","Artsiom");
        userRepository.removeEntity(user);
        Assert.assertTrue(true);
    }

    @Test(expected = RepositoryException.class)
    public void deleteWithException(){
        User user = new User();
        user.setId(1111111L);
        userRepository.removeEntity(user);
    }

    @Test
    public void updateEntity() throws RepositoryException {

        User expected = new User(1L, "artkuz","1997","Kuzmik","Artsiom");
        User actual = userRepository.updateEntity(expected);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RepositoryException.class)
    public void updateWithException(){
        User user = new User();
        user.setId(1111111L);
        userRepository.updateEntity(user);
    }


    @Test
    public void queryById() throws RepositoryException {

        List<User> userList = userRepository.query(new UserByIdSpecification(1L));
        User actual = userList.get(0);
        User expected = new User(1L, "artkuz","1997","Kuzmik","Artsiom");
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void queryByName() throws RepositoryException {

        List<User> userList = userRepository.query(new UserByNameSpecification("Artsiom"));
        User actual = userList.get(0);
        User expected = new User(1L, "artkuz","1997","Kuzmik","Artsiom");
        Assert.assertEquals(expected,actual);
    }
}
