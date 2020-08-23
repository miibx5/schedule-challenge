/*
Project .....................: schedule-challenge
Creation Date ...............: 22/08/2020 17:36:33
Developer....................: ederluciano
Copyright....................: 2020
Codification.................: UTF-8
...........................................................
 Éder L. Costa - © Copyright 2020 - All Rights Reserved
...........................................................
*/
package br.com.edersystems.schedulechallenge.exceptions;

import lombok.Getter;

@Getter
public class GenericError implements java.io.Serializable {

    private static final long serialVersionUID = -2303159927083891538L;

    private final String code;

    private final String message;

    public GenericError(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
