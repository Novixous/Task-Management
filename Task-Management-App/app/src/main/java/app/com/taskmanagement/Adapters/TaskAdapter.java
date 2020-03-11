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
        TextView showTaskName,
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

    public static class ShowCardTask extends RecyclerView.ViewHolder {
        TextView showTaskName, showAssignee, showStatus, showDateline;

        public ShowCardTask(@NonNull View itemView) {
            super(itemView);
            this.showTaskName = (TextView) itemView.findViewById(R.id.showTaskName);
            this.showAssignee = (TextView) itemView.findViewById(R.id.showAssignee);
            this.showStatus = (TextView) itemView.findViewById(R.id.showStatus);
            this.showDateline = (TextView) itemView.findViewById(R.id.showDeadline);
        }
    }

    @Override
    public int getItemViewType(int position) {

        switch (dataSet.get(position).type) {
            case 0:
                return TaskModel.SHOW_FORM_CREATE;
            case 1:
                return TaskModel.SHOW_CARD_TASK;
        }
        return 0;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        Integer[] id_not_show_create_task = {
                R.id.txtStatus,
                R.id.showStatus,
                R.id.txtNote,
                R.id.showNote,
                R.id.lineStatus,
                R.id.txtDateStart,
                R.id.showDateStart,
                R.id.txtDateEnd,
                R.id.showDateEnd,
                R.id.lineDate,
                R.id.txtImgResolution,
                R.id.btnImg,
                R.id.showImgResolution,
                R.id.linePhoto,
                R.id.txtResult,
                R.id.showResult,
                R.id.lineResult,
                R.id.txtReviewer,
                R.id.showReviewer,
                R.id.lineReviewer,
                R.id.txtConfirm,
                R.id.showConfirm,
                R.id.lineConfirm,
                R.id.txtMark,
                R.id.showMark,
                R.id.txtDateReview,
                R.id.showDateReview,
                R.id.lineMark,
                R.id.txtReview,
                R.id.showReview,
                R.id.lineReview,
                R.id.btnUpdateTask,
                R.id.btnApprove,
                R.id.btnDecline,
                R.id.showIDTask,
                R.id.txtIDTask
        };
        switch (viewType) {
            case TaskModel.SHOW_FORM_CREATE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_show_task, parent, false);
                for (int i = 0; i < id_not_show_create_task.length; i++) {
                    view.findViewById(id_not_show_create_task[i]).setVisibility(View.GONE);
                }
                return new CreateTaskHolder(view);
            case TaskModel.SHOW_CARD_TASK:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_show_card_task, parent, false);
                return new ShowCardTask(view);
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
                case TaskModel.SHOW_CARD_TASK:
                    ((ShowCardTask) holder).showTaskName.setText(object.getTaskName());
                    ((ShowCardTask) holder).showAssignee.setText(object.getAssignee().toString());
                    ((ShowCardTask) holder).showStatus.setText(object.getStatus().toString());
                    ((ShowCardTask) holder).showDateline.setText(object.getDeadline().toString());
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
