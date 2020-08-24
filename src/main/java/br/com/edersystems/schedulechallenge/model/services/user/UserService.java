/*
Project .....................: schedule-challenge
Creation Date ...............: 24/08/2020 12:05:38
Developer....................: ederluciano
Copyright....................: 2020
Codification.................: UTF-8
...........................................................
 Éder L. Costa - © Copyright 2020 - All Rights Reserved
...........................................................
*/
package br.com.edersystems.schedulechallenge.model.services.user;

import br.com.edersystems.schedulechallenge.exceptions.NotFoundException;
import br.com.edersystems.schedulechallenge.model.entities.User;
import br.com.edersystems.schedulechallenge.model.repositories.user.UserRepository;
import br.com.edersystems.schedulechallenge.model.request.user.UserPostRequest;
import br.com.edersystems.schedulechallenge.model.response.schedule.ScheduleTO;
import br.com.edersystems.schedulechallenge.model.response.user.UserTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserTO getUserById(Long id) {
        return buildResponseTO(repository.findById(id).orElseThrow(() -> new NotFoundException("schedule.user_not_found")));
    }

    public User getUserByCpf(String cpf) {
        return repository.findByCpf(cpf).orElseThrow(() -> new NotFoundException("schedule.user_not_found"));
    }

    public UserTO createUser(UserPostRequest request) {
        repository.findByCpf(request.getCpf()).ifPresent(user -> {
            throw new IllegalArgumentException("schedule.user_there_is_already");
        });
        return buildResponseTO(repository.save(buildRequest(request)));
    }

    private UserTO buildResponseTO(User user) {
        return new UserTO(user.getId(), user.getCpf(), user.getName(),
                user.getSchedules().stream().map(schedule ->
                        new ScheduleTO(schedule.getScheduleDate().toString(), schedule.getDescription(), schedule.getId()))
                        .collect(Collectors.toList()));
    }

    private User buildRequest(UserPostRequest request) {
        return new User(request.getCpf(), request.getNome());
    }
}
