package com.sean.flowabledemo.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sean.flowabledemo.domain.Person;
import com.sean.flowabledemo.dto.RollbackDto;
import com.sean.flowabledemo.repository.PersonRepository;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class MyService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private HistoryService historyService;

    public void startProcess(String assignee, String processDefinitionKey) {
        Person person = personRepository.findByUsername(assignee);
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("person", person);
        runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
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



    public List<Task> getTasks(String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }

    public void createDemoUsers() {
        if (personRepository.findAll().size() == 0) {
            personRepository.save(new Person("jbarrez", "Joram", "Barrez", new Date()));
            personRepository.save(new Person("trademakers", "Tijs", "Rademakers", new Date()));
        }
    }

}
