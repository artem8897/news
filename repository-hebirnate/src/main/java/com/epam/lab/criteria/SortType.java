package com.epam.lab.criteria;

import com.epam.lab.model.News;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum SortType {

    AUTHOR("AUTHOR"){
        @Override
        public List<Order> getOrder(CriteriaBuilder criteriaBuilder, Root<News> newsRoot) {
            List<Order> orders = new ArrayList<>();
            orders.add(criteriaBuilder.asc(newsRoot.get("author").get("name")));
            orders.add(criteriaBuilder.asc(newsRoot.get("author").get("surname")));
            return orders;
        }
    },
    DATE("DATE"){
        @Override
        public List<Order> getOrder(CriteriaBuilder criteriaBuilder, Root<News> newsRoot) {
            List<Order> orders = new ArrayList<>();
            orders.add(criteriaBuilder.asc(newsRoot.get("date")));
            return orders;
        }
    };

    public abstract List<Order> getOrder(CriteriaBuilder criteriaBuilder, Root<News> newsRoot);

    private String sorting;

    SortType(String sorting) {
        this.sorting = sorting;
    }

    @Override
    public String toString() {
        return "SortType{" +
                "sorting='" + sorting + '\'' +
                '}';
    }

    public static boolean isExist(String sorting){
        return Arrays.stream(SortType.values()).anyMatch(sortType -> sortType.name().equalsIgnoreCase(sorting));
    }
}
