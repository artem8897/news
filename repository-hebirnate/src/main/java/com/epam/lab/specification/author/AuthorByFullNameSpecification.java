package com.epam.lab.specification.author;

import com.epam.lab.model.Author;
import com.epam.lab.specification.EntitySpecification;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorByFullNameSpecification implements EntitySpecification<Author> {

    private String authorName;
    private String authorSurname;
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private List<Predicate> list = new ArrayList<>();


    public AuthorByFullNameSpecification(Author author) {
        this.authorName = author.getName();
        this.authorSurname = author.getSurname();
    }

    @Override
    public List<Author> specified(EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Author> criteriaQuery = criteriaBuilder.createQuery(Author.class);
        Root<Author> root = criteriaQuery.from(Author.class);
        criteriaQuery.select(root);
        addAuthorName(criteriaBuilder, root);
        addAuthorSurname(criteriaBuilder, root);
        Predicate[] predicateArray = new Predicate[list.size()];
        list.toArray(predicateArray);
        criteriaQuery.where(predicateArray);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    private void addAuthorName(CriteriaBuilder criteriaBuilder, Root<Author> authorRoot){
        if(authorName != null){
            Predicate namePredicate = criteriaBuilder.equal(authorRoot.get(NAME), authorName);
            list.add(namePredicate);
        }
    }
    private void addAuthorSurname(CriteriaBuilder criteriaBuilder, Root<Author> authorRoot){
        if(authorSurname != null){
            Predicate surnamePredicate = criteriaBuilder.equal(authorRoot.get(SURNAME), authorSurname);
            list.add(surnamePredicate);
        }
    }
}
