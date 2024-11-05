package com.sean.flowabledemo.controller;

import com.sean.flowabledemo.controller.dto.RollbackDto;
import com.sean.flowabledemo.controller.dto.TaskRepresentationDto;
import com.sean.flowabledemo.service.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping(value = "complete")
    public boolean completeTask(String taskId) {
        taskService.completeTask(taskId);
        return true;
    }

    @PostMapping(value = "assign")
    @ResponseBody
    public String assign(@RequestParam String taskId, @RequestParam String assignee) {
        taskService.assignTask(taskId, assignee);
        return "success";
    }

    @PostMapping(value = "claim")
    public boolean claimTask(String taskId, String assignee) {
        taskService.claimTask(taskId, assignee);
        return true;
    }

    @PostMapping(value = "unClaim")
    public boolean unClaimTask(String taskId) {
        taskService.unClaimTask(taskId);
        return true;
    }

    @PostMapping(value = "rollBack")
    public void rollBack(RollbackDto rollbackDto) {
        taskService.rollbackTask(rollbackDto.getCurrentTaskId(), rollbackDto.getTargetTaskId());
    }

    @GetMapping(value = "list")
    public List<TaskRepresentationDto> getTasks(@RequestParam String assignee) {
        List<Task> tasks = taskService.getTasks(assignee);
        List<TaskRepresentationDto> dtos = new ArrayList<TaskRepresentationDto>();
        for (Task task : tasks) {
            dtos.add(new TaskRepresentationDto(task.getId(), task.getName()));
        }
        return dtos;
    }
}
