package app.com.taskmanagement.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GroupModel implements Serializable {
    @SerializedName("groupId")
    private Long groupId;
    @SerializedName("groupName")
    private String groupName;
    @SerializedName("description")
    private String description;
    @SerializedName("creator")
    private Long creator;

    public GroupModel() {
    }

    public GroupModel(Long groupId, String groupName, String description, Long creator) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.description = description;
        this.creator = creator;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }
}
