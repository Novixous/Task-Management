package app.com.taskmanagement;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import app.com.taskmanagement.model.AccountModel;
import app.com.taskmanagement.model.GroupModel;
import app.com.taskmanagement.model.response.GroupResponse;
import app.com.taskmanagement.util.GsonRequest;
import app.com.taskmanagement.util.PreferenceUtil;
import app.com.taskmanagement.util.SingletonRequestQueue;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountDetailFragment extends Fragment {
    private AccountModel accountModel;
    private EditText edtId, edtFullname, edtUsername, edtEmail, edtPhone;
    private Spinner spinnerActive, spinnerGroup, spinnerRole;
    private List<GroupModel> groupModelList;
    private QRGEncoder qrgEncoder;
    Bitmap qrBitmap;


    private HashMap<Long, String> roleList = new HashMap<>();

    public AccountDetailFragment(AccountModel accountModel, HashMap<Long, String> roleList) {
        this.accountModel = accountModel;
        this.roleList =roleList;
    }


    public AccountDetailFragment(HashMap<Long, String> roleList) {
        this.roleList = roleList;

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.detail_account_fragment, container, false);
        if (accountModel == null) {
            this.accountModel = PreferenceUtil.getAccountFromSharedPreferences(getActivity().getApplicationContext());
        }
        edtId = rootView.findViewById(R.id.edtId);
        edtId.setText(accountModel.getAccountId().toString());
        edtId.setEnabled(false);

        edtFullname = rootView.findViewById(R.id.edtFullname);
        edtFullname.setText(accountModel.getFullName());
        edtFullname.setEnabled(false);

        edtUsername = rootView.findViewById(R.id.edtUsername);
        edtUsername.setText(accountModel.getUsername());
        edtUsername.setEnabled(false);

        edtEmail = rootView.findViewById(R.id.edtEmail);
        edtEmail.setText(accountModel.getEmail());
        edtEmail.setEnabled(false);

        edtPhone = rootView.findViewById(R.id.edtPhone);
        edtPhone.setText(accountModel.getPhone());
        edtPhone.setEnabled(false);

        spinnerActive = rootView.findViewById(R.id.spinnerActive);
        spinnerActive.setTag(accountModel.isDeactivated());
        spinnerActive.setEnabled(false);

        spinnerGroup = rootView.findViewById(R.id.spinnerGroup);
        getGroups();

        spinnerRole = rootView.findViewById(R.id.spinnerRole);
        ArrayList<String> tempRoleList = new ArrayList<>();
        tempRoleList.add(roleList.get(accountModel.getRoleId()));
        ArrayAdapter<String> roleAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, tempRoleList);
        spinnerRole.setAdapter(roleAdapter);
        spinnerRole.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        try {
            WindowManager manager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int smallerDimension = width < height ? width : height;
            smallerDimension = smallerDimension * 3 / 4;
            qrgEncoder = new QRGEncoder(accountModel.getAccountId().toString(), null, QRGContents.Type.TEXT, smallerDimension);
            // Getting QR-Code as Bitmap
            qrBitmap = qrgEncoder.encodeAsBitmap();
            // Setting Bitmap to ImageView
            ImageView qrImageView = rootView.findViewById(R.id.nav_image_view);
            qrImageView.setImageBitmap(qrBitmap);
        } catch (Exception e) {
            Log.v("Account Detail Fragment", e.toString());
        }

        return rootView;
    }

    public void getGroups() {
        String url = String.format(getActivity().getResources().getString(R.string.BASE_URL) + "/groups");
        HashMap<String, String> header = new HashMap<>();
        RequestQueue requestQueue = SingletonRequestQueue.getInstance(getActivity().getApplicationContext()).getRequestQueue();
        GsonRequest<GroupResponse> userListReponseGsonRequest = new GsonRequest<>(url, GroupResponse.class, header, new Response.Listener<GroupResponse>() {
            @Override
            public void onResponse(GroupResponse response) {
                groupModelList = new ArrayList<>();
                groupModelList.addAll(response.getData());
                for (GroupModel group : groupModelList) {
                    if (group.getGroupId().equals(accountModel.getGroupId())) {
                        ArrayList<String> tempGroupList = new ArrayList<>();
                        tempGroupList.add(group.getGroupName());
                        ArrayAdapter<String> groupAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, tempGroupList);
                        spinnerGroup.setAdapter(groupAdapter);
                        spinnerGroup.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.println(Log.ERROR, "", "");
            }
        });
        requestQueue.add(userListReponseGsonRequest);
    }
}
