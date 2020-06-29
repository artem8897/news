package com.epam.lab.specification;

import com.epam.lab.model.Entity;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public interface EntitySpecification<T extends Entity> {

    List<T> specified(EntityManager entityManager);
}
