package app.com.taskmanagement.model.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import app.com.taskmanagement.model.AccountModel;

public class UserListReponse implements Serializable {
    @SerializedName("data")
    List<AccountModel> accountModels;

    public UserListReponse() {
    }

    public List<AccountModel> getAccountModels() {
        return accountModels;
    }

    public void setAccountModels(List<AccountModel> accountModels) {
        this.accountModels = accountModels;
    }

    public UserListReponse(List<AccountModel> accountModels) {
        this.accountModels = accountModels;
    }
}
