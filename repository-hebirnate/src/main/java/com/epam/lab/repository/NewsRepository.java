package com.epam.lab.repository;

import com.epam.lab.criteria.NewsCriteria;
import com.epam.lab.model.News;

public interface NewsRepository extends EntityRepository<News> {

    long findCount(NewsCriteria newsCriteria);

}
