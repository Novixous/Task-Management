//package com.task.management.server.taskmanagementserver.scheduler;
//
//import com.task.management.server.taskmanagementserver.dto.NotificationRequestDto;
//import com.task.management.server.taskmanagementserver.dto.TaskToBeNotifiedDTO;
//import com.task.management.server.taskmanagementserver.mapper.AccountMapper;
//import com.task.management.server.taskmanagementserver.mapper.TaskMapper;
//import com.task.management.server.taskmanagementserver.service.NotificationService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.logging.SimpleFormatter;
//
//@Component
//public class NotificationScheduledTasks {
//    private static final Logger logger = LoggerFactory.getLogger(NotificationScheduledTasks.class);
//    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM:HH:mm:ss");
//    final TaskMapper taskMapper;
//    final AccountMapper accountMapper;
//    final NotificationService notificationService;
//
//
//    public NotificationScheduledTasks(TaskMapper taskMapper, AccountMapper accountMapper, NotificationService notificationService) {
//        this.taskMapper = taskMapper;
//        this.accountMapper = accountMapper;
//        this.notificationService = notificationService;
//    }
//
//    @Scheduled(initialDelay = 600000, fixedRate = 10000)
//    public void scheduleTaskWithFixedRate() throws InterruptedException {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy:hh:mm");
//        List<String> tokens = null;
//        String title = "";
//        String content = "";
//        NotificationRequestDto notification = null;
//        logger.info("Fixed Rate Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
//        List<TaskToBeNotifiedDTO> taskToBeNotifiedList = taskMapper.getTaskToBeNotifed();
//        for (TaskToBeNotifiedDTO task : taskToBeNotifiedList) {
//            String deadline = simpleDateFormat.format(task.getDeadline());
//            if (task.getAssignee().equals(task.getAccountCreated())) {
//                if (!accountMapper.getRoleIdByAccountId(task.getAssignee()).equals(Long.valueOf(0))) {
//                    title = "Task Due date!!!";
//                    content = "Task with name: " + task.getTaskName() + " under your management has due time at " + deadline;
//                    tokens = accountMapper.getTokensByAccountId(task.getAccountCreated());
//                    notification = new NotificationRequestDto();
//                    notification.setTitle(title);
//                    notification.setBody(content);
//                    notification.setTaskToBeNotifiedDTO(task);
//                    notificationService.sendsPnsToDevices(notification, tokens);
//                } else {
//                    //Send to user.
//                    title = "Task Due date!!!";
//                    content = "Task with name: " + task.getTaskName() + "created by you has due time at " + deadline;
//                    tokens = accountMapper.getTokensByAccountId(task.getAssignee());
//                    notification = new NotificationRequestDto();
//                    notification.setTitle(title);
//                    notification.setBody(content);
//                    notification.setTaskToBeNotifiedDTO(task);
//                    notificationService.sendsPnsToDevices(notification, tokens);
//
//                    //Send to manager.
//                    title = "Task Due date!!!";
//                    content = "Task with name: " + task.getTaskName() + " under your management due time at " + deadline;
//                    tokens = accountMapper.getTokensByAccountId(accountMapper.getManagerIdOfGroupByGroupId(task.getGroupId()));
//                    notification = new NotificationRequestDto();
//                    notification.setTitle(title);
//                    notification.setBody(content);
//                    notification.setTaskToBeNotifiedDTO(task);
//                    notificationService.sendsPnsToDevices(notification, tokens);
//                }
//            } else {
//                //Send to user.
//                title = "Task Due date!!!";
//                content = "Task with name: " + task.getTaskName() + " was assigned to you has due time at " + deadline;
//                tokens = accountMapper.getTokensByAccountId(task.getAssignee());
//                notification = new NotificationRequestDto();
//                notification.setTitle(title);
//                notification.setBody(content);
//                notification.setTaskToBeNotifiedDTO(task);
//                notificationService.sendsPnsToDevices(notification, tokens);
//
//                //Send to manager.
//                title = "Task Due date!!!";
//                content = "Task with name: " + task.getTaskName() + " created by you has due time at " + deadline;
//                tokens = accountMapper.getTokensByAccountId(task.getAccountCreated());
//                notification = new NotificationRequestDto();
//                notification.setTitle(title);
//                notification.setBody(content);
//                notification.setTaskToBeNotifiedDTO(task);
//                notificationService.sendsPnsToDevices(notification, tokens);
//            }
//            taskMapper.setTaskAsNotified(task.getTaskId());
//
//        }
//        logger.info("Size: " + taskToBeNotifiedList.size());
//        logger.info("Done", dateTimeFormatter.format(LocalDateTime.now()));
//
//    }
//}