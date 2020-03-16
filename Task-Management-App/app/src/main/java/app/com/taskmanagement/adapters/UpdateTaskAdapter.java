package app.com.taskmanagement.adapters;

import android.content.Context;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Scroller;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.com.taskmanagement.R;
import app.com.taskmanagement.model.AccountModel;
import app.com.taskmanagement.model.TaskModel;
import app.com.taskmanagement.util.PreferenceUtil;

public class UpdateTaskAdapter extends RecyclerView.Adapter {
    private ArrayList<TaskModel> dataSet;
    Context mContext;
    int total_types;
    TaskModel taskModel;
    private List<AccountModel> listMembers;
    private AccountModel currentAccount;
    Boolean dataLoaded;

    public UpdateTaskAdapter(ArrayList<TaskModel> data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();
        this.taskModel = new TaskModel();
        currentAccount = PreferenceUtil.getAccountFromSharedPreferences(mContext);
        dataLoaded = false;
    }

    public static class TaskFormHolder extends RecyclerView.ViewHolder {
        TextView valueIDtask, valueNote, valueStartdate, valueEnddate, valueOldID, valueCreator, valueReviewer, valueDateReview;
        EditText valueDescription, valueTaskname, valueReview, valueResult;
        Spinner valueStatus, valueAssignee, valueConfirm;
        ImageButton btnImg;
        ImageView valueImgResolution;
        NumberPicker valueMark;
        Button valueDateDeadline, valueTimeDeadline;
        Button btnCreate, btnUpdate, btnApprove, btnDecline;

        public TaskFormHolder(@NonNull View itemView) {
            super(itemView);
            this.valueTaskname = (EditText) itemView.findViewById(R.id.valueTaskName);
            this.valueIDtask = (TextView) itemView.findViewById(R.id.valueIDTask);
            this.valueOldID = (TextView) itemView.findViewById(R.id.valueIDOldTask);
            this.valueDateDeadline = (Button) itemView.findViewById(R.id.valueDateDeadline);
            this.valueTimeDeadline = (Button) itemView.findViewById(R.id.valueTimeDeadline);
            this.valueCreator = (TextView) itemView.findViewById(R.id.valueCreator);
            this.valueAssignee = (Spinner) itemView.findViewById(R.id.valueAssignee);
            this.valueDescription = (EditText) itemView.findViewById(R.id.valueDescription);

            this.valueNote = (TextView) itemView.findViewById(R.id.valueNote);
            this.valueReviewer = (TextView) itemView.findViewById(R.id.valueReviewer);
            this.valueStatus = (Spinner) itemView.findViewById(R.id.valueStatus);
            this.valueStartdate = (TextView) itemView.findViewById(R.id.valueDateStart);
            this.valueEnddate = (TextView) itemView.findViewById(R.id.valueDateEnd);
            this.valueResult = (EditText) itemView.findViewById(R.id.valueResult);
            this.btnImg = (ImageButton) itemView.findViewById(R.id.btnImg);
            this.valueImgResolution = (ImageView) itemView.findViewById(R.id.valueImgResolution);

            this.valueDateReview = (TextView) itemView.findViewById(R.id.valueDateReview);
            this.valueMark = (NumberPicker) itemView.findViewById(R.id.valueMark);
            this.valueReview = (EditText) itemView.findViewById(R.id.valueReview);

            this.btnCreate = (Button) itemView.findViewById(R.id.btnCreateTask);
            this.btnUpdate = (Button) itemView.findViewById(R.id.btnUpdateTask);
            this.btnApprove = (Button) itemView.findViewById(R.id.btnApprove);
            this.btnDecline = (Button) itemView.findViewById(R.id.btnDecline);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        Integer[] id_not_show_update = {
                R.id.btnCreateTask,
                R.id.btnApprove,
                R.id.btnCloneTask,
                R.id.btnDecline,
        };

        Integer[] update_edit_textview = {
                R.id.valueResult
        };
        switch (viewType) {
            case TaskModel.SHOW_UPDATE_TASK:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_show_task, parent, false);
                for (int i = 0; i < id_not_show_update.length; i++) {
                    view.findViewById(id_not_show_update[i]).setVisibility(View.GONE);
                }

                for (int i = 0; i < update_edit_textview.length; i++) {
                    final EditText temp = view.findViewById(update_edit_textview[i]);
                    temp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            temp.setCursorVisible(true);
                            temp.setFocusableInTouchMode(true);
                            temp.setInputType(InputType.TYPE_CLASS_TEXT);
                            temp.setTextIsSelectable(true);
                            temp.requestFocus();
                            temp.setMovementMethod(new ScrollingMovementMethod());
                            temp.setScroller(new Scroller(mContext));
                            temp.setVerticalScrollBarEnabled(true);
                        }
                    });
                }
                return new NewTaskAdapter.TaskFormHolder(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final TaskModel object = dataSet.get(position);
        if (object != null) {
            switch (object.type) {
                case TaskModel.SHOW_UPDATE_TASK:
//                    String splitStartdate = object.getStartTime().toString();
//                    String splitEnddate = object.getEndTime().toString();
                    ((NewTaskAdapter.TaskFormHolder) holder).valueIDtask.setText("");
                    ((NewTaskAdapter.TaskFormHolder) holder).valueOldID.setTag("");
                    ((NewTaskAdapter.TaskFormHolder) holder).valueDateDeadline.setTag("");
                    ((NewTaskAdapter.TaskFormHolder) holder).valueNote.setText("");
                    ((NewTaskAdapter.TaskFormHolder) holder).valueDescription.setText("");
                    ((NewTaskAdapter.TaskFormHolder) holder).valueStartdate.setText("");
                    ((NewTaskAdapter.TaskFormHolder) holder).valueEnddate.setText("");
                    ((NewTaskAdapter.TaskFormHolder) holder).valueResult.setText("");
                    ((NewTaskAdapter.TaskFormHolder) holder).valueStatus.setTag("");
                    ((NewTaskAdapter.TaskFormHolder) holder).valueCreator.setTag("");
//                    ((UpdateTaskHolder) holder).btnImg.setTag(object.getAssignee());
//                    ((UpdateTaskHolder) holder).valueImgResolution.setTag(object.getAssignee());
                    ((NewTaskAdapter.TaskFormHolder) holder).valueReviewer.setText("");
                    ((NewTaskAdapter.TaskFormHolder) holder).valueDateReview.setText("");
                    ((NewTaskAdapter.TaskFormHolder) holder).valueMark.setTag("");
                    ((NewTaskAdapter.TaskFormHolder) holder).valueReview.setText("");
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
