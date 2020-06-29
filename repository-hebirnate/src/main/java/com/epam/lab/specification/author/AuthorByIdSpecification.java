package com.epam.lab.specification.author;

import com.epam.lab.model.Author;
import com.epam.lab.specification.EntitySpecification;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class AuthorByIdSpecification implements EntitySpecification<Author> {

    private Long id;
    private static final String AUTHOR_ID = "id";

    public AuthorByIdSpecification(Long id) {
        this.id = id;
    }

    @Override
    public List<Author> specified(EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Author> criteriaQuery = criteriaBuilder.createQuery(Author.class);
        Root<Author> root = criteriaQuery.from(Author.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(AUTHOR_ID), id));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
