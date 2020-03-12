package app.com.taskmanagement.adapters;

import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
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
        Spinner showIdOld, showDeadline, showTime, showAssignee;

        public CreateTaskHolder(@NonNull View itemView) {
            super(itemView);
            this.showTaskName = (TextView) itemView.findViewById(R.id.showTaskName);
            this.showIdOld = (Spinner) itemView.findViewById(R.id.showIDOldTask);
            this.showDeadline = (Spinner) itemView.findViewById(R.id.showDateDeadline);
            this.showTime = (Spinner) itemView.findViewById(R.id.showTimeDeadline);
            this.showAssignee = (Spinner) itemView.findViewById(R.id.showAssignee);
            this.showDescription = (TextView) itemView.findViewById(R.id.showDescription);
        }
    }

    public static class ShowCardTaskHolder extends RecyclerView.ViewHolder {
        TextView showTaskName, showAssignee, showStatus, showDeadline;

        public ShowCardTaskHolder(@NonNull View itemView) {
            super(itemView);
            this.showTaskName = (TextView) itemView.findViewById(R.id.showtxtTaskName);
            this.showAssignee = (TextView) itemView.findViewById(R.id.showAssignee);
            this.showStatus = (TextView) itemView.findViewById(R.id.showStatus);
            this.showDeadline = (TextView) itemView.findViewById(R.id.showtxtDeadline);
        }
    }

    public static class UpdateTaskHolder extends RecyclerView.ViewHolder {
        TextView showTaskname, showIDtask, showNote, showDescription,
                showStartdate, showEnddate, showResult, showCreator, showReviewer, showReview, showDateReview;
        Spinner showOldID, showStatus, showAssignee, showDateDeadline, showTimeDeadline, showConfirm;
        ImageButton btnImg;
        ImageView showImgResolution;
        NumberPicker showMark;

        public UpdateTaskHolder(@NonNull View itemView) {
            super(itemView);
            this.showTaskname = (TextView) itemView.findViewById(R.id.showTaskName);
            this.showIDtask = (TextView) itemView.findViewById(R.id.showIDTask);
            this.showOldID = (Spinner) itemView.findViewById(R.id.showIDOldTask);
            this.showDateDeadline = (Spinner) itemView.findViewById(R.id.showDateDeadline);
            this.showTimeDeadline = (Spinner) itemView.findViewById(R.id.showTimeDeadline);
            this.showNote = (TextView) itemView.findViewById(R.id.showNote);
            this.showDescription = (TextView) itemView.findViewById(R.id.showDescription);
            this.showStartdate = (TextView) itemView.findViewById(R.id.showDateStart);
            this.showEnddate = (TextView) itemView.findViewById(R.id.showDateEnd);
            this.showResult = (TextView) itemView.findViewById(R.id.showResult);
            this.showStatus = (Spinner) itemView.findViewById(R.id.showStatus);
            this.showCreator = (TextView) itemView.findViewById(R.id.showCreator);
            this.showAssignee = (Spinner) itemView.findViewById(R.id.showAssignee);
            this.btnImg = (ImageButton) itemView.findViewById(R.id.btnImg);
            this.showImgResolution = (ImageView) itemView.findViewById(R.id.showImgResolution);
            this.showReviewer = (TextView) itemView.findViewById(R.id.showReviewer);
            this.showConfirm = (Spinner) itemView.findViewById(R.id.showConfirm);
            this.showDateReview = (TextView) itemView.findViewById(R.id.showDateReview);
            this.showMark = (NumberPicker) itemView.findViewById(R.id.showMark);
            this.showReview = (TextView) itemView.findViewById(R.id.showReview);
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
        Integer[] id_not_show_update = {
                R.id.btnCreateTask,
                R.id.btnApprove,
                R.id.btnDecline,
        };
        Integer[] create_edit_textview = {

                R.id.showDescription,
                R.id.showTaskName,
        };
        Integer[] update_edit_textview = {
                R.id.showResult
        };
        Integer[] update_review_textview = {
                R.id.showReview
        };
        switch (viewType) {
            case TaskModel.SHOW_FORM_CREATE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_show_task, parent, false);
                for (int i = 0; i < id_not_show_create_task.length; i++) {
                    view.findViewById(id_not_show_create_task[i]).setVisibility(View.GONE);
                }
                for (int i = 0; i < create_edit_textview.length; i++) {
                    final TextView temp = view.findViewById(create_edit_textview[i]);
                    temp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            temp.setCursorVisible(true);
                            temp.setFocusableInTouchMode(true);
                            temp.setInputType(InputType.TYPE_CLASS_TEXT);
                            temp.setTextIsSelectable(true);
                            temp.requestFocus();
                        }
                    });
                }
                return new CreateTaskHolder(view);
            case TaskModel.SHOW_CARD_TASK:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_show_card_task, parent, false);
                return new ShowCardTaskHolder(view);
            case TaskModel.SHOW_UPDATE_TASK:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_show_task, parent, false);
                for (int i = 0; i < id_not_show_update.length; i++) {
                    view.findViewById(id_not_show_update[i]).setVisibility(View.GONE);
                }
                for (int i = 0; i < update_edit_textview.length; i++) {
                    final TextView temp = view.findViewById(update_edit_textview[i]);
                    temp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            temp.setCursorVisible(true);
                            temp.setFocusableInTouchMode(true);
                            temp.setInputType(InputType.TYPE_CLASS_TEXT);
                            temp.setTextIsSelectable(true);
                            temp.requestFocus();
                        }
                    });
                }
                return new UpdateTaskHolder(view);
            case TaskModel.SHOW_REVIEW_TASK:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_show_task, parent, false);
                for (int i = 0; i < id_not_show_update.length; i++) {
                    view.findViewById(id_not_show_update[i]).setVisibility(View.GONE);
                }
                for (int i = 0; i < update_review_textview.length; i++) {
                    final TextView temp = view.findViewById(update_review_textview[i]);
                    temp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            temp.setCursorVisible(true);
                            temp.setFocusableInTouchMode(true);
                            temp.setInputType(InputType.TYPE_CLASS_TEXT);
                            temp.setTextIsSelectable(true);
                            temp.requestFocus();
                        }
                    });
                }
                return new UpdateTaskHolder(view);
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
                    ((CreateTaskHolder) holder).showDeadline.setTag(object.getDeadline());
                    ((CreateTaskHolder) holder).showTime.setTag(object.getTimeDeadline());
                    ((CreateTaskHolder) holder).showAssignee.setTag(object.getAssignee());
                    ((CreateTaskHolder) holder).showDescription.setText(object.getDescription());
                    break;
                case TaskModel.SHOW_CARD_TASK:
                    String splitDeadline = object.getDeadline().toString();
                    ((ShowCardTaskHolder) holder).showTaskName.setText(object.getTaskName());
                    ((ShowCardTaskHolder) holder).showAssignee.setText(object.getAssignee().toString());
                    ((ShowCardTaskHolder) holder).showStatus.setText(object.getStatus().toString());
                    ((ShowCardTaskHolder) holder).showDeadline.setText(splitDeadline.substring(0, 19).replace("T", "\n"));
                    break;
                case TaskModel.SHOW_UPDATE_TASK:
                    String splitStartdate = object.getStartTime().toString();
                    String splitEnddate = object.getEndTime().toString();
                    ((UpdateTaskHolder) holder).showTaskname.setText(object.getTaskName());
                    ((UpdateTaskHolder) holder).showIDtask.setText(object.getTaskId().toString());
                    ((UpdateTaskHolder) holder).showOldID.setTag(object.getTaskName());
                    ((UpdateTaskHolder) holder).showDateDeadline.setTag(object.getDeadline());
                    ((UpdateTaskHolder) holder).showNote.setText("");
                    ((UpdateTaskHolder) holder).showDescription.setText(object.getDescription());
                    ((UpdateTaskHolder) holder).showStartdate.setText(splitStartdate.substring(0, 19).replace("T", "\n"));
                    ((UpdateTaskHolder) holder).showEnddate.setText(splitEnddate.substring(0, 19).replace("T", "\n"));
                    ((UpdateTaskHolder) holder).showResult.setText(object.getResult());
                    ((UpdateTaskHolder) holder).showStatus.setTag(object.getStatus());
                    ((UpdateTaskHolder) holder).showCreator.setTag(object.getAccountCreated());
