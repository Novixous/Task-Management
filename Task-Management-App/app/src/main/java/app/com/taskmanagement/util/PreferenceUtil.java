package app.com.taskmanagement.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import app.com.taskmanagement.model.AccountModel;

public class PreferenceUtil {
    public static AccountModel getAccountFromSharedPreferences(Context mContext) {
        AccountModel result = new AccountModel();
        SharedPreferences preference = mContext.getSharedPreferences("login", Context.MODE_PRIVATE);
        if (preference.contains("account")) {
            String jsonAccount = preference.getString("account", null);
            Gson gson = new Gson();
            result = gson.fromJson(jsonAccount, AccountModel.class);
        }
        return result;
    }
}
