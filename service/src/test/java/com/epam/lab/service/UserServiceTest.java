package com.epam.lab.service;

import com.epam.lab.dto.entity.UserDto;
import com.epam.lab.exception.ServiceException;
import com.epam.lab.mapper.UserMapper;
import com.epam.lab.model.User;
import com.epam.lab.repository.impl.UserRepository;
import com.epam.lab.service.impl.UserServiceImpl;
import com.epam.lab.specification.user.UserByIdSpecification;
import com.epam.lab.specification.user.UserByNameSpecification;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class UserServiceTest {

    private UserRepository repository = mock(UserRepository.class);
    private UserMapper userMapper = new UserMapper(new ModelMapper());
    private UserServiceImpl userService = new UserServiceImpl(repository, userMapper);
    private User user = new User(1L, "login", "password", "surname", "name");
    private UserDto userDto = new UserDto();

    @Before
    public void initializing() {
        user.setRole("admin");
        userDto.setId(1L);
        userDto.setName("name");
        userDto.setSurname("surname");
        userDto.setLogin("login");
        userDto.setPassword("password");
        userDto.setRole("admin");

    }

    @Test
    public void add() {
        when(repository.addEntity(user)).thenReturn(user);
        UserDto actual = userService.add(userDto);
        assertEquals(userDto, actual);
    }


    @Test
    public void remove(){
        userService.delete(userDto);
        verify(repository, times(1)).removeEntity(user);
    }

    @Test
    public void update() {
        List<User> userList = new ArrayList<>();
        userList.add(user);
        when(repository.updateEntity(user)).thenReturn(user);
        UserDto actual = userService.update(userDto);
        assertEquals(userDto, actual);
    }

    @Test
    public void getAllUsersWithName() {
        List<User> users = new ArrayList<>();
        users.add(user);
        when(repository.query(any(UserByNameSpecification.class))).thenReturn(users);
        List<UserDto> actual = userService.findByName(userDto);
        List<UserDto> expected = new ArrayList<>();
        expected.add(userDto);
        assertEquals(expected, actual);
    }

    @Test(expected = ServiceException.class)
    public void getByIdWithException() {
        List<User> users = new ArrayList<>();
        when(repository.query(any(UserByIdSpecification.class))).thenReturn(users);
        UserDto actual = userService.findById(userDto);
        assertEquals(userDto, actual);
    }

    @Test
    public void findCount(){
        when(repository.findCount()).thenReturn(1L);
        long expected = 1L;
        long actual = userService.findCount();
        Assert.assertEquals(expected, actual);

    }

}
