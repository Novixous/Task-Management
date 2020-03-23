package app.com.taskmanagement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.notbytes.barcode_reader.BarcodeReaderActivity;

import java.util.HashMap;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import app.com.taskmanagement.model.Approve;
import app.com.taskmanagement.model.Role;
import app.com.taskmanagement.model.Status;
import app.com.taskmanagement.model.request.TokenRequestModel;
import app.com.taskmanagement.model.response.InitialResponse;
import app.com.taskmanagement.util.DialogUtil;
import app.com.taskmanagement.util.GsonRequest;
import app.com.taskmanagement.util.PreferenceUtil;
import app.com.taskmanagement.util.SingletonRequestQueue;

public class MainActivity extends AppCompatActivity {
    public static final int BARCODE_READER_ACTIVITY_REQUEST = 1234;
    Fragment currentFragment;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    Toolbar toolbar;
    private CharSequence mTitle;
    private HashMap<Long, String> approveList = new HashMap<>();
    private HashMap<Long, String> roleList = new HashMap<>();
    private HashMap<Long, String> statusList = new HashMap<>();
    private QRGEncoder qrgEncoder;
    Bitmap qrBitmap;
    String newToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        ((ImageButton) toolbar.findViewById(R.id.tool_bar_qr_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = BarcodeReaderActivity.getLaunchIntent(MainActivity.this, true, false);
                startActivityForResult(launchIntent, BARCODE_READER_ACTIVITY_REQUEST);
            }
        });
        sendTokenToServer();

        getInitialValue();

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
        try {
            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int smallerDimension = width < height ? width : height;
            smallerDimension = smallerDimension * 3 / 4;
            qrgEncoder = new QRGEncoder(PreferenceUtil.getAccountFromSharedPreferences(this.getApplicationContext()).getAccountId().toString(), null, QRGContents.Type.TEXT, smallerDimension);
            // Getting QR-Code as Bitmap
            qrBitmap = qrgEncoder.encodeAsBitmap();
            // Setting Bitmap to ImageView
            View header = navigationView.getHeaderView(0);
            ImageView qrImageView = header.findViewById(R.id.nav_image_view);
            qrImageView.setImageBitmap(qrBitmap);
        } catch (Exception e) {
            Log.v("Main Activity", e.toString());
        }


    }

    private void selectItem(int position, String title) {

        switch (position) {
            case 0:
                currentFragment = new MyTaskFragment(approveList, roleList, statusList);
                break;
            case 1:
                currentFragment = new FragmentCreateNewTask(approveList, roleList, statusList);
                break;
            case 2:
                currentFragment = new FragmentAccountDetail(roleList);
                break;
            case 3:
                currentFragment = new SettingsFragment();
                break;
            case 4:
                deleteTokenFromServer();
                break;
            case 5:
                currentFragment = new FragmentGroupList();
                break;
            case 6:
                currentFragment = new FragmentAccountList(roleList);
                break;
            case 7:
                currentFragment = new ClosedTaskListFragment("Archived tasks", approveList, roleList, statusList);
                break;
            default:
                break;
        }

        if (currentFragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
                fragmentManager.popBackStack();
            }
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
        ((TextView) toolbar.findViewById(R.id.tool_bar_title)).setText(mTitle);
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
                return 4;
            case "Group List":
                return 5;
            case "Account List":
                return 6;
            case "Archived":
                return 7;
            default:
                return -1;
        }
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

    private void getInitialValue() {
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
        if (newToken == null) newToken = PreferenceUtil.getStringFromPreference(this, "newToken");
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(this, "error in  scanning", Toast.LENGTH_SHORT).show();
            return;
        }

        if (requestCode == BARCODE_READER_ACTIVITY_REQUEST && data != null) {
            Barcode barcode = data.getParcelableExtra(BarcodeReaderActivity.KEY_CAPTURED_BARCODE);
            Toast.makeText(this, barcode.rawValue, Toast.LENGTH_SHORT).show();
        }

    }
}
