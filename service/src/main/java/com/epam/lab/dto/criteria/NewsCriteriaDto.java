package com.epam.lab.dto.criteria;

import com.epam.lab.criteria.SortType;
import com.epam.lab.dto.entity.AuthorDto;

import java.util.List;
import java.util.Objects;

public class NewsCriteriaDto {

    private AuthorDto authorDto;
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

    public AuthorDto getAuthorDto() {
        return authorDto;
    }

    public void setAuthorDto(AuthorDto authorDto) {
        this.authorDto = authorDto;
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
        NewsCriteriaDto that = (NewsCriteriaDto) o;
        return Objects.equals(authorDto, that.authorDto) &&
                Objects.equals(tagNameList, that.tagNameList) &&
                sortingType == that.sortingType &&
                Objects.equals(pageNumber, that.pageNumber) &&
                Objects.equals(pageSize, that.pageSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorDto, tagNameList, sortingType, pageNumber, pageSize);
    }

    @Override
    public String toString() {
        return "NewsCriteriaDto{" +
                "authorDto=" + authorDto +
                ", tagNameList=" + tagNameList +
                ", sortingType=" + sortingType +
                ", pagNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                '}';
    }

}
