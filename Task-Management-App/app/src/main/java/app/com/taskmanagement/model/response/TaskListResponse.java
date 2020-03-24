package app.com.taskmanagement.model.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class TaskListResponse implements Serializable {
    @SerializedName("data")
    private List<TaskResponse> taskList;

    @SerializedName("assignee")
    private HashMap<Long, String> assigneeList;

    public List<TaskResponse> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<TaskResponse> taskList) {
        this.taskList = taskList;
    }

    public HashMap<Long, String> getAssigneeList() {
        return assigneeList;
    }

    public void setAssigneeList(HashMap<Long, String> assigneeList) {
        this.assigneeList = assigneeList;
    }
}
