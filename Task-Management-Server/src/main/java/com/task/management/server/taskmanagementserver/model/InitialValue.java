package com.task.management.server.taskmanagementserver.model;

public class InitialValue {
    private int id;
    private String name;

    public InitialValue(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public InitialValue() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
