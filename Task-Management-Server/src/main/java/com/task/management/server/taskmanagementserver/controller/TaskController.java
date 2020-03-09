package com.task.management.server.taskmanagementserver.controller;

import com.task.management.server.taskmanagementserver.mapper.TaskMapper;
import com.task.management.server.taskmanagementserver.model.Task;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("task")
public class TaskController {

    private final TaskMapper taskMapper;

    public TaskController(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @GetMapping("/getTaskListByFieldId")
    public List<Task> getTaskListByAssigneeId(@RequestParam String fieldName, @RequestParam Long id) {
        List<Task> result = null;
        taskMapper.getTasksByIdField(fieldName, id);
        return result;
    }

    @PostMapping("/task")
    public void createTask(@RequestBody Task task) {

    }
}
