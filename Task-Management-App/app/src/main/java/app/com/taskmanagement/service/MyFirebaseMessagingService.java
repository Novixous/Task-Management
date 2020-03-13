package app.com.taskmanagement.service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.util.HashMap;

import app.com.taskmanagement.LoginActivity;
import app.com.taskmanagement.MainActivity;
import app.com.taskmanagement.R;
import app.com.taskmanagement.model.MyWorker;
import app.com.taskmanagement.model.request.TokenRequestModel;
import app.com.taskmanagement.model.response.LoginResponse;
import app.com.taskmanagement.util.DialogUtil;
import app.com.taskmanagement.util.GsonRequest;
import app.com.taskmanagement.util.PreferenceUtil;
import app.com.taskmanagement.util.SingletonRequestQueue;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onNewToken(String token) {
        Log.d("MyFirebaseMsgService", "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }

    public void sendRegistrationToServer(String token) {

        RequestQueue mRequestQueue = SingletonRequestQueue.getInstance(getApplicationContext()).getRequestQueue();
        String url = String.format(getResources().getString(R.string.BASE_URL) + "/notification/registerToken");

        HashMap<String, String> headers = new HashMap<>();
//        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        headers.put("connection", "keep-alive");
        headers.put("Content-Type", "application/json");
        TokenRequestModel tokenRequestModel = new TokenRequestModel(PreferenceUtil.getAccountFromSharedPreferences(getApplicationContext()).getAccountId(), token);
        Gson gson = new Gson();
        String body = gson.toJson(tokenRequestModel);

        GsonRequest<Integer> gsonRequest = new GsonRequest<>(Request.Method.POST, url, Integer.class, headers, body, new Response.Listener<Integer>() {
            @Override
            public void onResponse(Integer response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mRequestQueue.add(gsonRequest);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow();
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private void scheduleJob() {
        // [START dispatch_job]
        OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(MyWorker.class)
                .build();
        WorkManager.getInstance().beginWith(work).enqueue();
        // [END dispatch_job]
    }

    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }
}
