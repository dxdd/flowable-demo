package com.sean.flowabledemo.service;

import com.sean.flowabledemo.domain.Person;
import com.sean.flowabledemo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MockUsersService {

    @Autowired
    private PersonRepository personRepository;

    public void createDemoUsers() {
        if (personRepository.findAll().size() == 0) {
            personRepository.save(new Person("jbarrez", "Joram", "Barrez", new Date()));
            personRepository.save(new Person("trademakers", "Tijs", "Rademakers", new Date()));
        }
    }
}
