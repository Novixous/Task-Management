package com.task.management.server.taskmanagementserver.dto;

import com.task.management.server.taskmanagementserver.model.Task;
import lombok.Data;

@Data
public class NotificationRequestDto {

    private String target;
    private String title;
    private String body;
    private TaskToBeNotifiedDTO taskToBeNotifiedDTO;
    private Task taskUpdateNotified;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public TaskToBeNotifiedDTO getTaskToBeNotifiedDTO() {
        return taskToBeNotifiedDTO;
    }

    public void setTaskToBeNotifiedDTO(TaskToBeNotifiedDTO taskToBeNotifiedDTO) {
        this.taskToBeNotifiedDTO = taskToBeNotifiedDTO;
    }

    public Task getTaskUpdateNotified() {
        return taskUpdateNotified;
    }

    public void setTaskUpdateNotified(Task taskUpdateNotified) {
        this.taskUpdateNotified = taskUpdateNotified;
    }
}
