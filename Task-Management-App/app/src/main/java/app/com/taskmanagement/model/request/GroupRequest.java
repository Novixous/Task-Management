package app.com.taskmanagement.model.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import app.com.taskmanagement.model.Group;

public class GroupRequest implements Serializable {
    @SerializedName("data")
    Group data;

    public GroupRequest(Group data) {
        this.data = data;
    }

    public GroupRequest() {
    }

    public Group getData() {
        return data;
    }

    public void setData(Group data) {
        this.data = data;
    }
}
