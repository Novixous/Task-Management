package app.com.taskmanagement.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TaskList {
    @SerializedName("data")
    public List<Task> taskList;
}
