package app.com.taskmanagement.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;




public class AccountModel implements Serializable {
    @SerializedName("accountId")
    private Long accountId;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("firstName")
    private String firstName;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("fullName")
    private String fullName;
    @SerializedName("phone")
    private String phone;
    @SerializedName("email")
    private String email;
    @SerializedName("address")
    private String address;
    @SerializedName("deactivated")
    private boolean deactivated;
    @SerializedName("roleId")
    private Long roleId;
    @SerializedName("groupId")
    private Long groupId;

    public AccountModel() {
    }


    public AccountModel(Long accountId, String fullName, Long roleId, Long groupId) {
        this.accountId = accountId;
        this.fullName = fullName;
        this.roleId = roleId;
        this.groupId = groupId;
    }

    public AccountModel(Long accountId, String username, String fullName, String phone, String email, boolean deactivated, Long roleId, Long groupId) {
        this.accountId = accountId;
        this.username = username;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.deactivated = deactivated;
        this.roleId = roleId;
        this.groupId = groupId;
    }

    public String getFirstname() {
        return firstName;
    }

    public void setFirstname(String firstname) {
        this.firstName = firstname;
    }

    public String getLastname() {
        return lastName;
    }

    public void setLastname(String lastname) {
        this.lastName = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isDeactivated() {
        return deactivated;
    }

    public void setDeactivated(boolean deactivated) {
        this.deactivated = deactivated;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
