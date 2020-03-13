package app.com.taskmanagement.model.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import app.com.taskmanagement.model.TaskModel;

public class TaskList implements Serializable {
    @SerializedName("data")
    public List<TaskResponse> taskList;
}
