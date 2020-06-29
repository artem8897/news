package com.epam.lab.specification.news;

import com.epam.lab.criteria.NewsCriteria;
import com.epam.lab.model.News;
import com.epam.lab.specification.EntitySpecification;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class NewsByCriteriaSpecification implements EntitySpecification<News> {

    private NewsCriteria newsCriteria;
    private static final String AUTHOR = "author";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String TAGS = "tags";
    private static final String ID = "id";
    private List<Predicate> list = new ArrayList<>();
    private int numberOfCriteria = 0;

    public NewsByCriteriaSpecification(NewsCriteria newsCriteria) {
        this.newsCriteria = newsCriteria;
    }

    @Override
    public List<News> specified(EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<News> criteriaQuery = criteriaBuilder.createQuery(News.class);

        Root<News> newsRoot = criteriaQuery.from(News.class);
        criteriaQuery.select(newsRoot);

        addTagSearch(newsRoot);
        addAuthorNameSearch(criteriaBuilder, newsRoot);
        addAuthorSurnameSearch(criteriaBuilder, newsRoot);
        Predicate havingPredicate = criteriaBuilder.ge(criteriaBuilder.count(newsRoot), numberOfCriteria);
        Predicate[] predicateArray = new Predicate[list.size()];
        list.toArray(predicateArray);

        criteriaQuery.where(predicateArray).groupBy(newsRoot.get(AUTHOR).get(ID), newsRoot.get(AUTHOR).get(NAME),
                newsRoot.get(AUTHOR).get(SURNAME), newsRoot).having(havingPredicate);

        if (newsCriteria.getSortingType() != null) {
            criteriaQuery.orderBy(newsCriteria.getSortingType().getOrder(criteriaBuilder, newsRoot));
        }

        TypedQuery<News> typedQuery = entityManager.createQuery(criteriaQuery);

        if(newsCriteria.getPageNumber()!=null && newsCriteria.getPageSize()!= null){
            typedQuery.setFirstResult(newsCriteria.getPageNumber()).setMaxResults(newsCriteria.getPageSize());
        }

        return typedQuery.getResultList();

    }

    private void addTagSearch(Root<News> newsRoot) {
        if (newsCriteria.getTagNameList() != null && !newsCriteria.getTagNameList().isEmpty()) {
            Expression<String> expression = newsRoot.join(TAGS).get(NAME);
            Predicate tagPredicate = expression.in(newsCriteria.getTagNameList());
            list.add(tagPredicate);
            numberOfCriteria += newsCriteria.getTagNameList().size();
        }
    }

    private void addAuthorNameSearch(CriteriaBuilder criteriaBuilder, Root<News> newsRoot) {
        if (newsCriteria.getAuthor()!= null && newsCriteria.getAuthor().getName() != null) {
            Predicate authorNamePredicate = criteriaBuilder.equal(newsRoot.get(AUTHOR).get(NAME), newsCriteria.getAuthor().getName());
            list.add(authorNamePredicate);
        }
    }

    private void addAuthorSurnameSearch(CriteriaBuilder criteriaBuilder, Root<News> newsRoot) {
        if (newsCriteria.getAuthor() != null && newsCriteria.getAuthor().getSurname() != null) {
            Predicate authorSurnamePredicate = criteriaBuilder.equal(newsRoot.get(AUTHOR).get(SURNAME), newsCriteria.getAuthor().getSurname());
            list.add(authorSurnamePredicate);
        }
    }

}
