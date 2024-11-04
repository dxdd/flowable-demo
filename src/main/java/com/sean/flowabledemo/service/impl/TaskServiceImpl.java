package com.sean.flowabledemo.service.impl;

import com.sean.flowabledemo.service.TaskService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RuntimeService;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    @Lazy
    private ProcessEngine processEngine;

    @Autowired
    private org.flowable.engine.TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void claimTask(String taskId, String userId) {
         processEngine.getTaskService().claim(taskId, userId);
    }

    public void unClaimTask(String taskId){
        processEngine.getTaskService().unclaim(taskId);
    }

    public void assignTask(String taskId, String assignee) {
        processEngine.getTaskService().setAssignee(taskId, assignee);
    }

    @Override
    public void completeTask(String taskId) {
        processEngine.getTaskService().complete(taskId);
    }

    @Override
    public List<Task> getTasks(String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }

    public void rollbackTask(String currentTaskId, String targetTaskId) {
        try {
            // Get current todoTask
            Task currentTask = taskService.createTaskQuery()
                    .taskId(currentTaskId)
                    .singleResult();
            String processInstanceId = currentTask.getProcessInstanceId();
            String currentTaskExecutionId = currentTask.getExecutionId();

            // Get historical task nodes
            List<HistoricTaskInstance> historicTaskInstanceList = historyService.createHistoricTaskInstanceQuery()
                    .processInstanceId(currentTask.getProcessInstanceId())
                    .orderByHistoricTaskInstanceStartTime()
                    .asc()
                    .list();
            Iterator<HistoricTaskInstance> it = historicTaskInstanceList.iterator();
            // Loop through the nodes and get the key of the previous node of the current node
            String targetTaskDefinitionKey = "";
            while (it.hasNext()) {
                HistoricTaskInstance hti = it.next();
                if (targetTaskId.equals(hti.getId())) {
                    targetTaskDefinitionKey = hti.getTaskDefinitionKey();
                }
            }

            //rollback to any task
            runtimeService.createChangeActivityStateBuilder()
                    .processInstanceId(processInstanceId)
                    .moveExecutionToActivityId(currentTaskExecutionId, targetTaskDefinitionKey)
                    .changeState();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
