/*
Project .....................: schedule-challenge
Creation Date ...............: 24/08/2020 13:07:32
Developer....................: ederluciano
Copyright....................: 2020
Codification.................: UTF-8
...........................................................
 Éder L. Costa - © Copyright 2020 - All Rights Reserved
...........................................................
*/
package br.com.edersystems.schedulechallenge.model.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Setter
@Getter
public class PaginatedRequest implements Pageable, java.io.Serializable {

    private static final long serialVersionUID = 3369612505624437482L;

    private int limit;

    private int offset;

    private Sort sort;

    public PaginatedRequest() {
        this.sort = Sort.by(Sort.Direction.ASC, "id");
    }

    public PaginatedRequest(int offset, int limit) {
        this(offset, limit, Sort.by(Sort.Direction.ASC, "id"));
    }

    public PaginatedRequest(int offset, int limit, Sort sort) {
        if(offset < 0) {
            throw new IllegalArgumentException("schedule.offset_less_than_zero");
        }

        if(limit < 1) {
            throw new IllegalArgumentException("schedule.limit_less_than_one");
        }
        this.limit = limit;
        this.offset = offset;
        this.sort = sort;
    }

    public PaginatedRequest(int offset, int limit, Sort.Direction direction, String... properties) {
        this(offset, limit, Sort.by(direction, properties));
    }


    @Override
    public int getPageNumber() {
        return offset / limit;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return new PaginatedRequest(Long.valueOf(getOffset()).intValue() + getPageSize(), getPageSize(), getSort());
    }

    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? new PaginatedRequest(Long.valueOf(getOffset()).intValue() - getPageSize(), getPageSize(), getSort()) : this;
    }

    @Override
    public Pageable first() {
        return hasPrevious() ? previousOrFirst() : first();
    }

    @Override
    public boolean hasPrevious() {
        return offset > limit;
    }
}
