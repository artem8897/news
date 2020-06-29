package com.epam.lab.specification.news;

import com.epam.lab.model.News;
import com.epam.lab.specification.EntitySpecification;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class NewsByTitleSpecification implements EntitySpecification<News> {

    private String title;
    private static final String NEWS_TITLE = "title";

    public NewsByTitleSpecification(String title) {
        this.title = title;
    }

    @Override
    public List<News> specified(EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<News> criteriaQuery = criteriaBuilder.createQuery(News.class);
        Root<News> newsRoot = criteriaQuery.from(News.class);
        criteriaQuery.select(newsRoot).where(criteriaBuilder.equal(newsRoot.get(NEWS_TITLE), title));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
