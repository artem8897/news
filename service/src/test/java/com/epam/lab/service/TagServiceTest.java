package com.epam.lab.service;

import com.epam.lab.dto.entity.TagDto;
import com.epam.lab.exception.ServiceException;
import com.epam.lab.mapper.TagMapper;
import com.epam.lab.model.Tag;
import com.epam.lab.repository.impl.TagRepository;
import com.epam.lab.service.impl.TagServiceImpl;
import com.epam.lab.specification.tag.TagByIdSpecification;
import com.epam.lab.specification.tag.TagByNameSpecification;
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

@RunWith(JUnit4.class)
public class TagServiceTest {

    private TagRepository repository = mock(TagRepository.class);
    private TagMapper tagMapper = new TagMapper(new ModelMapper());
    private TagServiceImpl tagService = new TagServiceImpl(repository, tagMapper);
    private Tag tag = new Tag(1L, "science");
    private TagDto tagDto = new TagDto();

    @Before
    public void initializing() {
        tagDto.setId(1L);
        tagDto.setName("science");
    }

    @Test
    public void add() {
        tagDto.setId(null);
        Tag tagAdd = new Tag();
        tagAdd.setName("science");
        when(repository.addEntity(tagAdd)).thenReturn(tag);
        when(repository.query(any(TagByNameSpecification.class))).thenReturn(new ArrayList<>());
        TagDto actual = tagService.add(tagDto);
        TagDto expected = tagDto;
        tagDto.setId(1L);
        assertEquals(expected, actual);
    }

    @Test(expected = ServiceException.class)
    public void addWithException() {
        when(repository.addEntity(tag)).thenReturn(tag);
        List<Tag> tagList = new ArrayList<>();
        tagList.add(tag);
        when(repository.query(any(TagByNameSpecification.class))).thenReturn(tagList);
        TagDto actual = tagService.add(tagDto);
        TagDto expected = tagDto;
        assertEquals(expected, actual);
    }

    @Test
    public void remove(){
        tagService.delete(tagDto);
        verify(repository, times(1)).removeEntity(tag);
    }

    @Test
    public void update() {
        List<Tag> tagList = new ArrayList<>();
        tagList.add(tag);
        when(repository.updateEntity(tag)).thenReturn(tag);
        when(repository.query(any(TagByIdSpecification.class))).thenReturn(tagList);
        TagDto actual = tagService.update(tagDto);
        TagDto expected = tagDto;
        assertEquals(expected, actual);
    }

    @Test(expected = ServiceException.class)
    public void updateWithException() {
        List<Tag> tagList = new ArrayList<>();
        tagList.add(tag);
        when(repository.updateEntity(tag)).thenReturn(tag);
        when(repository.query(any(TagByIdSpecification.class))).thenReturn(new ArrayList<>());
        TagDto actual = tagService.update(tagDto);
        TagDto expected = tagDto;
        assertEquals(expected, actual);
    }

    @Test
    public void getAllTagsWithName() {
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);
        when(repository.query(any(TagByNameSpecification.class))).thenReturn(tags);
        TagDto tagDto = new TagDto();
        tagDto.setName("science");
        List<TagDto> actual = tagService.findByName(tagDto);
        List<TagDto> expected = new ArrayList<>();
        tagDto.setId(1L);
        expected.add(tagDto);
        assertEquals(expected, actual);
    }

    @Test
    public void getById() {
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);
        when(repository.query(any(TagByIdSpecification.class))).thenReturn(tags);
        TagDto tagDto = new TagDto();
        tagDto.setId(1L);
        TagDto actual = tagService.findById(tagDto);
        tagDto.setName("science");
        assertEquals(tagDto, actual);
    }

    @Test(expected = ServiceException.class)
    public void getByIdWithException() {
        when(repository.query(any(TagByIdSpecification.class))).thenReturn(new ArrayList<>());
        tagService.findById(tagDto);
        verify(repository, times(1)).removeEntity(tag);
    }

    @Test
    public void getCount(){
        when(repository.findCount()).thenReturn(1L);
        long expected = 1L;
        long actual = tagService.findCount();
        Assert.assertEquals(expected,actual);
    }
}
