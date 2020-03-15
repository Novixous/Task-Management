package app.com.taskmanagement.model.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import app.com.taskmanagement.model.TaskModel;
import app.com.taskmanagement.model.response.TaskResponse;
import app.com.taskmanagement.util.TimeUtil;

public class TaskCreateRequest implements Serializable {
    @SerializedName("data")
    private List<TaskResponse> data;

    public TaskCreateRequest(List<TaskModel> taskModels) {
    this.data = TimeUtil.convertTaskToTaskResponse(taskModels);
    }

    public List<TaskResponse> getData() {
        return data;
    }

    public void setData(List<TaskResponse> data) {
        this.data = data;
    }
}
