package com.epam.lab.service;

import com.epam.lab.dto.entity.AbstractDto;

import java.util.List;

public interface EntityService<T extends AbstractDto> {

    T add(T entity);

    T update(T entity) ;

    /**
     *
     * @param entity
     * @return amount of delted news when author was deleted
     */
    void delete(T entity)  ;

    List<T> findByName(T entity);

    T findById(T entity);

    long findCount();

}
