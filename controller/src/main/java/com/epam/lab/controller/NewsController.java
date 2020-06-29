package com.epam.lab.controller;

import com.epam.lab.criteria.SortType;
import com.epam.lab.dto.criteria.NewsCriteriaDto;
import com.epam.lab.dto.entity.AuthorDto;
import com.epam.lab.dto.entity.NewsDto;
import com.epam.lab.service.NewsService;
import com.epam.lab.validator.annotations.IsEnum;
import com.epam.lab.validator.annotations.TagNamesList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

import static com.epam.lab.controller.ControllerMessage.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("news")
@Validated
public class NewsController {

    private NewsService newsService;
    private static final String REG_EX_NAME = "[A-Za-z]{3,30}";

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NewsDto> findNews(@PathVariable @NotNull(message = ID_NULL_MESSAGE) @Min(value = 1, message = WRONG_ID_MESSAGE) Long id) {
        NewsDto newsDto = new NewsDto();
        newsDto.setId(id);
        return ResponseEntity.ok(newsService.findById(newsDto));
    }

    @GetMapping(value = "/title/{title}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NewsDto>> findNews(@PathVariable @NotNull(message = ID_NULL_MESSAGE) @Pattern(regexp = REG_EX_NAME) String title) {
        NewsDto newsDto = new NewsDto();
        newsDto.setTitle(title);
        return ResponseEntity.ok(newsService.findByName(newsDto));
    }

    @GetMapping()
    public ResponseEntity<List<NewsDto>> search(@RequestParam(required = false) @Pattern(regexp = REG_EX_NAME, message = WRONG_NAME) String name,
                                                @RequestParam(required = false) Integer page,
                                                @RequestParam(required = false) Integer pageSize,
                                                @RequestParam(required = false) @Pattern(regexp = REG_EX_NAME, message = WRONG_NAME) String surname,
                                                @RequestParam(required = false) @TagNamesList List<String> tagsNameList,
                                                @RequestParam(required = false) @IsEnum String sortingType) {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setName(name);
        authorDto.setSurname(surname);
        NewsCriteriaDto newsCriteria = new NewsCriteriaDto();
        newsCriteria.setAuthorDto(authorDto);
        newsCriteria.setPageNumber(page);
        newsCriteria.setPageSize(pageSize);
        newsCriteria.setTagNameList(tagsNameList);
        if (sortingType != null) {
            newsCriteria.setSortingType(SortType.valueOf(sortingType.toUpperCase()));
        }
        List<NewsDto> newsDtoList = newsService.findAllNewsByCriteria(newsCriteria);
        return ResponseEntity.ok(newsDtoList);
    }

    @GetMapping(value = "/count")
    public long getCountOFNews(@RequestParam(required = false) @Pattern(regexp = REG_EX_NAME, message = WRONG_NAME) String name,
                               @RequestParam(required = false) @Pattern(regexp = REG_EX_NAME, message = WRONG_NAME) String surname,
                               @RequestParam(required = false) @TagNamesList List<String> tagsNameList) {

        AuthorDto authorDto = new AuthorDto();
        authorDto.setName(name);
        authorDto.setSurname(surname);
        NewsCriteriaDto newsCriteria = new NewsCriteriaDto();
        newsCriteria.setAuthorDto(authorDto);
        newsCriteria.setTagNameList(tagsNameList);
        return newsService.findCount(newsCriteria);
    }

    @PostMapping(headers = {"Content-type=application/json"})
    public ResponseEntity<NewsDto> create(@Validated @RequestBody NewsDto newsDto) {
        newsDto = newsService.add(newsDto);
        return ResponseEntity.ok(newsDto);
    }

    @PutMapping(value = "/{id}", headers = {"Content-type=application/json"})
    public ResponseEntity<NewsDto> update(@RequestBody @Valid NewsDto newsDto, @PathVariable @NotNull(message = ControllerMessage.ID_NULL_MESSAGE) @Min(value = 1, message = ControllerMessage.WRONG_ID_MESSAGE) Long id) {
        newsDto.setId(id);
        newsDto = newsService.update(newsDto);
        return ResponseEntity.ok(newsDto);
    }

    @DeleteMapping(value = "/{id}", headers = {"Content-type=application/json"})
    public ResponseEntity<String> delete(@PathVariable @NotNull(message = ControllerMessage.ID_NULL_MESSAGE) @Min(value = 1, message = ControllerMessage.WRONG_ID_MESSAGE) Long id) {
        NewsDto news = new NewsDto();
        news.setId(id);
        newsService.delete(news);
        return ResponseEntity.ok("deleted news with id = " + id);
    }
}
