package app.com.taskmanagement.model.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import app.com.taskmanagement.model.AccountModel;

public class LoginResponse implements Serializable {
    @SerializedName("data")
    public AccountModel account;
    @SerializedName("errorMessage")
    public String errorMessage;
}
