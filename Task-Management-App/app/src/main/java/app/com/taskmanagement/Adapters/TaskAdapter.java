package app.com.taskmanagement.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import app.com.taskmanagement.R;
import app.com.taskmanagement.model.TaskModel;

public class TaskAdapter extends RecyclerView.Adapter {
    private ArrayList<TaskModel> dataSet;
    Context mContext;
    int total_types;

    public TaskAdapter(ArrayList<TaskModel> data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();
    }

    public static class CreateTaskHolder extends RecyclerView.ViewHolder {
        TextView
                showTaskName,
                showDescription;
        Spinner showIdOld, showDateline, showTime, showAssignee;

        public CreateTaskHolder(@NonNull View itemView) {
            super(itemView);
            this.showTaskName = (TextView) itemView.findViewById(R.id.showTaskName);
            this.showIdOld = (Spinner) itemView.findViewById(R.id.showIDOldTask);
            this.showDateline = (Spinner) itemView.findViewById(R.id.showDeadline);
            this.showTime = (Spinner) itemView.findViewById(R.id.showTime);
            this.showAssignee = (Spinner) itemView.findViewById(R.id.showAssignee);
            this.showDescription = (TextView) itemView.findViewById(R.id.showDescription);
        }
    }

    @Override
    public int getItemViewType(int position) {

        switch (dataSet.get(position).type) {
            case 0:
                return TaskModel.SHOW_FORM_CREATE;
        }
        return  0;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TaskModel.SHOW_FORM_CREATE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_show_task, parent, false);
                return new CreateTaskHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final TaskModel object = dataSet.get(position);
        if (object != null) {
            switch (object.type) {
                case TaskModel.SHOW_FORM_CREATE:
                    ((CreateTaskHolder) holder).showTaskName.setText(object.getTaskName());
                    ((CreateTaskHolder) holder).showIdOld.setTag(object.getOldTaskId());
                    ((CreateTaskHolder) holder).showDateline.setTag(object.getDeadline());
                    ((CreateTaskHolder) holder).showTime.setTag(object.getTimeDeadline());
                    ((CreateTaskHolder) holder).showAssignee.setTag(object.getAssignee());
                    ((CreateTaskHolder) holder).showDescription.setText(object.getDescription());
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
