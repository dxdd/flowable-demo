package com.sean.flowabledemo.service;

import org.flowable.task.api.Task;

import java.util.List;

public interface TaskService {

    void claimTask(String taskId, String userId);

    void unClaimTask(String taskId);

    void rollbackTask(String currentTaskId, String targetTaskId);

    void assignTask(String taskId, String assignee);

    void completeTask(String taskId);

    List<Task> getTasks(String assignee);
}
