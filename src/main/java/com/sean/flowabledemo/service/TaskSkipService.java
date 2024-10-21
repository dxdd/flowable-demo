package com.sean.flowabledemo.service;

import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;

@Service
public class TaskSkipService {
    public boolean checkSkip(DelegateExecution execution) {
        return execution.getProcessInstanceBusinessKey().equals("");
    }
}
