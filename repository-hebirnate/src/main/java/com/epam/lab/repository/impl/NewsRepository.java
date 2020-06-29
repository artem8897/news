package com.epam.lab.repository.impl;

import com.epam.lab.criteria.NewsCriteria;
import com.epam.lab.exception.RepositoryException;
import com.epam.lab.model.News;
import com.epam.lab.repository.EntityRepository;
import com.epam.lab.specification.EntitySpecification;
import org.hibernate.ReplicationMode;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional("hibernateTransactionManager")
public class NewsRepository implements com.epam.lab.repository.NewsRepository {

    @PersistenceContext
    EntityManager entityManager;

    private static final String AUTHOR = "author";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String TAGS = "tags";
    private static final String ID = "id";
    private List<Predicate> list = new ArrayList<>();
    private int numberOfCriteria = 0;

    @Override
    public News addEntity(News news) {
        entityManager.unwrap(Session.class).replicate(news, ReplicationMode.IGNORE);
        return news;
    }

    @Override
    public void removeEntity(News news) {
        news = entityManager.find(News.class, news.getId());
        if (news != null) {
            entityManager.remove(news);
        } else {
            throw new RepositoryException("wrong id");
        }
    }

    @Override
    public News updateEntity(News news) {
        if (isNewsIdExist(news.getId())) {
            entityManager.unwrap(Session.class).replicate(news, ReplicationMode.OVERWRITE);
            return news;
        } else {
            throw new RepositoryException("no such news");
        }
    }

    private boolean isNewsIdExist(long id) {
        return entityManager.find(News.class, id) != null;
    }

    public long findCount() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(News.class)));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public List<News> query(EntitySpecification<News> entitySpecification) {
        return entitySpecification.specified(entityManager);

    }

    @Override
    public long findCount(NewsCriteria newsCriteria) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);

        Root<News> newsRoot = criteriaQuery.from(News.class);
        criteriaQuery.select((criteriaBuilder.count(newsRoot)));

        addTagSearch(newsRoot, newsCriteria);
        addAuthorNameSearch(criteriaBuilder, newsRoot, newsCriteria);
        addAuthorSurnameSearch(criteriaBuilder, newsRoot, newsCriteria);
        Predicate havingPredicate = criteriaBuilder.ge(criteriaBuilder.count(newsRoot), numberOfCriteria);
        Predicate[] predicateArray = new Predicate[list.size()];
        list.toArray(predicateArray);

        criteriaQuery.where(predicateArray).groupBy(newsRoot).having(havingPredicate);

        TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery);

        return typedQuery.getResultList().size();

    }

    private void addTagSearch(Root<News> newsRoot, NewsCriteria newsCriteria) {
        if (newsCriteria.getTagNameList() != null && !newsCriteria.getTagNameList().isEmpty()) {
            Expression<String> expression = newsRoot.join(TAGS).get(NAME);
            Predicate tagPredicate = expression.in(newsCriteria.getTagNameList());
            list.add(tagPredicate);
            numberOfCriteria += newsCriteria.getTagNameList().size();
        }
    }

    private void addAuthorNameSearch(CriteriaBuilder criteriaBuilder, Root<News> newsRoot, NewsCriteria newsCriteria) {
        if (newsCriteria.getAuthor()!= null && newsCriteria.getAuthor().getName() != null) {
            Predicate authorNamePredicate = criteriaBuilder.equal(newsRoot.get(AUTHOR).get(NAME), newsCriteria.getAuthor().getName());
            list.add(authorNamePredicate);
        }
    }

    private void addAuthorSurnameSearch(CriteriaBuilder criteriaBuilder, Root<News> newsRoot, NewsCriteria newsCriteria) {
        if (newsCriteria.getAuthor() != null && newsCriteria.getAuthor().getSurname() != null) {
            Predicate authorSurnamePredicate = criteriaBuilder.equal(newsRoot.get(AUTHOR).get(SURNAME), newsCriteria.getAuthor().getSurname());
            list.add(authorSurnamePredicate);
        }
    }
}
