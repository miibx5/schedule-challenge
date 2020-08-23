/*
Project .....................: schedule-challenge
Creation Date ...............: 22/08/2020 17:39:44
Developer....................: ederluciano
Copyright....................: 2020
Codification.................: UTF-8
...........................................................
 Éder L. Costa - © Copyright 2020 - All Rights Reserved
...........................................................
*/
package br.com.edersystems.schedulechallenge.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@Getter
@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class UnProcessableEntityException extends RuntimeException {

    private static final long serialVersionUID = -3490347472239760820L;

    private final String message;

    public UnProcessableEntityException(String message) {
        super(message);
        this.message = message;
    }
}
