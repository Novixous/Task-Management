package app.com.taskmanagement.model;

import java.time.Instant;

public class TaskModel {
    public static final int SHOW_FORM_CREATE = 0;
    public int type;
    private Long taskId;
    private Long oldTaskId;
    private String taskName;
    private Instant createdTime;
    private Instant deadline;
    private Instant timeDeadline;
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

    public TaskModel() {
    }
    //Create&Update New Task and Show Task
    public TaskModel(int type, Long taskId, Long oldTaskId, String taskName, Instant createdTime,
                     Instant deadline, Long accountCreated, Long assignee, String description,
                     Instant timeDeadline, Long groupId,Long editedBy,Instant editedAt) {
        this.type = type;
        this.taskId = taskId;
        this.oldTaskId = oldTaskId;
        this.taskName = taskName;
        this.createdTime = createdTime;
        this.deadline = deadline;
        this.timeDeadline = timeDeadline;
        this.accountCreated = accountCreated;
        this.assignee = assignee;
        this.description = description;
        this.groupId = groupId;
    }

    public Instant getTimeDeadline() {
        return timeDeadline;
    }

    public void setTimeDeadline(Instant timeDeadline) {
        this.timeDeadline = timeDeadline;
    }

    public static int getShowFormCreate() {
        return SHOW_FORM_CREATE;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
}
