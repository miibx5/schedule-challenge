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
import br.com.edersystems.schedulechallenge.model.entities.enums.ScheduleType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ScheduleRepositoryTest {

    @Autowired
    private ScheduleRepository repository;

    @Test
    public void successfullScheduleCreation() {
        Schedule schedule = repository.save(new Schedule(BigDecimal.ONE.longValue(), LocalDate.of(2020, 8, 23), "Teste de criação da agenda", ScheduleType.CLIENT));
        assertNotNull(schedule);
        System.out.println(schedule.getScheduleDate());
        assertNotNull(schedule.getId());
    }

    @Test
    public void getSuccessfullScheduleById() {
        Schedule schedule = repository.save(new Schedule(BigDecimal.ONE.longValue(), LocalDate.of(2020, 8, 23), "Teste de criação da agenda", ScheduleType.CLIENT));
        assertNotNull(schedule);
        Optional<Schedule> scheduleRetrivied = repository.findById(schedule.getId());
        assertTrue(scheduleRetrivied.isPresent());
    }
}
