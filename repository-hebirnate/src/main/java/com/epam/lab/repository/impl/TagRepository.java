package com.epam.lab.repository.impl;

import com.epam.lab.exception.RepositoryException;
import com.epam.lab.model.Tag;
import com.epam.lab.repository.EntityRepository;
import com.epam.lab.specification.EntitySpecification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;


@Repository
@Transactional
public class TagRepository implements EntityRepository<Tag> {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Tag addEntity(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Override
    public void removeEntity(Tag tag) {
        tag = entityManager.find(Tag.class, tag.getId());
        if (tag != null) {
            entityManager.remove(tag);
        } else {
            throw new RepositoryException("wrong id");
        }
    }

    @Override
    public Tag updateEntity(Tag tag) {
        if (isTagIdExist(tag.getId())) {
            return entityManager.merge(tag);
        } else {
            throw new RepositoryException("no such tag");
        }
    }

    private boolean isTagIdExist(long id) {
        return entityManager.find(Tag.class, id) != null;
    }

    @Override
    public List<Tag> query(EntitySpecification<Tag> entitySpecification) {
        return entitySpecification.specified(entityManager);
    }

    @Override
    public long findCount() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(Tag.class)));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }
}
