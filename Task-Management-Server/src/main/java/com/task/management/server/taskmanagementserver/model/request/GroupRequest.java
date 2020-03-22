package com.task.management.server.taskmanagementserver.model.request;

import com.task.management.server.taskmanagementserver.model.Group;

import java.io.Serializable;

public class GroupRequest implements Serializable {
    private Group data;

    public GroupRequest(Group data) {
        this.data = data;
    }

    public GroupRequest() {
    }

    public Group getData() {
        return data;
    }

    public void setData(Group data) {
        this.data = data;
    }
}
