package com.sean.flowabledemo.service;

public interface ProcessInstanceService {
    void startProcess(String assignee, String processDefinitionKey);
}
