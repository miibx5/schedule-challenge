/*
Project .....................: schedule-challenge
Creation Date ...............: 22/08/2020 17:13:55
Developer....................: ederluciano
Copyright....................: 2020
Codification.................: UTF-8
...........................................................
 Éder L. Costa - © Copyright 2020 - All Rights Reserved
...........................................................
*/
package br.com.edersystems.schedulechallenge.model.services;

import br.com.edersystems.schedulechallenge.exceptions.NotFoundException;
import br.com.edersystems.schedulechallenge.exceptions.UnProcessableEntityException;
import br.com.edersystems.schedulechallenge.model.entities.Schedule;
import br.com.edersystems.schedulechallenge.model.entities.enums.ScheduleType;
import br.com.edersystems.schedulechallenge.model.repositories.ScheduleRepository;
import br.com.edersystems.schedulechallenge.model.request.ScheduleRequestCreate;
import br.com.edersystems.schedulechallenge.model.response.ScheduleResponseTO;
import br.com.edersystems.schedulechallenge.util.data.LocalDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;

@Service
public class ScheduleService {

    private final ScheduleRepository repository;

    @Autowired
    private LocalDateUtil localDateUtil;

    @Autowired
    public ScheduleService(ScheduleRepository repository) {
        this.repository = repository;
    }

    public ScheduleResponseTO createSchedule(ScheduleRequestCreate request) {
        Optional<Schedule> schedule = repository.findByScheduleDate(getScheduleDate(request));
        if(schedule.isPresent()) {
            throw new UnProcessableEntityException("schedule.date_there_is_already_scheduling");
        }
        return buildScheduleResponse(repository.save(buildSchedule(request)));
    }

    public ScheduleResponseTO getScheduleById(Long id) {
        Schedule scheduleToReturn = repository.findById(id).orElseThrow(() -> new NotFoundException("schedule.notfound"));
        return buildScheduleResponse(scheduleToReturn);
    }

    private ScheduleResponseTO buildScheduleResponse(Schedule schedule) {
        return new ScheduleResponseTO(schedule.getScheduleDate().toString(), schedule.getDescription(), schedule.getId());
    }

    private Schedule buildSchedule(ScheduleRequestCreate request) {
        return new Schedule(request.getId(), getScheduleDate(request), request.getDescricao(), getScheduleType(request.getTipo()));
    }

    private LocalDate getScheduleDate(ScheduleRequestCreate request) {
        validRequest(request);
        if(localDateUtil.isDateIsLessThanTomorrow(localDateUtil.convertStringToDate(request.getData()))) {
            throw new UnProcessableEntityException("schedule.not_register");
        }
        switch(getScheduleType(request.getTipo())) {
            case CLIENT:
                System.out.println("getScheduleType#localDateUtil.getTomorrow():" + localDateUtil.getTomorrow());
                System.out.println("getScheduleType:" + localDateUtil.getTomorrow().plusDays(BigDecimal.ONE.intValue()));
                LocalDate testLocalDate = localDateUtil.getTomorrow().plusDays(BigDecimal.ONE.intValue());
                System.out.println("getScheduleType#testLocalDate: " + testLocalDate);
                return localDateUtil.getTomorrow().plusDays(BigDecimal.ONE.intValue());
            case FORUM:
                return localDateUtil.getTomorrow().plusDays(3);
            case NOTARY:
                return localDateUtil.getTomorrow().plusDays(2);
            default:
                return localDateUtil.getTomorrow();
        }
    }

    private void validRequest(ScheduleRequestCreate request) {
        if(Objects.isNull(request.getData()) || request.getData().isBlank()) {
            throw new UnsupportedOperationException("schedule.required_date");
        }
        if(Objects.isNull(request.getDescricao()) || request.getDescricao().isBlank()) {
            throw new UnProcessableEntityException("schedule.required_description");
        }
        if(Objects.isNull(request.getTipo())) {
            throw new UnProcessableEntityException("schedule.required_type");
        }
        if(Objects.isNull(request.getId())) {
            throw new UnProcessableEntityException("schedule.required_id");
        }
    }

    private ScheduleType getScheduleType(String type) {
        System.out.println(type.toUpperCase());
        return EnumSet.allOf(ScheduleType.class).stream()
                .filter(t -> t.name().equals(type.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("schedule.type_not_found"));
    }
}
