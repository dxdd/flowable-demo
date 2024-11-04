package com.sean.flowabledemo.controller;

import com.sean.flowabledemo.controller.dto.StartProcessRepresentationDto;
import com.sean.flowabledemo.service.impl.ProcessInstanceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "processInstance")
public class ProcessInstanceController {

    @Autowired
    private ProcessInstanceServiceImpl processInstanceServiceImpl;

    @PostMapping(value = "/start")
    public void startProcessInstance(@RequestBody StartProcessRepresentationDto startProcessRepresentation) {
        processInstanceServiceImpl.startProcess(startProcessRepresentation.getAssignee(), startProcessRepresentation.getProcessDefinitionKey());
    }
}
