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

import app.com.taskmanagement.R;
import app.com.taskmanagement.model.GroupModel;
import app.com.taskmanagement.model.response.GroupResponse;
import app.com.taskmanagement.util.GsonRequest;
import app.com.taskmanagement.util.SingletonRequestQueue;

public class GroupCardAdapter extends RecyclerView.Adapter {
    private ArrayList<GroupModel> dataSet;
    private Fragment fragment;
    private Context mContext;
    private OnItemClicked onItemClickedListener;

    public void setOnItemClickedListener(OnItemClicked onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

    public GroupCardAdapter(Context mContext, Fragment fragment) {
        this.dataSet = new ArrayList<>();
        this.mContext = mContext;
        this.fragment = fragment;
        getGroupList();
    }

    public static class CardGroupHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtGroupName, txtDesGroup;
        OnItemClicked onItemClicked;

        public CardGroupHolder(@NonNull View itemView, OnItemClicked onItemClicked) {
            super(itemView);
            this.onItemClicked = onItemClicked;
            this.txtGroupName = (TextView) itemView.findViewById(R.id.txtGroupName);
            this.txtDesGroup = (TextView) itemView.findViewById(R.id.txtDesGroup);
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
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_group_fragment, parent, false);
        return new CardGroupHolder(view, onItemClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final GroupModel object = dataSet.get(position);
        if (object != null) {
            ((CardGroupHolder) holder).txtGroupName.setText(object.getGroupName());
            ((CardGroupHolder) holder).txtDesGroup.setText(object.getDescription());

        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public GroupModel getItem(int position) {
        return dataSet.get(position);
    }

    public interface OnItemClicked {
        void onClicked(int position);
    }

    public void getGroupList() {
        String url = String.format(mContext.getResources().getString(R.string.BASE_URL) + "/groups");
        HashMap<String, String> header = new HashMap<>();
        RequestQueue requestQueue = SingletonRequestQueue.getInstance(mContext).getRequestQueue();
        GsonRequest<GroupResponse> userListReponseGsonRequest = new GsonRequest<>(url, GroupResponse.class, header, new Response.Listener<GroupResponse>() {
            @Override
            public void onResponse(GroupResponse response) {
                dataSet = new ArrayList<>();
                dataSet.addAll(response.getData());
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
