package com.epam.lab.controller;

import com.epam.lab.dto.entity.TagDto;
import com.epam.lab.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("tags")
@Validated
public class TagController {

    private TagService tagService;
    private static final String REG_EX_NAME = "[A-Za-z]{3,30}";

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping()
    public ResponseEntity<List<TagDto>> findTag(@RequestParam(required = false) @Pattern(regexp = REG_EX_NAME, message = WRONG_NAME) String name) {
        TagDto tagDto = new TagDto();
        tagDto.setName(name);
        List<TagDto> tagDtoList = tagService.findByName(tagDto);
        return ResponseEntity.ok(tagDtoList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TagDto> findTag(@PathVariable @NotNull(message = NAME_NULL_MESSAGE) @Min(value = 1) Long id) {
        TagDto tagDto = new TagDto();
        tagDto.setId(id);
        tagDto = tagService.findById(tagDto);
        return ResponseEntity.ok(tagDto);
    }

    @PostMapping()
    public ResponseEntity<TagDto> create(@Validated @RequestBody TagDto tagDto) {
        tagDto = tagService.add(tagDto);
        return ResponseEntity.ok(tagDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TagDto> update(@RequestBody @Valid TagDto tagDto, @PathVariable @NotNull(message = ID_NULL_MESSAGE) @Min(value = 1, message = WRONG_ID_MESSAGE) Long id) {
        tagDto.setId(id);
        tagDto = tagService.update(tagDto);
        return ResponseEntity.ok(tagDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable @NotNull(message = ID_NULL_MESSAGE) @Min(value = 1, message = WRONG_ID_MESSAGE) Long id) {
        TagDto tagDto = new TagDto();
        tagDto.setId(id);
        tagService.delete(tagDto);
        return ResponseEntity.ok("tag was deleted with id = " + id);
    }

    @GetMapping(value = "/count")
    public long getCountOFNews() {
        return tagService.findCount();
    }
}