package app.com.taskmanagement.model.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TaskResponse implements Serializable {
    @SerializedName("taskId")
    private Long taskId;

    @SerializedName("oldTaskId")
    private Long oldTaskId;

    @SerializedName("taskName")
    private String taskName;

    @SerializedName("createdTime")
    private String createdTime;

    @SerializedName("deadline")
    private String deadline;

    @SerializedName("accountCreated")
    private Long accountCreated;

    @SerializedName("assignee")
    private Long assignee;

    @SerializedName("description")
    private String description;

    @SerializedName("resolution")
    private String resolution;

    @SerializedName("imgResolutionUrl")
    private String imgResolutionUrl;

    @SerializedName("result")
    private String result;

    @SerializedName("startTime")
    private String startTime;

    @SerializedName("endTime")
    private String endTime;

    @SerializedName("managerComment")
    private String managerComment;

    @SerializedName("mark")
    private Long mark;

    @SerializedName("reviewerId")
    private Long reviewerId;

    @SerializedName("reviewTime")
    private String reviewTime;

    @SerializedName("confirmId")
    private Long confirmId;

    @SerializedName("approvedId")
    private Long approvedId;

    @SerializedName("status")
    private Long status;

    @SerializedName("groupId")
    private Long groupId;

    @SerializedName("editedBy")
    private Long editedBy;

    @SerializedName("editedAt")
    private String editedAt;

    public TaskResponse() {
    }

    public TaskResponse(Long taskId, Long oldTaskId, String taskName, String createdTime, String deadline, Long accountCreated, Long assignee, String description, String resolution, String imgResolutionUrl, String result, String startTime, String endTime, String managerComment, Long mark, Long reviewerId, String reviewTime, Long confirmId, Long approvedId, Long status, Long groupId, Long editedBy, String editedAt) {
        this.taskId = taskId;
        this.oldTaskId = oldTaskId;
        this.taskName = taskName;
        this.createdTime = createdTime;
        this.deadline = deadline;
        this.accountCreated = accountCreated;
        this.assignee = assignee;
        this.description = description;
        this.resolution = resolution;
        this.imgResolutionUrl = imgResolutionUrl;
        this.result = result;
        this.startTime = startTime;
        this.endTime = endTime;
        this.managerComment = managerComment;
        this.mark = mark;
        this.reviewerId = reviewerId;
        this.reviewTime = reviewTime;
        this.confirmId = confirmId;
        this.approvedId = approvedId;
        this.status = status;
        this.groupId = groupId;
        this.editedBy = editedBy;
        this.editedAt = editedAt;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getOldTaskId() {
        return oldTaskId;
    }

    public void setOldTaskId(Long oldTaskId) {
        this.oldTaskId = oldTaskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public Long getAccountCreated() {
        return accountCreated;
    }

    public void setAccountCreated(Long accountCreated) {
        this.accountCreated = accountCreated;
    }

    public Long getAssignee() {
        return assignee;
    }

    public void setAssignee(Long assignee) {
        this.assignee = assignee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getImgResolutionUrl() {
        return imgResolutionUrl;
    }

    public void setImgResolutionUrl(String imgResolutionUrl) {
        this.imgResolutionUrl = imgResolutionUrl;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getManagerComment() {
        return managerComment;
    }

    public void setManagerComment(String managerComment) {
        this.managerComment = managerComment;
    }

    public Long getMark() {
        return mark;
    }

    public void setMark(Long mark) {
        this.mark = mark;
    }

    public Long getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(Long reviewerId) {
        this.reviewerId = reviewerId;
    }

    public String getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(String reviewTime) {
        this.reviewTime = reviewTime;
    }

    public Long getConfirmId() {
        return confirmId;
    }

    public void setConfirmId(Long confirmId) {
        this.confirmId = confirmId;
    }

    public Long getApprovedId() {
        return approvedId;
    }

    public void setApprovedId(Long approvedId) {
        this.approvedId = approvedId;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getEditedBy() {
        return editedBy;
    }

    public void setEditedBy(Long editedBy) {
        this.editedBy = editedBy;
    }

    public String getEditedAt() {
        return editedAt;
    }

    public void setEditedAt(String editedAt) {
        this.editedAt = editedAt;
    }
}
