package com.epam.lab.specification.tag;

import com.epam.lab.model.Tag;
import com.epam.lab.specification.EntitySpecification;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class TagByIdSpecification implements EntitySpecification<Tag> {

    private Long id;
    private static final String TAG_ID = "id";

    public TagByIdSpecification(Long id) {
        this.id = id;
    }

    @Override
    public List<Tag> specified(EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);

        Root<Tag> root = criteriaQuery.from(Tag.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(TAG_ID), id));
        return entityManager.createQuery(criteriaQuery).getResultList();

    }
}
