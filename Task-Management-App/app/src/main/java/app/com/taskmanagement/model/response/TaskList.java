package app.com.taskmanagement.model.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TaskList implements Serializable {
    @SerializedName("data")
    public List<TaskResponse> taskList;
}
