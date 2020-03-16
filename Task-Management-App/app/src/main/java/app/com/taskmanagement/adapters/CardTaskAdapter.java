package app.com.taskmanagement.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.com.taskmanagement.R;
import app.com.taskmanagement.UserUpdateTaskFragment;
import app.com.taskmanagement.model.AccountModel;
import app.com.taskmanagement.model.TaskModel;
import app.com.taskmanagement.util.PreferenceUtil;

public class CardTaskAdapter extends RecyclerView.Adapter {
    private ArrayList<TaskModel> dataSet;
    Context mContext;
    int total_types;
    TaskModel taskModel;
    private List<AccountModel> listMembers;
    private AccountModel currentAccount;
    Boolean dataLoaded;

    public CardTaskAdapter(ArrayList<TaskModel> data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();
        this.taskModel = new TaskModel();
        currentAccount = PreferenceUtil.getAccountFromSharedPreferences(mContext);
        dataLoaded = false;
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final TaskModel object = dataSet.get(position);
        if (object != null) {
            switch (object.type) {
                case TaskModel.SHOW_CARD_TASK:
                    String splitDeadline = object.getDeadline().toString();
                    ((ShowCardTaskHolder) holder).cardTask.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new UserUpdateTaskFragment()).addToBackStack(null).commit();

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
}
