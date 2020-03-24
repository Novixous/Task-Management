package app.com.taskmanagement;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.com.taskmanagement.model.AccountModel;
import app.com.taskmanagement.model.GroupModel;
import app.com.taskmanagement.model.response.GroupResponse;
import app.com.taskmanagement.util.GsonRequest;
import app.com.taskmanagement.util.PreferenceUtil;
import app.com.taskmanagement.util.SingletonRequestQueue;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailAccountFragment extends Fragment {
    private AccountModel accountModel;
    private EditText edtId, edtFullname, edtUsername, edtEmail, edtPhone;
    private Spinner spinnerActive, spinnerGroup, spinnerRole;
    private List<GroupModel> groupModelList;


    private HashMap<Long, String> roleList = new HashMap<>();

    public DetailAccountFragment(AccountModel accountModel, HashMap<Long, String> roleList) {
        this.accountModel = accountModel;
        this.roleList =roleList;
    }


    public DetailAccountFragment(HashMap<Long, String> roleList) {
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
        edtFullname = rootView.findViewById(R.id.edtFullname);
        edtFullname.setText(accountModel.getFullName());
        edtUsername = rootView.findViewById(R.id.edtUsername);
        edtUsername.setText(accountModel.getUsername());
        edtEmail = rootView.findViewById(R.id.edtEmail);
        edtEmail.setText(accountModel.getEmail());
        edtPhone = rootView.findViewById(R.id.edtPhone);
        edtPhone.setText(accountModel.getPhone());

        spinnerActive = rootView.findViewById(R.id.spinnerActive);
        spinnerActive.setTag(accountModel.isDeactivated());

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
