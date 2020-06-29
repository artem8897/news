package com.epam.lab.mapper;

import com.epam.lab.dto.entity.TagDto;
import com.epam.lab.model.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TagMapper implements AbstractMapper<TagDto, Tag> {

    private ModelMapper mapper;

    @Autowired
    public TagMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Tag toEntity(TagDto dto) {
            return Objects.isNull(dto) ? null : mapper.map(dto, Tag.class);
        }

        public TagDto toDto(Tag entity) {
            return Objects.isNull(entity) ? null : mapper.map(entity, TagDto.class);
        }
}
