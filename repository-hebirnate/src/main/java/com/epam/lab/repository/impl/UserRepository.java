package com.epam.lab.repository.impl;

import com.epam.lab.exception.RepositoryException;
import com.epam.lab.model.User;
import com.epam.lab.repository.EntityRepository;
import com.epam.lab.specification.EntitySpecification;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserRepository implements EntityRepository<User> {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public User addEntity(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public void removeEntity(User user) {
        user = entityManager.find(User.class, user.getId());
        if (user != null) {
            entityManager.remove(user);
        } else {
            throw new RepositoryException("no such user");
        }
    }

    @Override
    public User updateEntity(User user) {
        if (isUserIdExist(user.getId())) {
            return entityManager.merge(user);
        } else {
            throw new RepositoryException("no user for update");
        }
    }

    private boolean isUserIdExist(long id) {
        return entityManager.find(User.class, id) != null;
    }


    @Override
    public List<User> query(EntitySpecification<User> userSpecification) {
        return userSpecification.specified(entityManager);
    }

    @Override
    public long findCount() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(User.class)));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

}
