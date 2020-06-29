package com.epam.lab.repository.impl;

import com.epam.lab.exception.RepositoryException;
import com.epam.lab.model.Author;
import com.epam.lab.repository.EntityRepository;
import com.epam.lab.specification.EntitySpecification;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional("hibernateTransactionManager")
public class AuthorRepository implements EntityRepository<Author> {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Author addEntity(Author author) {
        entityManager.persist(author);
        return author;
    }

    @Override
    public void removeEntity(Author author) {
        author = entityManager.find(Author.class, author.getId());
        if (author != null) {
            entityManager.remove(author);
        } else {
            throw new RepositoryException("wrong id");
        }
    }

    @Override
    public Author updateEntity(Author author) {
        if (isAuthorIdExist(author.getId())) {
            return entityManager.merge(author);
        } else {
            throw new RepositoryException("no such author");
        }
    }

    private boolean isAuthorIdExist(long id) {
        return entityManager.find(Author.class, id) != null;
    }

    @Override
    public List<Author> query(EntitySpecification<Author> entitySpecification) {
        return entitySpecification.specified(entityManager);
    }

    @Override
    public long findCount() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(Author.class)));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }


}
