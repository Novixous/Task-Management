package app.com.taskmanagement.model.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TaskList implements Serializable {
    @SerializedName("data")
    private List<TaskResponse> taskList;

    public List<TaskResponse> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<TaskResponse> taskList) {
        this.taskList = taskList;
    }
}
