package app.com.taskmanagement.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.com.taskmanagement.R;
import app.com.taskmanagement.model.AccountModel;
import app.com.taskmanagement.model.GroupModel;
import app.com.taskmanagement.model.response.GroupResponse;
import app.com.taskmanagement.model.response.UserListReponse;
import app.com.taskmanagement.util.GsonRequest;
import app.com.taskmanagement.util.SingletonRequestQueue;

public class AccountCardAdapter extends RecyclerView.Adapter {
    private ArrayList<AccountModel> dataSet;
    private Fragment fragment;
    private Context mContext;
    private OnItemClicked onItemClickedListener;
    private HashMap<Long, String> roleList;
    private HashMap<Long, GroupModel> groupHashMap;


    public AccountCardAdapter(Context mContext, Fragment fragment, HashMap<Long, String> roleList) {
        this.dataSet = new ArrayList<>();
        this.roleList = roleList;
        this.fragment = fragment;
        this.mContext = mContext;
        getAccountList();
    }

    public AccountModel getItem(int position) {
        return dataSet.get(position);
    }

    public interface OnItemClicked {
        void onClicked(int position);
    }

    public void setOnItemClickedListener(OnItemClicked onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

    public static class CardAccountHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtAccountName, txtRoleAccount, txtGroupAccount;
        OnItemClicked onItemClicked;

        public CardAccountHolder(@NonNull View itemView, OnItemClicked onItemClicked) {
            super(itemView);
            this.txtAccountName = (TextView) itemView.findViewById(R.id.txtAccountName);
            this.txtRoleAccount = (TextView) itemView.findViewById(R.id.txtRoleAccount);
            this.txtGroupAccount = (TextView) itemView.findViewById(R.id.txtGroupName);
            this.onItemClicked = onItemClicked;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClicked.onClicked(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_account_fragment, parent, false);
        return new CardAccountHolder(view, onItemClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final AccountModel object = dataSet.get(position);
        if (object != null) {
            ((CardAccountHolder) holder).txtAccountName.setText(object.getFullName());
            ((CardAccountHolder) holder).txtRoleAccount.setText(roleList.get(object.getRoleId()));
            ((CardAccountHolder) holder).txtGroupAccount.setText(object.getGroupId() != null ? groupHashMap.get(object.getGroupId()).getGroupName() : "None");
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();

    }

    public void getAccountList() {
        String url = String.format(mContext.getResources().getString(R.string.BASE_URL) + "/accounts");
        HashMap<String, String> header = new HashMap<>();
        RequestQueue requestQueue = SingletonRequestQueue.getInstance(mContext).getRequestQueue();
        GsonRequest<UserListReponse> userListReponseGsonRequest = new GsonRequest<>(url, UserListReponse.class, header, new Response.Listener<UserListReponse>() {
            @Override
            public void onResponse(UserListReponse response) {
                dataSet = new ArrayList<>();
                dataSet.addAll(response.getAccountModels());
                getGroups();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.println(Log.ERROR, "", "");
            }
        });
        requestQueue.add(userListReponseGsonRequest);
    }

    public void getGroups() {
        String url = String.format(mContext.getResources().getString(R.string.BASE_URL) + "/groups");
        HashMap<String, String> header = new HashMap<>();
        RequestQueue requestQueue = SingletonRequestQueue.getInstance(mContext).getRequestQueue();
        GsonRequest<GroupResponse> userListReponseGsonRequest = new GsonRequest<>(url, GroupResponse.class, header, new Response.Listener<GroupResponse>() {
            @Override
            public void onResponse(GroupResponse response) {
                List<GroupModel> groupList = new ArrayList<>();
                groupList.addAll(response.getData());
                groupHashMap = new HashMap<>();
                for (GroupModel group : groupList) {
                    groupHashMap.put(group.getGroupId(), group);
                }
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
