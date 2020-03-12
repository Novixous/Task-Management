package app.com.taskmanagement.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Approve implements Serializable {
    @SerializedName("id")
    private Long id;
    @SerializedName("content")
    private String name;

    public Approve() {
    }

    public Approve(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
