package app.com.taskmanagement.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.Instant;

public class TaskModel implements Serializable {
    public static final int SHOW_FORM_CREATE = 0;
    public static final int SHOW_CARD_TASK = 1;
    public static final int SHOW_UPDATE_TASK = 2;
    public static final int SHOW_REVIEW_TASK = 3;
    public static final int SHOW_TASK_TO_APPROVE = 4;
    public int type;

    @SerializedName("taskId")
    private Long taskId;

    @SerializedName("oldTaskId")
    private Long oldTaskId;

    @SerializedName("taskName")
    private String taskName;

    @SerializedName("createdTime")
    private Instant createdTime;

    @SerializedName("deadline")
    private Instant deadline;

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
    private Instant startTime;

    @SerializedName("endTime")
    private Instant endTime;

    @SerializedName("managerComment")
    private String managerComment;

    @SerializedName("mark")
    private Long mark;

    @SerializedName("reviewerId")
    private Long reviewerId;

    @SerializedName("reviewTime")
    private Instant reviewTime;

    @SerializedName("approvedId")
    private Long approvedId;

    @SerializedName("status")
    private Long status;

    @SerializedName("groupId")
    private Long groupId;

    @SerializedName("editedBy")
    private Long editedBy;

    @SerializedName("editedAt")
    private Instant editedAt;

    @SerializedName("closed")
    private boolean closed;

    private String date;
    private String time;

    public TaskModel() {
    }

    //Create

    public TaskModel(int type) {
        this.type = type;
    }

    public TaskModel(int type, Long taskId, Long oldTaskId, String taskName, Instant createdTime, Instant deadline, Long accountCreated, Long assignee, String description, Long approvedId, Long status, Long groupId, Long editedBy, Instant editedAt, boolean closed) {
        this.type = type;
        this.taskId = taskId;
        this.oldTaskId = oldTaskId;
        this.taskName = taskName;
        this.createdTime = createdTime;
        this.deadline = deadline;
        this.accountCreated = accountCreated;
        this.assignee = assignee;
        this.description = description;
        this.approvedId = approvedId;
        this.status = status;
        this.groupId = groupId;
        this.editedBy = editedBy;
        this.editedAt = editedAt;
        this.closed = closed;
    }

    //Update

    //Show Card Task
    public TaskModel(int type, Long taskId, String taskName, Instant deadline, Long assignee, Long approvedId, Long status, Long groupId) {
        this.type = type;
        this.taskId = taskId;
        this.taskName = taskName;
        this.deadline = deadline;
        this.assignee = assignee;
        this.approvedId = approvedId;
        this.status = status;
        this.groupId = groupId;
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

    public String getDate() {
        return date;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public void setDate(String date) {
        this.date = date;
        if (this.time != null) {
            String temp = this.date + "T" + this.time + ":00Z";
            this.deadline = Instant.parse(temp);
        }
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
        if (this.date != null) {
            String temp = this.date + "T" + this.time + ":00Z";
            this.deadline = Instant.parse(temp);
        }
    }
}
