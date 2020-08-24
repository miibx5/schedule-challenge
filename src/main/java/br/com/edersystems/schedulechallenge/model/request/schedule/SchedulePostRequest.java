/*
Project .....................: schedule-challenge
Creation Date ...............: 22/08/2020 17:17:52
Developer....................: ederluciano
Copyright....................: 2020
Codification.................: UTF-8
...........................................................
 Éder L. Costa - © Copyright 2020 - All Rights Reserved
...........................................................
*/
package br.com.edersystems.schedulechallenge.model.request.schedule;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SchedulePostRequest implements java.io.Serializable {

    private static final long serialVersionUID = 4027854917257257044L;

    private String cpf;

    private String data;

    private String descricao;

    private String tipo;

    public SchedulePostRequest() {
        this(null, null, null, null);
    }

    public SchedulePostRequest(String cpf, String data, String descricao, String tipo) {
        this.cpf = cpf;
        this.data = data;
        this.descricao = descricao;
        this.tipo = tipo;
    }
}