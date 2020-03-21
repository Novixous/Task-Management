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
import app.com.taskmanagement.TaskDetailFragment;
import app.com.taskmanagement.model.AccountModel;
import app.com.taskmanagement.model.TaskModel;
import app.com.taskmanagement.model.response.TaskList;
import app.com.taskmanagement.model.response.TaskResponse;
import app.com.taskmanagement.util.GsonRequest;
import app.com.taskmanagement.util.PreferenceUtil;
import app.com.taskmanagement.util.SingletonRequestQueue;
import app.com.taskmanagement.util.TimeUtil;

public class CardTaskTodoAdapter extends RecyclerView.Adapter {
    public static final int ROLE_USER = 0;
    public static final int ROLE_MANAGER = 1;
    public static final int ROLE_ADMIN = 2;
    private Context mContext;
    private ArrayList<TaskModel> dataSet;

    private HashMap<Long, String> approveList;
    private HashMap<Long, String> roleList;
    private HashMap<Long, String> statusList;
    private HashMap<Long, String> assigneeMap;

    private AccountModel currentAccount;


    public CardTaskTodoAdapter(Context context, HashMap<Long, String> approveList, HashMap<Long, String> roleList, HashMap<Long, String> statusList) {
        this.approveList = approveList;
        this.roleList = roleList;
        this.statusList = statusList;
        this.assigneeMap = new HashMap<>();
        this.dataSet = new ArrayList<>();
        this.mContext = context;
        currentAccount = PreferenceUtil.getAccountFromSharedPreferences(mContext);
        getTodoTaskList(currentAccount.getRoleId());
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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_show_card_task, parent, false);
        return new ShowCardTaskHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final TaskModel object = dataSet.get(position);
        String splitDeadline = object.getDeadline().toString();
        ((ShowCardTaskHolder) holder).cardTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame,
                                new TaskDetailFragment
                                        (approveList,
                                                roleList,
                                                statusList,
                                                dataSet.get(position).getTaskId(),
                                                TaskDetailFragment.MODE_TODO)).addToBackStack(null).commit();

            }
        });
        ((ShowCardTaskHolder) holder).valueTaskName.setText(object.getTaskName());
        ((ShowCardTaskHolder) holder).valueAssignee.setText(assigneeMap.get(object.getAssignee()));
        ((ShowCardTaskHolder) holder).valueStatus.setText(statusList.get(object.getStatus()));
        ((ShowCardTaskHolder) holder).valueDeadline.setText(splitDeadline.substring(0, 19).replace("T", "\n"));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();

    }

    public void getTodoTaskList(Long roleId) {
        RequestQueue requestQueue = SingletonRequestQueue.getInstance(mContext.getApplicationContext()).getRequestQueue();
        HashMap<String, String> headers = new HashMap<>();
        String url = "";
        switch (roleId.intValue()) {
            case 0:
                url = mContext.getResources().getString(R.string.BASE_URL) + "/task/getTaskListByFieldId?fieldName=assignee&value=" + currentAccount.getAccountId() + "&fieldName2=approve_id&value2=" + Long.valueOf(1) + "&split3=and(&fieldName3=status_id&value3=" + Long.valueOf(0) + "&split4=or&fieldName4=status_id&value4=" + Long.valueOf(1) + "&split5=or&fieldName5=status_id=" + Long.valueOf(4) + "&splitClosed=)and&isClosed=false";
                break;
            case 1:
                url = mContext.getResources().getString(R.string.BASE_URL) + "/task/getTaskListByFieldId?fieldName=group_id&value=" + currentAccount.getGroupId() + "&fieldName2=approve_id&value2=" + Long.valueOf(1) + "&split3=and(&fieldName3=status_id&value3=" + Long.valueOf(0) + "&split4=or&fieldName4=status_id&value4=" + Long.valueOf(1) + "&split5=or&fieldName5=status_id=" + Long.valueOf(4) + "&splitClosed=)and&isClosed=false";
                break;
            case 2:
                url = mContext.getResources().getString(R.string.BASE_URL) + "/task/getTaskListByFieldId?fieldName=approve_id&value=" + Long.valueOf(1) + "&split2=and(&fieldName2=status_id&value2=" + Long.valueOf(0) + "&split3=or&fieldName3=status_id&value3=" + Long.valueOf(1) + "&split4=or&fieldName4=status_id&value4=" + Long.valueOf(4) + "&splitClosed=)and&isClosed=false";
                break;
        }

        GsonRequest<TaskList> gsonRequest = new GsonRequest<>(url, TaskList.class, headers, new Response.Listener<TaskList>() {
            @Override
            public void onResponse(TaskList response) {
                List<TaskResponse> responses = new ArrayList<>();
                responses.addAll(response.getTaskList());
                List<TaskModel> taskModels = TimeUtil.convertTaskResponseToTask(responses);
                dataSet = new ArrayList<>();
                for (TaskModel task : taskModels) {
                    task.setType(TaskModel.SHOW_CARD_TASK);
                    dataSet.add(task);
                }
                assigneeMap.putAll(response.getAssigneeList());
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
