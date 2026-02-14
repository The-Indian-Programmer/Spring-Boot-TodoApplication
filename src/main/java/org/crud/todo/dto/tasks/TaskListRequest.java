package org.crud.todo.dto.tasks;

import jakarta.validation.constraints.*;


public class TaskListRequest {

    @Min(value = 1, message = "Page must be greater than 0.")
    private Integer page = 1;

    @Min(value = 1, message = "Limit must be at least 1.")
    @Max(value = 100, message = "Limit cannot exceed 100.")
    private Integer limit = 10;

    @Size(max = 100, message = "Query cannot exceed 100 characters.")
    private String query;

    @Pattern(
            regexp = "^(id|title|dueDate|createdAt|updatedAt)$",
            message = "Sort field must be one of: id, title, dueDate, createdAt, updatedAt."
    )
    private String sort = "id";

    @Pattern(
            regexp = "^(asc|desc)$",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Sort order must be either 'asc' or 'desc'."
    )
    private String sortorder = "desc";

    public Integer getPage() {
        return page;
    }

    public Integer getLimit() {
        return limit;
    }

    public String getQuery() {
        return query;
    }

    public String getSort() {
        return sort;
    }

    public String getSortorder() {
        return sortorder;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setSortorder(String sortorder) {
        this.sortorder = sortorder;
    }
}
