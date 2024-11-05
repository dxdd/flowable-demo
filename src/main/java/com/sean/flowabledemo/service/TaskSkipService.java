package com.sean.flowabledemo.service;

import org.flowable.engine.delegate.DelegateExecution;

/**
 * config skip service
 */
public class TaskSkipService {
    private final static String SKIP_DEFINITION_KEY = "t-2";

    public boolean checkSkip(DelegateExecution execution) {
        return SKIP_DEFINITION_KEY.equals(execution.getProcessInstanceBusinessKey());
    }
}
