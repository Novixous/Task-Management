package app.com.taskmanagement.model.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import app.com.taskmanagement.model.GroupModel;

public class GroupResponse implements Serializable {
    @SerializedName("data")
    List<GroupModel> data;

    public GroupResponse(List<GroupModel> data) {
        this.data = data;
    }

    public GroupResponse() {
    }

    public List<GroupModel> getData() {
        return data;
    }

    public void setData(List<GroupModel> data) {
        this.data = data;
    }
}
