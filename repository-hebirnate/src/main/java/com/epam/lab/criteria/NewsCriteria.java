package com.epam.lab.criteria;

import com.epam.lab.model.Author;

import java.util.List;
import java.util.Objects;

public class NewsCriteria {

    private Author author;
    private List<String> tagNameList;
    private SortType sortingType;
    private Integer pageNumber;



    private Integer pageSize;

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<String> getTagNameList() {
        return tagNameList;
    }

    public void setTagNameList(List<String> tagNameList) {
        this.tagNameList = tagNameList;
    }

    public SortType getSortingType() {
        return sortingType;
    }

    public void setSortingType(SortType sortingType) {
        this.sortingType = sortingType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsCriteria that = (NewsCriteria) o;
        return Objects.equals(author, that.author) &&
                Objects.equals(tagNameList, that.tagNameList) &&
                sortingType == that.sortingType &&
                Objects.equals(pageNumber, that.pageNumber) &&
                Objects.equals(pageSize, that.pageSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, tagNameList, sortingType, pageNumber, pageSize);
    }

    @Override
    public String toString() {
        return "NewsCriteria{" +
                "author=" + author +
                ", tagNameList=" + tagNameList +
                ", sortingType=" + sortingType +
                ", pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                '}';
    }
}
