/*
Project .....................: schedule-challenge
Creation Date ...............: 23/08/2020 15:31:42
Developer....................: ederluciano
Copyright....................: 2020
Codification.................: UTF-8
...........................................................
 Éder L. Costa - © Copyright 2020 - All Rights Reserved
...........................................................
*/
package br.com.edersystems.schedulechallenge.model.repositories;

import br.com.edersystems.schedulechallenge.model.entities.Schedule;
import br.com.edersystems.schedulechallenge.model.entities.User;
import br.com.edersystems.schedulechallenge.model.entities.enums.ScheduleType;
import br.com.edersystems.schedulechallenge.model.repositories.schedule.ScheduleRepository;
import br.com.edersystems.schedulechallenge.model.request.PaginatedRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ScheduleRepositoryTest {

    @Autowired
    private ScheduleRepository repository;

    @Test
    public void successfullScheduleCreation() {
        Schedule schedule = repository.save(new Schedule(new User("name", "013.717.857-32"), ScheduleType.CLIENT, LocalDate.of(2020, 8, 23), "Teste de criação da agenda"));
        assertNotNull(schedule);
        System.out.println(schedule.getScheduleDate());
        assertNotNull(schedule.getId());
    }

    @Test
    public void getSuccessfullScheduleByOwnerAndScheduleDate() {
        Schedule schedule = repository.save(new Schedule(new User("name", "013.717.857-32"), ScheduleType.CLIENT, LocalDate.of(2020, 8, 23), "Teste de criação da agenda"));
        assertNotNull(schedule);
        Optional<Schedule> scheduleRetrivied = repository.findByOwnerAndScheduleDate(schedule.getOwner(), schedule.getScheduleDate());
        assertTrue(scheduleRetrivied.isPresent());
    }

    @Test
    public void getSuccessfullScheduleByOwner() {
        User owner = new User("name", "013.717.857-32");
        Schedule schedule = repository.save(new Schedule(owner, ScheduleType.CLIENT, LocalDate.of(2020, 8, 23), "Teste de criação da agenda"));
        owner = schedule.getOwner();
        Schedule schedule1 = repository.save(new Schedule(owner, ScheduleType.CLIENT, LocalDate.of(2020, 8, 24), "Teste de criação da agenda"));
        Schedule schedule2 = repository.save(new Schedule(owner, ScheduleType.CLIENT, LocalDate.of(2020, 8, 25), "Teste de criação da agenda"));
        assertNotNull(schedule);
        assertNotNull(schedule1);
        assertNotNull(schedule2);
        List<Schedule> scheduleRetrivieds = repository.findAllByOwner(schedule.getOwner(), new PaginatedRequest(0, 2)).getContent();
        assertFalse(scheduleRetrivieds.isEmpty());
        assertTrue(scheduleRetrivieds.size() > BigDecimal.ONE.intValue());
    }
}
