/*
Project .....................: schedule-challenge
Creation Date ...............: 23/08/2020 17:02:08
Developer....................: ederluciano
Copyright....................: 2020
Codification.................: UTF-8
...........................................................
 Éder L. Costa - © Copyright 2020 - All Rights Reserved
...........................................................
*/
package br.com.edersystems.schedulechallenge.controllers;

import br.com.edersystems.schedulechallenge.model.entities.Schedule;
import br.com.edersystems.schedulechallenge.model.entities.User;
import br.com.edersystems.schedulechallenge.model.entities.enums.ScheduleType;
import br.com.edersystems.schedulechallenge.model.repositories.schedule.ScheduleRepository;
import br.com.edersystems.schedulechallenge.model.request.schedule.SchedulePostRequest;
import br.com.edersystems.schedulechallenge.model.response.schedule.ScheduleTO;
import br.com.edersystems.schedulechallenge.model.services.schedule.ScheduleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebMvcTest(controllers = ScheduleController.class)
public class ScheduleControllerTest {

    @MockBean
    private ScheduleService service;

    @MockBean
    private ScheduleRepository repository;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void successfullScheduleCreation() throws Exception {
        SchedulePostRequest request = getRequest();
        Schedule schedule = getSchedule();
        ScheduleTO response = getScheduleResponse();

        System.out.println(mapper.writeValueAsString(request));

        when(service.createSchedule(any(SchedulePostRequest.class))).thenReturn(response);
        when(repository.save(any(Schedule.class))).thenReturn(schedule);
        mockMvc.perform(post("/agendas")
                .content(mapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void getSuccessfullScheduleById() throws Exception {
        ScheduleTO response = getScheduleResponse();
        when(service.getScheduleById(any(Long.class))).thenReturn(response);
        mockMvc.perform(get("/agendas/{id}", BigDecimal.ONE.longValue()))
                .andExpect(status().isOk());
    }

    private SchedulePostRequest getRequest() {
        SchedulePostRequest request = new SchedulePostRequest();
        request.setData("2020-08-23");
        request.setDescricao("Teste de criacao da agenda");
        return request;
    }

    private Schedule getSchedule() {
        return new Schedule(new User("name", "013.717.857-32"), ScheduleType.CLIENT, LocalDate.of(2020, 8, 23), "Teste de criação da agenda");
    }

    private ScheduleTO getScheduleResponse() {
        return new ScheduleTO(getSchedule().getScheduleDate().toString(), getSchedule().getDescription(), getSchedule().getId());
    }
}