//                    ((UpdateTaskHolder) holder).btnImg.setTag(object.getAssignee());
//                    ((UpdateTaskHolder) holder).showImgResolution.setTag(object.getAssignee());
                    ((UpdateTaskHolder) holder).showReviewer.setText(object.getReviewerId().toString());
                    ((UpdateTaskHolder) holder).showConfirm.setTag(object.getConfirmId());
                    ((UpdateTaskHolder) holder).showDateReview.setText(object.getReviewTime().toString());
                    ((UpdateTaskHolder) holder).showMark.setTag(object.getAssignee());
                    ((UpdateTaskHolder) holder).showReview.setText(object.getManagerComment());
                    break;
                case TaskModel.SHOW_REVIEW_TASK:
                    String startdate = object.getStartTime().toString();
                    String enddate = object.getEndTime().toString();
                    String dateReview = object.getReviewTime().toString();
                    ((UpdateTaskHolder) holder).showTaskname.setText(object.getTaskName());
                    ((UpdateTaskHolder) holder).showIDtask.setText(object.getTaskId().toString());
                    ((UpdateTaskHolder) holder).showOldID.setTag(object.getTaskName());
                    ((UpdateTaskHolder) holder).showDateDeadline.setTag(object.getDeadline());
                    ((UpdateTaskHolder) holder).showNote.setText("");
                    ((UpdateTaskHolder) holder).showDescription.setText(object.getDescription());
                    ((UpdateTaskHolder) holder).showStartdate.setText(startdate.substring(0, 19).replace("T", "\n"));
                    ((UpdateTaskHolder) holder).showEnddate.setText(enddate.substring(0, 19).replace("T", "\n"));
                    ((UpdateTaskHolder) holder).showResult.setText(object.getResult());
                    ((UpdateTaskHolder) holder).showStatus.setTag(object.getStatus());
                    ((UpdateTaskHolder) holder).showCreator.setTag(object.getAccountCreated());
                    ((UpdateTaskHolder) holder).showReviewer.setText(dateReview.substring(0, 19).replace("T", "\n"));
                    ((UpdateTaskHolder) holder).showConfirm.setTag(object.getConfirmId());
                    ((UpdateTaskHolder) holder).showDateReview.setText(object.getReviewTime().toString());
                    ((UpdateTaskHolder) holder).showMark.setTag(object.getAssignee());
                    ((UpdateTaskHolder) holder).showReview.setText(object.getManagerComment());
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
