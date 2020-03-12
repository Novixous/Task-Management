package com.task.management.server.taskmanagementserver.controller;

import com.task.management.server.taskmanagementserver.mapper.TaskMapper;
import com.task.management.server.taskmanagementserver.model.Task;
import com.task.management.server.taskmanagementserver.model.request.TaskRequest;
import com.task.management.server.taskmanagementserver.model.response.TaskResponse;
import com.task.management.server.taskmanagementserver.util.TimeUtil;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("task")
public class TaskController {

    private final TaskMapper taskMapper;

    public TaskController(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @GetMapping("/getTaskListByFieldId")
    public HashMap<String, List<TaskResponse>> getTaskListByAssigneeId(@RequestParam("fieldName") String fieldName, @RequestParam("value") Long id) {
        HashMap<String, List<TaskResponse>> result = new HashMap<>();
        List<TaskResponse> tasks = TimeUtil.convertTaskToTaskResponse(taskMapper.getTasksByIdField(fieldName, id));
        result.put("data", tasks);
        return result;
    }

    @PostMapping
    public int createTask(@RequestBody TaskRequest taskRequest) {
        List<TaskResponse> taskResponses = taskRequest.getData();
        List<Task> converted = TimeUtil.convertTaskResponseToTask(taskResponses);
        return taskMapper.createTask(converted.get(0));
    }

    @PutMapping
    public int updateTask(@RequestBody TaskRequest taskRequest) {
        List<TaskResponse> taskResponses = taskRequest.getData();
        List<Task> converted = TimeUtil.convertTaskResponseToTask(taskResponses);
        return taskMapper.updateTask(converted.get(0));
    }
}
