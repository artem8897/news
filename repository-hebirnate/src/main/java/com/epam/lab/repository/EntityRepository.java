package com.epam.lab.repository;

import com.epam.lab.model.Entity;
import com.epam.lab.specification.EntitySpecification;

import java.util.List;

public interface EntityRepository<E extends Entity> {

    E addEntity(E e);

    void removeEntity(E e);

    E updateEntity(E e);

    List<E> query(EntitySpecification<E> entitySpecification);

    long findCount();
}
