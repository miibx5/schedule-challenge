/*
Project .....................: schedule-challenge
Creation Date ...............: 24/08/2020 11:51:23
Developer....................: ederluciano
Copyright....................: 2020
Codification.................: UTF-8
...........................................................
 Éder L. Costa - © Copyright 2020 - All Rights Reserved
...........................................................
*/
package br.com.edersystems.schedulechallenge.model.response.user;

import br.com.edersystems.schedulechallenge.model.response.schedule.ScheduleTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserTO implements java.io.Serializable {

    private static final long serialVersionUID = 8416410507726189272L;

    private Long id;

    private String cpf;

    private String nome;

    @JsonIgnore
    private List<ScheduleTO> agendamentos;

    public UserTO() {
        this(null, null, null);
    }

    public UserTO(Long id, String cpf, String nome) {
        this(id, cpf, nome, null);
    }

    public UserTO(Long id, String cpf, String nome, List<ScheduleTO> agendamentos) {
        this.id = id;
        this.cpf = cpf;
        this.nome = nome;
        this.agendamentos = agendamentos;
    }
}
