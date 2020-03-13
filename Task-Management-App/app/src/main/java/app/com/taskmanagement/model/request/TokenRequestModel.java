package app.com.taskmanagement.model.request;

public class TokenRequestModel {
    private Long accountId;
    private String token;

    public TokenRequestModel() {
    }

    public TokenRequestModel(Long accountId, String token) {
        this.accountId = accountId;
        this.token = token;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
