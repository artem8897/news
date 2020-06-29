package com.epam.lab.mapper;

import com.epam.lab.dto.entity.AbstractDto;
import com.epam.lab.model.Entity;

 public interface AbstractMapper <T extends AbstractDto, K extends Entity>{

    K toEntity(T dto);
    T toDto(K entity);

}
