package com.task.management.server.taskmanagementserver.model;

import java.io.Serializable;

public class Group implements Serializable {
    private Long groupId;
    private String groupName;
    private String description;
    private Long creator;

    public Group() {
    }

    public Group(Long groupId, String groupName, String description, Long creator) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.description = description;
        this.creator = creator;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }
}
