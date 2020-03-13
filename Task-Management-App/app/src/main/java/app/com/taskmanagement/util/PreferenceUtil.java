package app.com.taskmanagement.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.Set;

import app.com.taskmanagement.LoginActivity;
import app.com.taskmanagement.MainActivity;
import java.util.Set;
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

    public static void writeStringToPreference(Context mContext, String key, String value) {
        SharedPreferences preference = mContext.getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getStringFromPreference(Context mContext, String key) {
        SharedPreferences preference = mContext.getSharedPreferences("login", Context.MODE_PRIVATE);
        return preference.getString(key, null);
    }

}
