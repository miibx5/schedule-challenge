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
package br.com.edersystems.schedulechallenge.model.repositories.schedule;

import br.com.edersystems.schedulechallenge.model.entities.Schedule;
import br.com.edersystems.schedulechallenge.model.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends PagingAndSortingRepository<Schedule, Long> {

    Optional<Schedule> findByOwnerAndScheduleDate(User owner, LocalDate scheduleDate);

    Page<Schedule> findAllByOwner(User owner, Pageable pageable);
}
