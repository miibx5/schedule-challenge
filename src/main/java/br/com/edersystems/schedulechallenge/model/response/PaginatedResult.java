/*
Project .....................: schedule-challenge
Creation Date ...............: 24/08/2020 16:28:50
Developer....................: ederluciano
Copyright....................: 2020
Codification.................: UTF-8
...........................................................
 Éder L. Costa - © Copyright 2020 - All Rights Reserved
...........................................................
*/
package br.com.edersystems.schedulechallenge.model.response;


import lombok.Getter;

import java.util.List;

@Getter
public class PaginatedResult<T> implements java.io.Serializable {

    public static final long DEFAULT_OFFSET = 0;

    public static final int DEFAULT_MAX_NO_OF_ROWS = 100;

    private static final long serialVersionUID = -7750652274961152062L;

    private final Long offset;

    private final int limit;

    private final long totalElements;

    private final List<T> elements;

    public PaginatedResult(List<T> elements, long totalElements, Long offset, int limit) {
        this.elements = elements;
        this.totalElements = totalElements;
        this.offset = offset;
        this.limit = limit;
    }


}