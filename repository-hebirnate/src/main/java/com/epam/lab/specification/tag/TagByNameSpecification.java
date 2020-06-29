package com.epam.lab.specification.tag;

import com.epam.lab.model.Tag;
import com.epam.lab.specification.EntitySpecification;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class TagByNameSpecification implements EntitySpecification<Tag> {

    private String name;
    private static final String TAG_NAME = "name";

    public TagByNameSpecification(String name) {
        this.name = name;
    }

    @Override
    public List<Tag> specified(EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);
        if(name != null){
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(TAG_NAME), name));
        }else {
            criteriaQuery.select(root);
        }
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
