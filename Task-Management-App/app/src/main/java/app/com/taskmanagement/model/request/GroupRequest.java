package app.com.taskmanagement.model.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import app.com.taskmanagement.model.GroupModel;

public class GroupRequest implements Serializable {
    @SerializedName("data")
    GroupModel data;

    public GroupRequest(GroupModel data) {
        this.data = data;
    }

    public GroupRequest() {
    }

    public GroupModel getData() {
        return data;
    }

    public void setData(GroupModel data) {
        this.data = data;
    }
}
