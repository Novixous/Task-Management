package com.task.management.server.taskmanagementserver.model.request;

import com.task.management.server.taskmanagementserver.model.response.TaskResponse;

import java.util.List;

public class TaskRequest {
    private List<TaskResponse> data;

    public TaskRequest() {
    }

    public TaskRequest(List<TaskResponse> data) {
        this.data = data;
    }

    public List<TaskResponse> getData() {
        return data;
    }

    public void setData(List<TaskResponse> data) {
        this.data = data;
    }
}
