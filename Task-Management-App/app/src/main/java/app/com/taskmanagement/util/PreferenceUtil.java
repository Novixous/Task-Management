package app.com.taskmanagement.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import app.com.taskmanagement.MainActivity;
import app.com.taskmanagement.model.AccountModel;

public class PreferenceUtil {

    public static AccountModel getAccountFromPreference(Context mContext) {
        AccountModel account = null;
        SharedPreferences preference = ((AppCompatActivity) mContext).getSharedPreferences("login", Context.MODE_PRIVATE);
        if (preference.contains("account")) {
            String jsonAccount = preference.getString("account", null);
            Gson gson = new Gson();
            account = gson.fromJson(jsonAccount, AccountModel.class);
        }
        return account;
    }
}
