/*
Project .....................: schedule-challenge
Creation Date ...............: 22/08/2020 17:13:46
Developer....................: ederluciano
Copyright....................: 2020
Codification.................: UTF-8
...........................................................
 Éder L. Costa - © Copyright 2020 - All Rights Reserved
...........................................................
*/
package br.com.edersystems.schedulechallenge.model.repositories;

import br.com.edersystems.schedulechallenge.model.entities.Schedule;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends PagingAndSortingRepository<Schedule, Long> {

    Optional<Schedule> findByScheduleDate(LocalDate scheduleDate);
}
