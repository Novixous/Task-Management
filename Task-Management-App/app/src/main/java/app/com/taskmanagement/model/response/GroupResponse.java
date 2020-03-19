package app.com.taskmanagement.model.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import app.com.taskmanagement.model.Group;

public class GroupResponse implements Serializable {
    @SerializedName("data")
    List<Group> data;

    public GroupResponse(List<Group> data) {
        this.data = data;
    }

    public GroupResponse() {
    }

    public List<Group> getData() {
        return data;
    }

    public void setData(List<Group> data) {
        this.data = data;
    }
}
