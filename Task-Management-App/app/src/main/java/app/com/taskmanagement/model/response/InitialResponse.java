package app.com.taskmanagement.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import app.com.taskmanagement.model.ApproveModel;
import app.com.taskmanagement.model.RoleModel;
import app.com.taskmanagement.model.StatusModel;

public class InitialResponse {
    @SerializedName("role")
    private List<RoleModel> roleModels;
    @SerializedName("status")
    private List<StatusModel> statusModels;
    @SerializedName("approve")
    private List<ApproveModel> approveModels;

    public InitialResponse() {
    }

    public InitialResponse(List<RoleModel> roleModels, List<StatusModel> statusModels, List<ApproveModel> approveModels) {
        this.roleModels = roleModels;
        this.statusModels = statusModels;
        this.approveModels = approveModels;
    }

    public List<RoleModel> getRoleModels() {
        return roleModels;
    }

    public void setRoleModels(List<RoleModel> roleModels) {
        this.roleModels = roleModels;
    }

    public List<StatusModel> getStatusModels() {
        return statusModels;
    }

    public void setStatusModels(List<StatusModel> statusModels) {
        this.statusModels = statusModels;
    }

    public List<ApproveModel> getApproveModels() {
        return approveModels;
    }

    public void setApproveModels(List<ApproveModel> approveModels) {
        this.approveModels = approveModels;
    }
}
