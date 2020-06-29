//package com.epam.lab.service;
//
//import com.epam.lab.dto.criteria.NewsCriteriaDto;
//import com.epam.lab.dto.entity.AuthorDto;
//import com.epam.lab.dto.entity.NewsDto;
//import com.epam.lab.dto.entity.TagDto;
//import com.epam.lab.mapper.AuthorMapper;
//import com.epam.lab.mapper.NewsCriteriaMapper;
//import com.epam.lab.mapper.NewsMapper;
//import com.epam.lab.mapper.TagMapper;
//import com.epam.lab.exception.ServiceException;
//import com.epam.lab.model.Author;
//import com.epam.lab.model.News;
//import com.epam.lab.model.Tag;
//import com.epam.lab.repository.impl.AuthorRepository;
//import com.epam.lab.repository.impl.NewsRepository;
//import com.epam.lab.repository.impl.TagRepository;
//import com.epam.lab.service.impl.NewsServiceImpl;
//import com.epam.lab.specification.author.AuthorByFullNameSpecification;
//import com.epam.lab.specification.author.AuthorByIdSpecification;
//import com.epam.lab.specification.news.NewsByCriteriaSpecification;
//import com.epam.lab.specification.news.NewsByIdSpecification;
//import com.epam.lab.specification.news.NewsByTitleSpecification;
//import com.epam.lab.specification.tag.TagByIdSpecification;
//import com.epam.lab.specification.tag.TagByNameSpecification;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.JUnit4;
//import org.modelmapper.ModelMapper;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@RunWith(JUnit4.class)
//public class NewsServiceTest {
//
//    private NewsRepository newsRepository = mock(NewsRepository.class);
//    private AuthorRepository authorRepository = mock(AuthorRepository.class);
//    private TagRepository tagRepository = mock(TagRepository.class);
//    private AuthorMapper authorMapper = new AuthorMapper(new ModelMapper());
//    private TagMapper tagMapper = new TagMapper(new ModelMapper());
//    private NewsMapper newsMapper = new NewsMapper(new ModelMapper(), authorMapper, tagMapper);
//    private NewsCriteriaMapper newsCriteriaMapper = new NewsCriteriaMapper(new ModelMapper(), authorMapper);
//    private NewsServiceImpl service = new NewsServiceImpl(newsRepository,
//            authorRepository, newsCriteriaMapper, newsMapper, tagRepository);
//    private Author author = new Author(1L, "Artsiom", "Kuzmik");
//    private AuthorDto authorDto = new AuthorDto();
//    private Set<Tag> tagList = new HashSet<>();
//    private List<Tag> tags = new ArrayList<>();
//    private Set<TagDto> tagListDto = new HashSet<>();
//    private List<Author> authorList = new ArrayList<>();
//    private List<News> newsList = new ArrayList<>();
//    private Tag tag = new Tag(1L, "science");
//    private TagDto tagDto = new TagDto();
//    private NewsDto newsDto = new NewsDto();
//    private News news = new News(author, tagList, "title", "short", "looong", LocalDate.now(), LocalDate.now());
//
//    @Before
//    public void initializing() {
//        news.setId(1L);
//        tagDto.setId(1L);
//        tagDto.setName("science");
//        tagList.add(tag);
//        tagListDto.add(tagDto);
//        authorDto.setId(1L);
//        authorDto.setName("Artsiom");
//        authorDto.setSurname("Kuzmik");
//        authorList.add(author);
//        newsDto.setId(1L);
//        newsDto.setAuthorDto(authorDto);
//        newsDto.setTagDto(tagListDto);
//        newsDto.setTitle("title");
//        newsDto.setShortText("short");
//        newsDto.setLongText("looong");
//        newsDto.setDate(LocalDate.now());
//        newsDto.setModificationDate(LocalDate.now());
//    }
//
//    @Test
//    public void add() {
//        News newsForAdd = new News(author, tagList, "title", "short", "looong", LocalDate.now(), LocalDate.now());
//        newsForAdd.setTag(tagList);
//        newsForAdd.setAuthor(author);
//        tags.add(tag);
//        when(newsRepository.addEntity(newsForAdd)).thenReturn(news);
//        when(tagRepository.query(any(TagByIdSpecification.class))).thenReturn(tags);
//        when(tagRepository.query(any(TagByNameSpecification.class))).thenReturn(null);
//        when(authorRepository.query(any(AuthorByIdSpecification.class))).thenReturn(authorList);
//        when(authorRepository.query(any(AuthorByFullNameSpecification.class))).thenReturn(null);
//        newsDto.setId(null);
//        NewsDto actual = service.add(newsDto);
//        newsDto.setId(0L);
//        assertEquals(newsDto, actual);
//    }
//
//    @Test
//    public void delete() {
//        service.delete(newsDto);
//        verify(newsRepository, times(1)).removeEntity(news);
//    }
//
//    @Test
//    public void update() {
//        tags.add(tag);
//        newsList.add(news);
//        when(newsRepository.updateEntity(news)).thenReturn(news);
//        when(tagRepository.query(any(TagByIdSpecification.class))).thenReturn(tags);
//        when(tagRepository.query(any(TagByNameSpecification.class))).thenReturn(null);
//        when(authorRepository.query(any(AuthorByIdSpecification.class))).thenReturn(authorList);
//        when(authorRepository.query(any(AuthorByFullNameSpecification.class))).thenReturn(null);
//        when(newsRepository.query(any(NewsByIdSpecification.class))).thenReturn(newsList);
//        NewsDto actual = service.update(newsDto);
//        NewsDto expected = newsDto;
//        assertEquals(expected, actual);
//    }
//
//    @Test(expected = ServiceException.class)
//    public void addWithExceptionInTag() {
//
//        News newsForAdd = new News(author, tagList, "title", "short", "looong", LocalDate.now(), LocalDate.now());
//        newsForAdd.setTag(tagList);
//        newsForAdd.setAuthor(author);
//        when(newsRepository.addEntity(newsForAdd)).thenReturn(news);
//        when(tagRepository.query(any(TagByIdSpecification.class))).thenReturn(tags);
//        when(tagRepository.query(any(TagByNameSpecification.class))).thenReturn(null);
//        when(authorRepository.query(any(AuthorByIdSpecification.class))).thenReturn(authorList);
//        when(authorRepository.query(any(AuthorByFullNameSpecification.class))).thenReturn(null);
//
//        NewsDto actual = service.add(newsDto);
//        NewsDto expected = newsDto;
//        assertEquals(expected, actual);
//    }
//
//    @Test(expected = ServiceException.class)
//    public void addWithExceptionInAuthor() {
//
//        News newsForAdd = new News(author, tagList, "title", "short", "looong", LocalDate.now(), LocalDate.now());
//        newsForAdd.setTag(tagList);
//        newsForAdd.setAuthor(author);
//        authorList.clear();
//        tags.add(tag);
//        when(newsRepository.addEntity(newsForAdd)).thenReturn(news);
//        when(tagRepository.query(any(TagByIdSpecification.class))).thenReturn(tags);
//        when(tagRepository.query(any(TagByNameSpecification.class))).thenReturn(null);
//        when(authorRepository.query(any(AuthorByIdSpecification.class))).thenReturn(authorList);
//        when(authorRepository.query(any(AuthorByFullNameSpecification.class))).thenReturn(null);
//
//        NewsDto actual = service.add(newsDto);
//        NewsDto expected = newsDto;
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void getAllNewsByCriteria() {
//        List<String> longList = new ArrayList<>();
//        longList.add("science");
//        newsList.add(news);
//        when(newsRepository.query(any(NewsByCriteriaSpecification.class))).thenReturn(newsList);
//        NewsCriteriaDto newsCriteriaDto = new NewsCriteriaDto();
//        newsCriteriaDto.setAuthorDto(authorMapper.toDto(author));
//        newsCriteriaDto.setTagNameList(longList);
//
//        List<NewsDto> actual = service.findAllNewsByCriteria(newsCriteriaDto);
//        List<NewsDto> expected = new ArrayList() {{
//            add(newsDto);
//        }};
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void getNewsById() {
//        newsList.add(news);
//        when(newsRepository.query(any(NewsByIdSpecification.class))).thenReturn(newsList);
//        NewsDto actual = service.findById(newsDto);
//        assertEquals(newsDto, actual);
//    }
//
//    @Test
//    public void getAllUsersByCriteria() {
//        newsList.add(news);
//        when(newsRepository.query(any(NewsByTitleSpecification.class))).thenReturn(newsList);
//        List<NewsDto> actual = service.findByName(newsDto);
//        List<NewsDto> expected = new ArrayList() {{
//            add(newsDto);
//        }};
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void findById() {
//        newsList.add(news);
//        when(newsRepository.query(any(NewsByIdSpecification.class))).thenReturn(newsList);
//        NewsDto news = service.findById(newsDto);
//        assertEquals(news, newsDto);
//        verify(newsRepository, times(1)).query(any(NewsByIdSpecification.class));
//
//    }
//
//    @Test(expected = ServiceException.class)
//    public void findByIdWithException() {
//        when(newsRepository.query(any(NewsByIdSpecification.class))).thenReturn(new ArrayList<>());
//        service.findById(newsDto);
//        verify(newsRepository, times(1)).removeEntity(news);
//
//    }
//
//    @Test
//    public void findCount(){
//        when(newsRepository.findCount()).thenReturn(1L);
//        long expected = 1L;
//        long actual = service.findCount();
//        Assert.assertEquals(expected, actual);
//    }
//
//}
