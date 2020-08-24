/*
Project .....................: schedule-challenge
Creation Date ...............: 24/08/2020 12:40:22
Developer....................: ederluciano
Copyright....................: 2020
Codification.................: UTF-8
...........................................................
 Éder L. Costa - © Copyright 2020 - All Rights Reserved
...........................................................
*/
package br.com.edersystems.schedulechallenge.model.request.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserPostRequest implements java.io.Serializable {

    private static final long serialVersionUID = 2069178521837841911L;

    private String cpf;

    private String nome;

    public UserPostRequest() {
        this(null, null);
    }

    public UserPostRequest(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
    }
}
