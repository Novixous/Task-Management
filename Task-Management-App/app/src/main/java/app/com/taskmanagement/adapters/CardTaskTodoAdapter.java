package app.com.taskmanagement.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.com.taskmanagement.R;
import app.com.taskmanagement.UserUpdateTaskFragment;
import app.com.taskmanagement.model.AccountModel;
import app.com.taskmanagement.model.TaskModel;
import app.com.taskmanagement.model.response.TaskList;
import app.com.taskmanagement.model.response.TaskResponse;
import app.com.taskmanagement.util.GsonRequest;
import app.com.taskmanagement.util.PreferenceUtil;
import app.com.taskmanagement.util.SingletonRequestQueue;
import app.com.taskmanagement.util.TimeUtil;

public class CardTaskTodoAdapter extends RecyclerView.Adapter {
    private ArrayList<TaskModel> dataSet;
    Context mContext;
    int total_types;
    private List<AccountModel> listMembers;
    private AccountModel currentAccount;
    Boolean dataLoaded;

    public CardTaskTodoAdapter(Context context) {
        this.dataSet = new ArrayList<>();
        this.mContext = context;
        total_types = dataSet.size();
        currentAccount = PreferenceUtil.getAccountFromSharedPreferences(mContext);
        dataLoaded = false;

        if (currentAccount.getRoleId().equals(Long.valueOf(0))) {
            getUserPendingTaskList();
        } else if (currentAccount.getRoleId().equals(Long.valueOf(1))) {

        } else {

        }
    }

    public static class ShowCardTaskHolder extends RecyclerView.ViewHolder {
        TextView valueTaskName, valueAssignee, valueStatus, valueDeadline;
        LinearLayout cardTask;

        public ShowCardTaskHolder(@NonNull View itemView) {
            super(itemView);
            this.cardTask = (LinearLayout) itemView.findViewById(R.id.card_task);
            this.valueTaskName = (TextView) itemView.findViewById(R.id.valuetxtTaskName);
            this.valueAssignee = (TextView) itemView.findViewById(R.id.valuetxtAssignee);
            this.valueStatus = (TextView) itemView.findViewById(R.id.valuetxtStatus);
            this.valueDeadline = (TextView) itemView.findViewById(R.id.valuetxtDeadline);
        }
    }

    @Override
    public int getItemViewType(int position) {

        switch (dataSet.get(position).type) {
            case 0:
                return TaskModel.SHOW_FORM_CREATE;
            case 1:
                return TaskModel.SHOW_CARD_TASK;
            case 2:
                return TaskModel.SHOW_UPDATE_TASK;
        }
        return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TaskModel.SHOW_CARD_TASK:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_show_card_task, parent, false);
                return new ShowCardTaskHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final TaskModel object = dataSet.get(position);
        if (object != null) {
            switch (object.type) {
                case TaskModel.SHOW_CARD_TASK:
                    String splitDeadline = object.getDeadline().toString();
                    ((ShowCardTaskHolder) holder).cardTask.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new UserUpdateTaskFragment(null, null, null, dataSet.get(position).getTaskId())).addToBackStack(null).commit();

                        }
                    });
                    ((ShowCardTaskHolder) holder).valueTaskName.setText(object.getTaskName());
                    ((ShowCardTaskHolder) holder).valueAssignee.setText(object.getAssignee().toString());
                    ((ShowCardTaskHolder) holder).valueStatus.setText(object.getStatus().toString());
                    ((ShowCardTaskHolder) holder).valueDeadline.setText(splitDeadline.substring(0, 19).replace("T", "\n"));
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();

    }

    public void getUserPendingTaskList() {
        RequestQueue requestQueue = SingletonRequestQueue.getInstance(mContext.getApplicationContext()).getRequestQueue();
        HashMap<String, String> headers = new HashMap<>();
        String url = mContext.getResources().getString(R.string.BASE_URL) + "/task/getTaskListByFieldId?fieldName=assignee&value=" + currentAccount.getAccountId() + "&fieldName2=approve_id&value2=" + Long.valueOf(1) + "&split3=and(&fieldName3=status_id&value3=" + Long.valueOf(0) + "&split4=or&fieldName4=status_id&value4=" + Long.valueOf(1) + "&split5=or&fieldName5=status_id&value5=" + Long.valueOf(4) + "&splitClosed=)and&isClosed=false";
        GsonRequest<TaskList> gsonRequest = new GsonRequest<>(url, TaskList.class, headers, new Response.Listener<TaskList>() {
            @Override
            public void onResponse(TaskList response) {
                List<TaskResponse> responses = new ArrayList<>();
                responses.addAll(response.getTaskList());
                switch (currentAccount.getRoleId().intValue()) {
                    case 0:
                        List<TaskModel> taskModels = TimeUtil.convertTaskResponseToTask(responses);
                        dataSet = new ArrayList<>();
                        for (TaskModel task : taskModels) {
                            task.setType(TaskModel.SHOW_CARD_TASK);
                            dataSet.add(task);
                        }
                }
                dataLoaded = true;
                notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Connection Time Out", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(gsonRequest);
    }

}
