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
package br.com.edersystems.schedulechallenge.model.services.schedule;

import br.com.edersystems.schedulechallenge.exceptions.NotFoundException;
import br.com.edersystems.schedulechallenge.exceptions.UnProcessableEntityException;
import br.com.edersystems.schedulechallenge.model.entities.Schedule;
import br.com.edersystems.schedulechallenge.model.entities.User;
import br.com.edersystems.schedulechallenge.model.entities.enums.ScheduleType;
import br.com.edersystems.schedulechallenge.model.repositories.schedule.ScheduleRepository;
import br.com.edersystems.schedulechallenge.model.request.PaginatedRequest;
import br.com.edersystems.schedulechallenge.model.request.schedule.SchedulePostRequest;
import br.com.edersystems.schedulechallenge.model.response.PaginatedResult;
import br.com.edersystems.schedulechallenge.model.response.schedule.ScheduleTO;
import br.com.edersystems.schedulechallenge.model.services.user.UserService;
import br.com.edersystems.schedulechallenge.util.data.LocalDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private final ScheduleRepository repository;

    private final UserService userService;

    @Autowired
    private LocalDateUtil localDateUtil;

    @Autowired
    public ScheduleService(ScheduleRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public ScheduleTO createSchedule(SchedulePostRequest request) {
        User owner = userService.getUserByCpf(request.getCpf());
        System.out.println("\nScheduleService#createSchedule#Data: " + getScheduleDate(request) + "\n");
        Optional<Schedule> schedule = repository.findByOwnerAndScheduleDate(owner, getScheduleDate(request));
        if(schedule.isPresent()) {
            throw new UnProcessableEntityException("schedule.date_there_is_already_scheduling");
        }
        return buildScheduleResponse(repository.save(buildSchedule(owner, request)));
    }

    public ScheduleTO getScheduleById(Long id) {
        Schedule scheduleToReturn = repository.findById(id).orElseThrow(() -> new NotFoundException("schedule.not_found"));
        return buildScheduleResponse(scheduleToReturn);
    }

    public PaginatedResult<ScheduleTO> getSchedulesByOwner(String cpf, PaginatedRequest request) {
        User owner = userService.getUserByCpf(cpf);
        Page<Schedule> schedules = repository.findAllByOwner(owner, request);
        List<ScheduleTO> scheduleTOS = schedules
                .stream()
                .map(s -> new ScheduleTO(s.getScheduleDate().toString(), s.getDescription(), s.getId()))
                .collect(Collectors.toList());
        return new PaginatedResult<ScheduleTO>(scheduleTOS, schedules.getTotalElements(), request.getOffset(), request.getLimit());
    }

    private ScheduleTO buildScheduleResponse(Schedule schedule) {
        return new ScheduleTO(schedule.getScheduleDate().toString(), schedule.getDescription(), schedule.getId());
    }

    private Schedule buildSchedule(User owner, SchedulePostRequest request) {
        return new Schedule(owner, getScheduleType(request.getTipo()), getScheduleDate(request), request.getDescricao());
    }

    private LocalDate getScheduleDate(SchedulePostRequest request) {
        validRequest(request);
        if(localDateUtil.isDateIsLessThanTomorrow(localDateUtil.convertStringToDate(request.getData()))) {
            throw new UnProcessableEntityException("schedule.not_register");
        }
        switch(getScheduleType(request.getTipo())) {
            case CLIENT:
                return localDateUtil.getTomorrow().plusDays(BigDecimal.ONE.intValue());
            case FORUM:
                return localDateUtil.getTomorrow().plusDays(3);
            case NOTARY:
                return localDateUtil.getTomorrow().plusDays(2);
            default:
                if(localDateUtil.isDateIsLessThanTomorrow(localDateUtil.convertStringToDate(request.getData()))) {
                    throw new UnProcessableEntityException("schedule.not_register");
                }
                return localDateUtil.convertStringToDate(request.getData());
        }
    }

    private void validRequest(SchedulePostRequest request) {
        if(Objects.isNull(request.getData()) || request.getData().isBlank()) {
            throw new UnsupportedOperationException("schedule.required_date");
        }
        if(Objects.isNull(request.getDescricao()) || request.getDescricao().isBlank()) {
            throw new UnProcessableEntityException("schedule.required_description");
        }
        if(Objects.isNull(request.getTipo())) {
            throw new UnProcessableEntityException("schedule.required_type");
        }

    }

    private ScheduleType getScheduleType(String type) {
        return EnumSet.allOf(ScheduleType.class).stream()
                .filter(t -> t.name().equals(type.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("schedule.type_not_found"));
    }
}
