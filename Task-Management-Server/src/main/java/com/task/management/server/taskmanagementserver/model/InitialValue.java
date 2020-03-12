package com.task.management.server.taskmanagementserver.model;

public class InitialValue {
    private int id;
    private String content;

    public InitialValue(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public InitialValue() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
