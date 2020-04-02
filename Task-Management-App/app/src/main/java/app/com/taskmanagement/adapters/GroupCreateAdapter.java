package app.com.taskmanagement.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;

import app.com.taskmanagement.GroupListFragment;
import app.com.taskmanagement.R;
import app.com.taskmanagement.model.GroupModel;
import app.com.taskmanagement.model.request.GroupRequest;
import app.com.taskmanagement.util.GsonRequest;
import app.com.taskmanagement.util.PreferenceUtil;

public class GroupCreateAdapter extends RecyclerView.Adapter {
    Context mContext;

    public GroupCreateAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public static class CreateGroupHolder extends RecyclerView.ViewHolder {
        EditText txtGroupName, txtDesGroup, txtCreator;
        Button btnCreate;

        public CreateGroupHolder(@NonNull View itemView) {
            super(itemView);
            this.txtGroupName = (EditText) itemView.findViewById(R.id.edtGroupName);
            this.txtDesGroup = (EditText) itemView.findViewById(R.id.edtGroupDescription);
            this.btnCreate = (Button) itemView.findViewById(R.id.btnCreateGroup);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_create_fragment, parent, false);
        return new CreateGroupHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        ((CreateGroupHolder) holder).btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupName = ((CreateGroupHolder) holder).txtGroupName.getText().toString();
                String groupDescription = ((CreateGroupHolder) holder).txtDesGroup.getText().toString();

                if (!groupName.isEmpty() && !groupDescription.isEmpty()) {
                    GroupModel groupModel = new GroupModel();
                    groupModel.setCreator(PreferenceUtil.getAccountFromSharedPreferences(mContext.getApplicationContext()).getAccountId());
                    groupModel.setGroupName(groupName);
                    groupModel.setDescription(groupDescription);
                    createGroup(groupModel);
                } else {
                    Toast.makeText(mContext, "Please input both group name and group description", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public void createGroup(GroupModel groupModel) {
        String url = String.format(mContext.getResources().getString(R.string.BASE_URL) + "/group");
        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        RequestQueue requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        Gson gson = new Gson();
        GroupRequest groupRequest = new GroupRequest();
        groupRequest.setData(groupModel);
        String body = gson.toJson(groupRequest);
        GsonRequest<Integer> taskResponseCreateRequest = new GsonRequest<>(Request.Method.POST, url, Integer.class, header, body, new Response.Listener<Integer>() {
            @Override
            public void onResponse(Integer response) {
                ((AppCompatActivity) mContext).getSupportFragmentManager().popBackStack();
                ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new GroupListFragment());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.println(Log.ERROR, "", "");
            }
        });
        requestQueue.add(taskResponseCreateRequest);
    }
}
