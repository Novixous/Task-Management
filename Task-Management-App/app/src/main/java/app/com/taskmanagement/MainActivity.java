package app.com.taskmanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;

import app.com.taskmanagement.model.Approve;
import app.com.taskmanagement.model.Confirm;
import app.com.taskmanagement.model.Role;
import app.com.taskmanagement.model.Status;
import app.com.taskmanagement.model.response.InitialResponse;
import app.com.taskmanagement.util.DialogUtil;
import app.com.taskmanagement.util.GsonRequest;
import app.com.taskmanagement.util.SingletonRequestQueue;

public class MainActivity extends AppCompatActivity {
    Fragment currentFragment;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    Toolbar toolbar;
    private CharSequence mTitle;
    HashMap<Long, String> confirmList = new HashMap<>();
    HashMap<Long, String> approveList = new HashMap<>();
    HashMap<Long, String> roleList = new HashMap<>();
    HashMap<Long, String> statusList = new HashMap<>();
    String newToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        sendTokenToServer();

//        customRequest();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        selectItem(0, "My Task");

        navigationView = (NavigationView) findViewById(R.id.nv);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String title = item.getTitle().toString();
                int order = getOrderFromMenu(title);
                selectItem(order, title);
                return true;
            }
        });


    }

    private void selectItem(int position, String title) {

        switch (position) {
            case 0:
                currentFragment = new MyTaskFragment();
                break;
            case 1:
                currentFragment = new FragmentCreateNewTask();
                break;
            case 2:
                currentFragment = new MyAccountFragment();
                break;
            case 3:
                currentFragment = new SettingsFragment();
                break;
            case 4:


            default:
                break;
        }

        if (currentFragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, currentFragment).commit();
            drawerLayout.closeDrawers();
            setTitle(title);

        } else {
            Log.e("MainActivity", "Error in creating currentFragment");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    public int getOrderFromMenu(String title) {
        switch (title) {
            case "My Task":
                return 0;
            case "Create New Task":
                return 1;
            case "My Account":
                return 2;
            case "Settings":
                return 3;
            case "Logout":
                deleteTokenFromServer();
                return 4;
            default:
                return -1;
        }
    }

    public void clickToChangePwd(View view) {
        currentFragment = new FragmentChangePassword();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, currentFragment).addToBackStack(null).commit();
    }

    public void clickToShowDetailTask(View view) {
        currentFragment = new UserUpdateTaskFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, currentFragment).addToBackStack(null).commit();
    }


    public void clickToGetTime(View view) {
    }

    public static class FormCreateTaskFragment extends Fragment {


        public FormCreateTaskFragment() {
            // Required empty public constructor
        }


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_show_task, container, false);
        }
    }

    private void customRequest() {
        RequestQueue mRequestQueue = SingletonRequestQueue.getInstance(getApplicationContext()).getRequestQueue();
        String url = String.format(getResources().getString(R.string.BASE_URL) + "/getInitialValue");

        HashMap<String, String> headers = new HashMap<>();
//        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");

        GsonRequest<InitialResponse> gsonRequest = new GsonRequest<>(url, InitialResponse.class, headers, new Response.Listener<InitialResponse>() {
            @Override
            public void onResponse(InitialResponse response) {
                publishInitialValues(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtil.showDialogExit(MainActivity.this);
            }
        });

        mRequestQueue.add(gsonRequest);

    }

    public void publishInitialValues(InitialResponse response) {
        for (Confirm confirm : response.getConfirms()) {
            confirmList.put(confirm.getId(), confirm.getName());
        }
        for (Role role : response.getRoles()) {
            roleList.put(role.getId(), role.getName());
        }
        for (Status status : response.getStatuses()) {
            statusList.put(status.getId(), status.getName());
        }
        for (Approve approve : response.getApproves()) {
            approveList.put(approve.getId(), approve.getName());
        }
    }

    public void sendTokenToServer() {
        newToken = PreferenceUtil.getStringFromPreference(getApplicationContext(), "newToken");
        String oldToken = PreferenceUtil.getStringFromPreference(getApplicationContext(), "oldToken");
        if (newToken == null && oldToken != null) {
            newToken = oldToken;
            oldToken = null;
        }
        if (oldToken != null) {
            if (oldToken.equals(newToken)) return;
        }
        RequestQueue mRequestQueue = SingletonRequestQueue.getInstance(getApplicationContext()).getRequestQueue();
        String url = String.format(getResources().getString(R.string.BASE_URL) + "/notification/registerToken");

        HashMap<String, String> headers = new HashMap<>();
//        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        headers.put("connection", "keep-alive");
        headers.put("Content-Type", "application/json");
        TokenRequestModel tokenRequestModel = new TokenRequestModel(PreferenceUtil.getAccountFromSharedPreferences(getApplicationContext()).getAccountId(), newToken);
        Gson gson = new Gson();
        String body = gson.toJson(tokenRequestModel);

        GsonRequest<Integer> gsonRequest = new GsonRequest<>(Request.Method.POST, url, Integer.class, headers, body, new Response.Listener<Integer>() {
            @Override
            public void onResponse(Integer response) {
                PreferenceUtil.writeStringToPreference(getApplicationContext(), "oldToken", newToken);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mRequestQueue.add(gsonRequest);
    }

    public void deleteTokenFromServer() {
        RequestQueue mRequestQueue = SingletonRequestQueue.getInstance(getApplicationContext()).getRequestQueue();
        String url = String.format(getResources().getString(R.string.BASE_URL) + "/notification/deleteToken");
        if(newToken == null)newToken = PreferenceUtil.getStringFromPreference(this,"newToken");
        HashMap<String, String> headers = new HashMap<>();
//        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        headers.put("connection", "keep-alive");
        headers.put("Content-Type", "application/json");
        TokenRequestModel tokenRequestModel = new TokenRequestModel(PreferenceUtil.getAccountFromSharedPreferences(getApplicationContext()).getAccountId(), newToken);
        Gson gson = new Gson();
        String body = gson.toJson(tokenRequestModel);

        GsonRequest<Integer> gsonRequest = new GsonRequest<>(Request.Method.POST, url, Integer.class, headers, body, new Response.Listener<Integer>() {
            @Override
            public void onResponse(Integer response) {
                SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("account");
                editor.remove("newToken");
                editor.commit();
                finish();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mRequestQueue.add(gsonRequest);
    }

}
