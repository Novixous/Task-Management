package app.com.taskmanagement.model;

import java.time.Instant;

public class Task {
    private Long taskId;
    private Long oldTaskId;
    private String taskName;
    private Instant createdTime;
    private Instant deadline;
    private Long accountCreated;
    private Long assignee;
    private String description;
    private String resolution;
    private String imgResolutionUrl;
    private String result;
    private Instant startTime;
    private Instant endTime;
    private String managerComment;
    private Long mark;
    private Long reviewerId;
    private Instant reviewTime;
    private Long confirmId;
    private Long approvedId;
    private Long status;
    private Long groupId;
    private Long editedBy;
    private Instant editedAt;

    public Task() {
    }

    public Task(Long taskId, Long oldTaskId, String taskName, Instant createdTime, Instant deadline, Long accountCreated, Long assignee, String description, String resolution, String imgResolutionUrl, String result, Instant startTime, Instant endTime, String managerComment, Long mark, Long reviewerId, Instant reviewTime, Long confirmId, Long approvedId, Long status, Long groupId, Long editedBy, Instant editedAt) {
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

    public Instant getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public Instant getDeadline() {
        return deadline;
    }

    public void setDeadline(Instant deadline) {
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

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
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

    public Instant getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Instant reviewTime) {
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

    public Instant getEditedAt() {
        return editedAt;
    }

    public void setEditedAt(Instant editedAt) {
        this.editedAt = editedAt;
    }
}
