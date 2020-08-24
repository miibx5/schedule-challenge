/*
Project .....................: schedule-challenge
Creation Date ...............: 22/08/2020 17:13:35
Developer....................: ederluciano
Copyright....................: 2020
Codification.................: UTF-8
...........................................................
 Éder L. Costa - © Copyright 2020 - All Rights Reserved
...........................................................
*/
package br.com.edersystems.schedulechallenge.model.entities;

import br.com.edersystems.schedulechallenge.model.entities.enums.ScheduleType;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Entity
@Table(name = "schedules")
public class Schedule implements java.io.Serializable {

    private static final long serialVersionUID = -1427407054510382174L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "schedule.required_user")
    @JoinColumn(name = "owner_id", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL)
    private final User owner;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "schedule.required_type")
    private final ScheduleType type;

    @NotNull(message = "schedule.required_date")
    @Column(nullable = false)
    private final LocalDate scheduleDate;

    @NotBlank(message = "schedule.required_description")
    @Column(columnDefinition = "text", nullable = false)
    private final String description;

    public Schedule() {
        this(null, null, null, null);
    }

    public Schedule(User owner, ScheduleType type, LocalDate scheduleDate, String description) {
        this.owner = owner;
        this.type = type;
        this.scheduleDate = scheduleDate;
        this.description = description;
    }
}
