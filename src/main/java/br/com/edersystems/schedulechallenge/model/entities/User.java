/*
Project .....................: schedule-challenge
Creation Date ...............: 24/08/2020 09:31:25
Developer....................: ederluciano
Copyright....................: 2020
Codification.................: UTF-8
...........................................................
 Éder L. Costa - © Copyright 2020 - All Rights Reserved
...........................................................
*/
package br.com.edersystems.schedulechallenge.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User implements java.io.Serializable {

    private static final long serialVersionUID = 6169018015297065498L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "schedule.required_user_cpf")
    @Column(length = 14, nullable = false, unique = true)
    private String cpf;

    @NotBlank(message = "schedule.required_user_name")
    @Column(length = 120, nullable = false)
    private String name;


    @OneToMany(mappedBy = "owner")
    private List<Schedule> schedules;

    public User() {
        this(null, null);
    }

    public User(String cpf, String name) {
        this.cpf = cpf;
        this.name = name;
        this.schedules = new ArrayList<>();
    }
}

