package com.sean.flowabledemo.service.impl;

import com.sean.flowabledemo.domain.Person;
import com.sean.flowabledemo.repository.PersonRepository;
import com.sean.flowabledemo.service.ProcessInstanceService;
import org.flowable.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class ProcessInstanceServiceImpl implements ProcessInstanceService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private PersonRepository personRepository;

    public void startProcess(String assignee, String processDefinitionKey) {
        Person person = personRepository.findByUsername(assignee);
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("person", person);
        runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
    }

}
