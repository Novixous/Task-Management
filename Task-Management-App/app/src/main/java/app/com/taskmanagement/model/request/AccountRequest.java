package app.com.taskmanagement.model.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import app.com.taskmanagement.model.AccountModel;

public class AccountRequest implements Serializable {
    @SerializedName("data")
    AccountModel data;

    public AccountModel getData() {
        return data;
    }

    public void setData(AccountModel data) {
        this.data = data;
    }

    public AccountRequest() {
    }

    public AccountRequest(AccountModel data) {
        this.data = data;
    }
}
