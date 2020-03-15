package com.task.management.server.taskmanagementserver.dto;

import java.time.Instant;

public class TaskToBeNotifiedDTO {
    private Long taskId;
    private Long oldTaskId;
    private String taskName;
    private Long assignee;
    private Long accountCreated;
    private Instant deadline;
    private Long groupId;

    public TaskToBeNotifiedDTO() {
    }

    public TaskToBeNotifiedDTO(Long taskId, Long oldTaskId, String taskName, Long assignee, Long accountCreated, Instant deadline, Long groupId) {
        this.taskId = taskId;
        this.oldTaskId = oldTaskId;
        this.taskName = taskName;
        this.assignee = assignee;
        this.accountCreated = accountCreated;
        this.deadline = deadline;
        this.groupId = groupId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getOldTaskId() {
        return oldTaskId;
    }

    public void setOldTaskId(Long oldTaskId) {
        this.oldTaskId = oldTaskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Long getAssignee() {
        return assignee;
    }

    public void setAssignee(Long assignee) {
        this.assignee = assignee;
    }

    public Long getAccountCreated() {
        return accountCreated;
    }

    public void setAccountCreated(Long accountCreated) {
        this.accountCreated = accountCreated;
    }

    public Instant getDeadline() {
        return deadline;
    }

    public void setDeadline(Instant deadline) {
        this.deadline = deadline;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
