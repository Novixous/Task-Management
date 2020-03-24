package com.task.management.server.taskmanagementserver.controller;

import com.task.management.server.taskmanagementserver.dto.NotificationRequestDto;
import com.task.management.server.taskmanagementserver.dto.TaskToBeNotifiedDTO;
import com.task.management.server.taskmanagementserver.mapper.AccountMapper;
import com.task.management.server.taskmanagementserver.mapper.TaskMapper;
import com.task.management.server.taskmanagementserver.model.Account;
import com.task.management.server.taskmanagementserver.model.Task;
import com.task.management.server.taskmanagementserver.model.request.TaskRequest;
import com.task.management.server.taskmanagementserver.model.response.TaskResponse;
import com.task.management.server.taskmanagementserver.scheduler.NotificationScheduledTasks;
import com.task.management.server.taskmanagementserver.service.NotificationService;
import com.task.management.server.taskmanagementserver.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("task")
public class TaskController {
    public static final int DEADLINE_NULL = -2;
    public static final int TASk_NAME_EMPTY = -3;
    public static final int RESULT_REQUIRED = -1;
    public static final int REVIEW_REQUIRED = -2;
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM:HH:mm:ss");
    private final TaskMapper taskMapper;
    private final AccountMapper accountMapper;
    private final NotificationService notificationService;

    public TaskController(TaskMapper taskMapper, AccountMapper accountMapper, NotificationService notificationService) {
        this.taskMapper = taskMapper;
        this.accountMapper = accountMapper;
        this.notificationService = notificationService;
    }

    @GetMapping("/getTaskListByFieldId")
    public HashMap<String, Object> getTaskListByAssigneeId(@RequestParam(name = "split", required = false) String split, @RequestParam(name = "fieldName", required = false) String fieldName, @RequestParam(name = "value", required = false) Long value,
                                                           @RequestParam(name = "split2", required = false) String split2, @RequestParam(name = "fieldName2", required = false) String fieldName2, @RequestParam(name = "value2", required = false) Long value2,
                                                           @RequestParam(name = "split3", required = false) String split3, @RequestParam(name = "fieldName3", required = false) String fieldName3, @RequestParam(name = "value3", required = false) Long value3,
                                                           @RequestParam(name = "split4", required = false) String split4, @RequestParam(name = "fieldName4", required = false) String fieldName4, @RequestParam(name = "value4", required = false) Long value4,
                                                           @RequestParam(name = "split5", required = false) String split5, @RequestParam(name = "fieldName5", required = false) String fieldName5, @RequestParam(name = "value5", required = false) Long value5,
                                                           @RequestParam(name = "splitClosed", required = false) String splitClosed, @RequestParam(name = "isClosed", required = false) Boolean isClosed,
                                                           @RequestParam(name = "from", required = false) String from, @RequestParam(name = "to", required = false) String to) {
        HashMap<String, Object> result = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


        List<TaskResponse> tasks = TimeUtil.convertTaskToTaskResponse(taskMapper.getTasksByIdField(split, fieldName, value,
                split2, fieldName2, value2,
                split3, fieldName3, value3,
                split4, fieldName4, value4,
                split5, fieldName5, value5,
                splitClosed, isClosed, from, to));
        HashMap<Long, String> assignee = new HashMap<>();
        for (TaskResponse task : tasks) {
            if (!assignee.containsKey(task.getAssignee())) {
                Account account = accountMapper.getAccountById(task.getAssignee());
                assignee.put(account.getAccountId(), account.getFullName());
            }
        }
        result.put("data", tasks);
        result.put("assignee", assignee);
        return result;
    }

    @PostMapping
    public int createTask(@RequestBody TaskRequest taskRequest) {
        List<TaskResponse> taskResponses = taskRequest.getData();
        List<Task> converted = TimeUtil.convertTaskResponseToTask(taskResponses);
        Task result = converted.get(0);
        if (result.getTaskName().isEmpty()) {
            return TASk_NAME_EMPTY;
        }
        try {
            taskMapper.createTask(result);
            return result.getTaskId().intValue();
        } catch (Exception e) {
            if (e.getMessage().contains("Column 'deadline' cannot be null")) {
                return DEADLINE_NULL;
            } else {
                return -1;
            }
        }

    }

    @PutMapping
    public int updateTask(@RequestBody TaskRequest taskRequest) {
        List<TaskResponse> taskResponses = taskRequest.getData();
        List<Task> converted = TimeUtil.convertTaskResponseToTask(taskResponses);
        if ((converted.get(0).getStatus().equals(Long.valueOf(2)) || converted.get(0).getStatus().equals(Long.valueOf(3))) && (converted.get(0).getResult().isEmpty() || converted.get(0).getImgResolutionUrl().isEmpty())) {
            return RESULT_REQUIRED;
        }
        if (converted.get(0).getIsClosed() != null) {
            if ((converted.get(0).getStatus().equals(Long.valueOf(4)) || converted.get(0).getIsClosed()) && converted.get(0).getManagerComment().isEmpty()) {
                return REVIEW_REQUIRED;
            }
        }
        return taskMapper.updateTask(converted.get(0));
    }

    @GetMapping("/{taskId}")
    public TaskResponse getTaskById(@PathVariable("taskId") Long taskId) {
        List<Task> tasks = new ArrayList<>();
        tasks.add(taskMapper.getTaskByTaskId(taskId));
        return TimeUtil.convertTaskToTaskResponse(tasks).get(0);
    }


}
