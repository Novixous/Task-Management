package app.com.taskmanagement.model;

public class AccountModel {
    public static final int SHOW_PROFILE = 0;
    public static final int CHANGE_PASSWORD = 1;
    public int type;
    private Long accountId;
    private String username;
    private String password;
    private String fullName;
    private String phone;
    private String email;
    private boolean deactivated;
    private Long roleId;
    private Long groupId;

    public AccountModel(int type, Long accountId, String username,
                        String fullName, String phone, String email,
                        boolean deactivated, Long roleId, Long groupId) {
        this.type = type;
        this.accountId = accountId;
        this.username = username;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.deactivated = deactivated;
        this.roleId = roleId;
        this.groupId = groupId;
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
