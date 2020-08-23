/*
Project .....................: schedule-challenge
Creation Date ...............: 22/08/2020 17:13:14
Developer....................: ederluciano
Copyright....................: 2020
Codification.................: UTF-8
...........................................................
 Éder L. Costa - © Copyright 2020 - All Rights Reserved
...........................................................
*/
package br.com.edersystems.schedulechallenge.controllers;

import br.com.edersystems.schedulechallenge.model.request.ScheduleRequestCreate;
import br.com.edersystems.schedulechallenge.model.response.ScheduleResponseTO;
import br.com.edersystems.schedulechallenge.model.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/agendas", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ScheduleController {

    private final ScheduleService service;

    @Autowired
    public ScheduleController(ScheduleService service) {
        this.service = service;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ScheduleResponseTO> createSchedule(@Valid @RequestBody ScheduleRequestCreate request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createSchedule(request));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ScheduleResponseTO> getScheduleById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getScheduleById(id));
    }
}
