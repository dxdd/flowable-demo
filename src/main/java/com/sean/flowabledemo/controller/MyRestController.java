package com.sean.flowabledemo.controller;

import com.sean.flowabledemo.dto.RollbackDto;
import com.sean.flowabledemo.service.MyService;
import jakarta.annotation.Resource;
import org.flowable.engine.ProcessEngine;
import org.flowable.task.api.Task;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MyRestController {

    @Resource
    private MyService myService;

    @Resource
    private ProcessEngine processEngine;

    @RequestMapping(value = "/tasks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskRepresentation> getTasks(@RequestParam String assignee) {
        List<Task> tasks = myService.getTasks(assignee);
        List<TaskRepresentation> dtos = new ArrayList<TaskRepresentation>();
        for (Task task : tasks) {
            dtos.add(new TaskRepresentation(task.getId(), task.getName()));
        }
        return dtos;
    }

    @PostMapping(value = "/process")
    public void startProcessInstance(@RequestBody StartProcessRepresentation startProcessRepresentation) {
        myService.startProcess(startProcessRepresentation.getAssignee(), startProcessRepresentation.getProcessDefinitionKey());
    }

    @RequestMapping(value = "/completeTask", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void completeTask(@RequestParam String taskId) {
        processEngine.getTaskService().complete(taskId);
    }

    @RequestMapping(value = "assignTask", method = RequestMethod.GET)
    @ResponseBody
    public String assign(@RequestParam String taskId, @RequestParam String assignee) {
        processEngine.getTaskService().setAssignee(taskId, assignee);
        return "success";
    }

    @PostMapping(value = "/rollBack")
    public void rollBack(RollbackDto rollbackDto) {
        myService.rollbackTask(rollbackDto.getCurrentTaskId(), rollbackDto.getTargetTaskId());
    }

//    @RequestMapping
//    @ResponseBody
//    public void rollBackTask(String taskId){
//        processEngine.getTaskService()
//    }

    static class TaskRepresentation {

        private String id;
        private String name;

        public TaskRepresentation(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    static class StartProcessRepresentation {

        private String assignee;
        private String processDefinitionKey;

        public String getProcessDefinitionKey() {
            return processDefinitionKey;
        }

        public void setProcessDefinitionKey(String processDefinitionKey) {
            this.processDefinitionKey = processDefinitionKey;
        }

        public String getAssignee() {
            return assignee;
        }

        public void setAssignee(String assignee) {
            this.assignee = assignee;
        }
    }

}
