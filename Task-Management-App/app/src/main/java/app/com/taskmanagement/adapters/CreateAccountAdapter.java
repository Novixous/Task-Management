package app.com.taskmanagement.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import app.com.taskmanagement.FragmentGroupList;
import app.com.taskmanagement.R;
import app.com.taskmanagement.model.AccountModel;
import app.com.taskmanagement.model.Group;
import app.com.taskmanagement.model.request.AccountRequest;
import app.com.taskmanagement.model.response.GroupResponse;
import app.com.taskmanagement.util.GsonRequest;
import app.com.taskmanagement.util.SingletonRequestQueue;

public class CreateAccountAdapter extends RecyclerView.Adapter {
    public static final int MODE_MYACCOUNT = 0;
    public static final int MODE_ACCOUNTDETAIL = 1;
    public static final int MODE_CREATEACCOUNT = 2;
    private AccountModel accountModel;
    Context mContext;
    int total_types;
    private Boolean dataLoaded;
    private List<Group> groupList;
    private HashMap<Long, String> roleList;
    private List<AccountModel> listMembers;
    private int currentRole;

    public CreateAccountAdapter(Context mContext, HashMap<Long, String> roleList) {
        this.mContext = mContext;
        this.groupList = new ArrayList<>();
        this.roleList = roleList;
        this.accountModel = new AccountModel();
        dataLoaded = false;
        getGroups();
    }

    Integer[] not_show_on_create = {
            R.id.titleID,
            R.id.txtId,
            R.id.txtChangePwd,
            R.id.lineID
    };

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_create_account, parent, false);
        for (int i = 0; i < not_show_on_create.length; i++) {
            view.findViewById(not_show_on_create[i]).setVisibility(View.GONE);
        }

        return new CreateAccountHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {

//      Group
        List<String> spinnerGroupItems = new ArrayList<>();
        if (groupList != null) {
            for (Group group : groupList) {
                spinnerGroupItems.add(group.getGroupName());
            }
        }
        if (listMembers == null) {
            ArrayAdapter<String> groupAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, spinnerGroupItems);
            groupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ((CreateAccountHolder) holder).txtGroup.setAdapter(groupAdapter);
            ((CreateAccountHolder) holder).txtGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    accountModel.setGroupId(groupList.get(position).getGroupId());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        //Role
        final Collection<String> roleValue = roleList.values();
        ArrayList<String> listOfRole = new ArrayList<String>(roleValue);
        ArrayAdapter<String> roleAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, listOfRole);
        ((CreateAccountHolder) holder).txtRole.setAdapter(roleAdapter);
        ((CreateAccountHolder) holder).txtRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = ((CreateAccountHolder) holder).txtRole.getItemAtPosition(position).toString();
                BiMap<Long, String> statusBiMap = HashBiMap.create(roleList);
                currentRole = statusBiMap.inverse().get(value).intValue();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Deactivate
        ((CreateAccountHolder) holder).txtActive.setSelection(0);

        if (accountModel != null) {
            ((CreateAccountHolder) holder).btnAccountCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Fullname
                    accountModel.setFullName(((CreateAccountHolder) holder).txtFullname.getText().toString());
                    //Username
                    accountModel.setUsername(((CreateAccountHolder) holder).txtUsername.getText().toString());
                    //Password
                    accountModel.setPassword(((CreateAccountHolder) holder).edtPassword.getText().toString());
                    //Phone
                    accountModel.setPhone(((CreateAccountHolder) holder).txtPhone.getText().toString());
                    //Email
                    accountModel.setEmail(((CreateAccountHolder) holder).txtEmail.getText().toString());
                    accountModel.setRoleId(Long.valueOf(currentRole));
                    createAccount(accountModel);
                }
            });
        }
    }

    public static class CreateAccountHolder extends RecyclerView.ViewHolder {
        TextView txtFullname, txtUsername, txtEmail, txtPhone, edtPassword;
        Spinner txtRole, txtActive, txtGroup;
        Button btnAccountCreate;

        public CreateAccountHolder(@NonNull View itemView) {
            super(itemView);
            txtFullname = (EditText) itemView.findViewById(R.id.txtFullname);
            edtPassword = (EditText) itemView.findViewById(R.id.edtPassword);
            txtUsername = (EditText) itemView.findViewById(R.id.txtUsername);
            txtEmail = (EditText) itemView.findViewById(R.id.txtEmail);
            txtPhone = (EditText) itemView.findViewById(R.id.txtPhone);
            txtRole = (Spinner) itemView.findViewById(R.id.txtRole);
            txtActive = (Spinner) itemView.findViewById(R.id.txtActive);
            txtGroup = (Spinner) itemView.findViewById(R.id.txtGroup);
            btnAccountCreate = (Button) itemView.findViewById(R.id.btnAccountCreate);
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public void createAccount(AccountModel accountModel) {
        String url = String.format(mContext.getResources().getString(R.string.BASE_URL) + "/account");
        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        RequestQueue requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        Gson gson = new Gson();
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setData(accountModel);
        String body = gson.toJson(accountModel);
        GsonRequest<Integer> taskResponseCreateRequest = new GsonRequest<>(Request.Method.POST, url, Integer.class, header, body, new Response.Listener<Integer>() {
            @Override
            public void onResponse(Integer response) {
                ((AppCompatActivity) mContext).getSupportFragmentManager().popBackStack();
                ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new FragmentGroupList());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.println(Log.ERROR, "", "");
            }
        });
        requestQueue.add(taskResponseCreateRequest);
    }

    public void getGroups() {
        String url = String.format(mContext.getResources().getString(R.string.BASE_URL) + "/groups");
        HashMap<String, String> header = new HashMap<>();
        RequestQueue requestQueue = SingletonRequestQueue.getInstance(mContext).getRequestQueue();
        GsonRequest<GroupResponse> userListReponseGsonRequest = new GsonRequest<>(url, GroupResponse.class, header, new Response.Listener<GroupResponse>() {
            @Override
            public void onResponse(GroupResponse response) {
                groupList = new ArrayList<>();
                groupList.addAll(response.getData());
                dataLoaded = true;

                notifyDataSetChanged();
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
