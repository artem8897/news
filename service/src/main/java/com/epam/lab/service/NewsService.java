package com.epam.lab.service;

import com.epam.lab.criteria.NewsCriteria;
import com.epam.lab.dto.criteria.NewsCriteriaDto;
import com.epam.lab.dto.entity.NewsDto;

import java.util.List;

public interface NewsService extends EntityService<NewsDto> {

    /**
     *
     * @param newsCriteriaDto
     * @return List of news found by Tags and/or author or if newsCriteria is empty returns list of all news
     */

    List<NewsDto> findAllNewsByCriteria(NewsCriteriaDto newsCriteriaDto) ;

    long findCount(NewsCriteriaDto newsCriteria);

}
