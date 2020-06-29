package com.epam.lab.service;

import com.epam.lab.dto.entity.AuthorDto;
import com.epam.lab.mapper.AuthorMapper;
import com.epam.lab.exception.ServiceException;
import com.epam.lab.model.Author;
import com.epam.lab.repository.impl.AuthorRepository;
import com.epam.lab.service.impl.AuthorServiceImpl;
import com.epam.lab.specification.author.AuthorByFullNameSpecification;
import com.epam.lab.specification.author.AuthorByIdSpecification;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class AuthorServiceTest {

    private AuthorRepository repository = mock(AuthorRepository.class);
    private AuthorMapper authorMapper = new AuthorMapper(new ModelMapper());
    private AuthorServiceImpl authorService = new AuthorServiceImpl(repository, authorMapper);
    private Author author = new Author(1L, "Artsiom", "Kuzmik");
    private AuthorDto authorDto = new AuthorDto();

    @Before
    public void init() {
        authorDto.setId(1L);
        authorDto.setName("Artsiom");
        authorDto.setSurname("Kuzmik");
    }

    @Test
    public void add() {
        authorDto.setId(null);
        Author authorForAdd = new Author();
        authorForAdd.setName("Artsiom");
        authorForAdd.setSurname("Kuzmik");
        when(repository.addEntity(authorForAdd)).thenReturn(author);
        AuthorDto actual = authorService.add(authorDto);
        authorDto.setId(1L);
        assertEquals(authorDto, actual);
    }

    @Test(expected = ServiceException.class)
    public void addWithException() {
        List<Author> authorList = new ArrayList<>();
        authorList.add(author);
        when(repository.addEntity(author)).thenReturn(author);
        when(repository.query(any(AuthorByFullNameSpecification.class))).thenReturn(authorList);

        AuthorDto actual = authorService.add(authorDto);
        AuthorDto expected = authorDto;
        assertEquals(expected, actual);
    }

    @Test
    public void update() {
        List<Author> authorList = new ArrayList<>();
        authorList.add(author);
        when(repository.updateEntity(author)).thenReturn(author);
        when(repository.query(any(AuthorByIdSpecification.class))).thenReturn(authorList);
        when(repository.query(any(AuthorByFullNameSpecification.class))).thenReturn(null);
        AuthorDto actual = authorService.update(authorDto);
        AuthorDto expected = authorDto;
        assertEquals(expected, actual);
    }

    @Test(expected = ServiceException.class)
    public void updateWithException() {

        when(repository.updateEntity(author)).thenReturn(author);
        when(repository.query(any(AuthorByIdSpecification.class))).thenReturn(new ArrayList<>());
        AuthorDto actual = authorService.update(authorDto);
        AuthorDto expected = authorDto;
        assertEquals(expected, actual);
    }

    @Test
    public void remove() {
        authorService.delete(authorDto);
        verify(repository, times(1)).removeEntity(author);
    }

    @Test
    public void findById() {

        List<Author> authors = new ArrayList() {{
            add(author);
        }};
        when(repository.query(any(AuthorByIdSpecification.class))).thenReturn(authors);
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(1L);
        AuthorDto actual = authorService.findById(authorDto);
        authorDto.setName("Artsiom");
        authorDto.setSurname("Kuzmik");
        assertEquals(authorDto, actual);
    }

    @Test
    public void findByName() {

        List<Author> authors = new ArrayList() {{
            add(author);
        }};
        when(repository.query(any(AuthorByFullNameSpecification.class))).thenReturn(authors);
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(1L);
        List<AuthorDto> actual = authorService.findByName(authorDto);
        authorDto.setName("Artsiom");
        authorDto.setSurname("Kuzmik");
        List<AuthorDto> expected = new ArrayList<>();
        expected.add(authorDto);
        assertEquals(expected, actual);
    }

    @Test(expected = ServiceException.class)
    public void findByIdWithException() {
        List<Author> authors = new ArrayList();
        when(repository.query(any(AuthorByIdSpecification.class))).thenReturn(authors);
        authorService.findById(authorDto);
        verify(repository, times(1)).removeEntity(author);

    }

    @Test
    public void count() {
        when(repository.findCount()).thenReturn(1L);
        long expected = 1L;
        long actual = authorService.findCount();
        Assert.assertEquals(expected, actual);
    }

}
