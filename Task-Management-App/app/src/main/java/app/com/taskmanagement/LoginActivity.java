package app.com.taskmanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import app.com.taskmanagement.model.request.LoginRequest;
import app.com.taskmanagement.model.response.LoginResponse;
import app.com.taskmanagement.util.GsonRequest;
import app.com.taskmanagement.util.SingletonRequestQueue;

public class LoginActivity extends AppCompatActivity {
    TextView errorTextView;
    EditText edtUsername, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        errorTextView = findViewById(R.id.txtError);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        SharedPreferences preference = getSharedPreferences("login", Context.MODE_PRIVATE);
        if (preference.contains("account")) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void login(String username, String password) {

        errorTextView.setVisibility(View.VISIBLE);
        errorTextView.setTextColor(getResources().getColor(R.color.black));
        errorTextView.setText("Waiting......");
        RequestQueue mRequestQueue = SingletonRequestQueue.getInstance(getApplicationContext()).getRequestQueue();
        String url = String.format(getResources().getString(R.string.BASE_URL) + "/login");

        HashMap<String, String> headers = new HashMap<>();
//        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        headers.put("connection", "keep-alive");
        headers.put("Content-Type", "application/json");
        LoginRequest loginRequest = new LoginRequest(username, password);
        Gson gson = new Gson();
        String body = gson.toJson(loginRequest);

        GsonRequest<LoginResponse> gsonRequest = new GsonRequest<>(Request.Method.POST, url, LoginResponse.class, headers, body, new Response.Listener<LoginResponse>() {
            @Override
            public void onResponse(LoginResponse response) {
                if (response.errorMessage != null) {
                    errorTextView.setText(response.errorMessage);
                    errorTextView.setTextColor(getResources().getColor(R.color.red));
                } else {
                    errorTextView.setVisibility(View.GONE);
                    SharedPreferences preference = getSharedPreferences("login", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preference.edit();
                    Gson gson = new Gson();
                    String jsonAccount = gson.toJson(response.account);
                    editor.putString("account", jsonAccount);
                    editor.commit();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Unable to get connection from server!!", Toast.LENGTH_SHORT).show();
                showDialogExit();
            }
        });

        mRequestQueue.add(gsonRequest);

    }

    public void showDialogExit() {

        new AlertDialog.Builder(this)
                .setMessage("No connection from server. Do you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                }).create().show();
    }

    public void clickToLogin(View view) {
        String username, password;
        username = edtUsername.getText().toString();
        password = edtPassword.getText().toString();
        if (username != null && password != null) {
            if (!username.trim().equals("") && !password.trim().equals("")) {
                login(username, password);
                return;
            }
        }
        errorTextView.setTextColor(getResources().getColor(R.color.red));
        errorTextView.setVisibility(View.VISIBLE);
        errorTextView.setText("Please input both username and password");
    }
}
