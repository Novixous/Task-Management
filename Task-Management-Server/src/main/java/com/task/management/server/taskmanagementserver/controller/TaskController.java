package com.task.management.server.taskmanagementserver.controller;

import com.task.management.server.taskmanagementserver.mapper.TaskMapper;
import com.task.management.server.taskmanagementserver.model.Task;
import com.task.management.server.taskmanagementserver.model.request.TaskRequest;
import com.task.management.server.taskmanagementserver.model.response.TaskResponse;
import com.task.management.server.taskmanagementserver.util.TimeUtil;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
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
    public HashMap<String, List<TaskResponse>> getTaskListByAssigneeId(@RequestParam("fieldName") String fieldName, @RequestParam("value") Long value,
                                                                       @RequestParam(name = "split2", required = false) String split2, @RequestParam(name = "fieldName2", required = false) String fieldName2, @RequestParam(name = "value2", required = false) Long value2,
                                                                       @RequestParam(name = "split3", required = false) String split3, @RequestParam(name = "fieldName3", required = false) String fieldName3, @RequestParam(name = "value3", required = false) Long value3,
                                                                       @RequestParam(name = "split4", required = false) String split4, @RequestParam(name = "fieldName4", required = false) String fieldName4, @RequestParam(name = "value4", required = false) Long value4,
                                                                       @RequestParam(name = "split5", required = false) String split5, @RequestParam(name = "fieldName5", required = false) String fieldName5, @RequestParam(name = "value5", required = false) Long value5,
                                                                       @RequestParam(name = "splitClosed", required = false) String splitClosed, @RequestParam(name = "isClosed", required = false) Boolean isClosed) {
        HashMap<String, List<TaskResponse>> result = new HashMap<>();
        List<TaskResponse> tasks = TimeUtil.convertTaskToTaskResponse(taskMapper.getTasksByIdField(fieldName, value,
                split2, fieldName2, value2,
                split3, fieldName3, value3,
                split4, fieldName4, value4,
                split5, fieldName5, value5,
                splitClosed, isClosed));
        result.put("data", tasks);
        return result;
    }

    @PostMapping
    public int createTask(@RequestBody TaskRequest taskRequest) {
        List<TaskResponse> taskResponses = taskRequest.getData();
        List<Task> converted = TimeUtil.convertTaskResponseToTask(taskResponses);
        Task result = converted.get(0);
        taskMapper.createTask(result);
        return result.getTaskId().intValue();

    }

    @PutMapping
    public int updateTask(@RequestBody TaskRequest taskRequest) {
        List<TaskResponse> taskResponses = taskRequest.getData();
        List<Task> converted = TimeUtil.convertTaskResponseToTask(taskResponses);
        return taskMapper.updateTask(converted.get(0));
    }

    @GetMapping("/{taskId}")
    public TaskResponse getTaskById(@PathVariable("taskId") Long taskId) {
        List<Task> tasks = new ArrayList<>();
        tasks.add(taskMapper.getTaskByTaskId(taskId));
        return TimeUtil.convertTaskToTaskResponse(tasks).get(0);
    }

}
