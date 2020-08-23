/*
Project .....................: schedule-challenge
Creation Date ...............: 22/08/2020 17:24:40
Developer....................: ederluciano
Copyright....................: 2020
Codification.................: UTF-8
...........................................................
 Éder L. Costa - © Copyright 2020 - All Rights Reserved
...........................................................
*/
package br.com.edersystems.schedulechallenge.model.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ScheduleResponseTO implements java.io.Serializable {

    private static final long serialVersionUID = -3867289828867270012L;

    private String data;

    private String descricao;

    private Long id;

    public ScheduleResponseTO() {
        this(null, null, null);
    }

    public ScheduleResponseTO(String data, String descricao, Long id) {
        this.data = data;
        this.descricao = descricao;
        this.id = id;
    }
}
