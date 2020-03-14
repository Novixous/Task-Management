package app.com.taskmanagement.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import app.com.taskmanagement.model.Approve;
import app.com.taskmanagement.model.Role;
import app.com.taskmanagement.model.Status;

public class InitialResponse {
    @SerializedName("role")
    private List<Role> roles;
    @SerializedName("status")
    private List<Status> statuses;
    @SerializedName("approve")
    private List<Approve> approves;

    public InitialResponse() {
    }

    public InitialResponse(List<Role> roles, List<Status> statuses, List<Approve> approves) {
        this.roles = roles;
        this.statuses = statuses;
        this.approves = approves;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }

    public List<Approve> getApproves() {
        return approves;
    }

    public void setApproves(List<Approve> approves) {
        this.approves = approves;
    }
}
