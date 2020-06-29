package com.epam.lab.service.impl;
import com.epam.lab.dto.entity.TagDto;
import com.epam.lab.mapper.TagMapper;
import com.epam.lab.exception.ServiceException;
import com.epam.lab.model.Tag;
import com.epam.lab.repository.EntityRepository;
import com.epam.lab.service.TagService;
import com.epam.lab.specification.tag.TagByIdSpecification;
import com.epam.lab.specification.tag.TagByNameSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TagServiceImpl implements TagService {

    private EntityRepository<Tag> tagRepository;
    private TagMapper tagMapper;

    @Autowired
    public TagServiceImpl(EntityRepository<Tag> tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    @Override
    public TagDto add(TagDto tagDto) {
        if (tagDto.getId() == null) {
            checkForExistingTagName(tagDto.getName());
            Tag tag = tagMapper.toEntity(tagDto);
            Tag addedTag = tagRepository.addEntity(tag);
            return tagMapper.toDto(addedTag);
        } else {
            throw new ServiceException("cannot be add with id");
        }
    }

    private void checkForExistingTagName(String name) {
        TagByNameSpecification tagByNameSpecification = new TagByNameSpecification(name);
        List<Tag> listOfTags = tagRepository.query(tagByNameSpecification);
        if (!listOfTags.isEmpty()) {
            throw new ServiceException("you try to create tag existing in database, choose from existing tags");
        }
    }

    @Override
    public TagDto update(TagDto tagDto) {
        checkForExistingTagId(tagDto.getId());
        checkForExistingTagName(tagDto.getName());
        Tag tag = tagMapper.toEntity(tagDto);
        Tag updatedTag = tagRepository.updateEntity(tag);
        return tagMapper.toDto(updatedTag);
    }

    private void checkForExistingTagId(long id) {
        TagByIdSpecification tagByIdSpecification = new TagByIdSpecification(id);
        List<Tag> listOfTags = tagRepository.query(tagByIdSpecification);
        if (listOfTags.isEmpty()) {
            throw new ServiceException("unexpected tag id");
        }
    }

    @Override
    public void delete(TagDto tagDto) {
        Tag tag = tagMapper.toEntity(tagDto);
        tagRepository.removeEntity(tag);
    }

    @Override
    public List<TagDto> findByName(TagDto tagDto) {
        TagByNameSpecification tagByNameSpecification = new TagByNameSpecification(tagDto.getName());
        List<Tag> tagList = tagRepository.query(tagByNameSpecification);
        return convertListTagsToDto(tagList);
    }
    @Override
    public TagDto findById(TagDto tagDto) {
        TagByIdSpecification tagByIdSpecification = new TagByIdSpecification(tagDto.getId());
        List<Tag> tagList = tagRepository.query(tagByIdSpecification);
        if (tagList.size() == 1) {
            Tag tag = tagList.get(0);
            return tagMapper.toDto(tag);
        } else {
            throw new ServiceException("no such tag in database");
        }
    }

    public long findCount(){
        return tagRepository.findCount();
    }

    private List<TagDto> convertListTagsToDto(List<Tag> tagList){
        return tagList.stream().map(tagMapper::toDto).collect(Collectors.toList());
    }

}
