package app.com.taskmanagement.adapters;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import app.com.taskmanagement.R;
import app.com.taskmanagement.model.AccountModel;
import app.com.taskmanagement.model.TaskModel;
import app.com.taskmanagement.util.PreferenceUtil;

public class TaskAdapter extends RecyclerView.Adapter {
    private ArrayList<TaskModel> dataSet;
    Context mContext;
    int total_types;
    TaskModel taskModel;

    public TaskAdapter(ArrayList<TaskModel> data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();
        this.taskModel = new TaskModel();
    }

//    public static class CreateTaskHolder extends RecyclerView.ViewHolder {
//        TextView showTaskName,
//                showDescription;
//        Spinner showIdOld, showAssignee;
//        Button showDeadline, showTime;
//        Button btnCreate;

//        public CreateTaskHolder(@NonNull View itemView) {
//            super(itemView);
//            this.showTaskName = (TextView) itemView.findViewById(R.id.showTaskName);
//            this.showIdOld = (Spinner) itemView.findViewById(R.id.showIDOldTask);
//            this.showDeadline = (Button) itemView.findViewById(R.id.showDateDeadline);
//            this.showTime = (Button) itemView.findViewById(R.id.showTimeDeadline);
//            this.showAssignee = (Spinner) itemView.findViewById(R.id.showAssignee);
//            this.showDescription = (TextView) itemView.findViewById(R.id.showDescription);
//            this.btnCreate = (Button) itemView.findViewById(R.id.btnCreateTask);
//        }
//    }

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

    public static class TaskFormHolder extends RecyclerView.ViewHolder {
        TextView showTaskname, showIDtask, showNote, showDescription,
                showStartdate, showEnddate, showResult, showCreator, showReviewer, showReview, showDateReview;
        Spinner showOldID, showStatus, showAssignee, showConfirm;
        ImageButton btnImg;
        ImageView showImgResolution;
        NumberPicker showMark;
        Button showDateDeadline, showTimeDeadline;
        Button btnCreate;

        public TaskFormHolder(@NonNull View itemView) {
            super(itemView);
            this.showTaskname = (TextView) itemView.findViewById(R.id.showTaskName);
            this.showIDtask = (TextView) itemView.findViewById(R.id.showIDTask);
            this.showOldID = (Spinner) itemView.findViewById(R.id.showIDOldTask);
            this.showDateDeadline = (Button) itemView.findViewById(R.id.showDateDeadline);
            this.showTimeDeadline = (Button) itemView.findViewById(R.id.showTimeDeadline);
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
            this.btnCreate = (Button) itemView.findViewById(R.id.btnCreateTask);
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
                R.id.txtIDTask,
                R.id.txtIdOldTask,
                R.id.showIDOldTask,
                R.id.lineID
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
                return new TaskFormHolder(view);
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
                return new TaskFormHolder(view);
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
                return new TaskFormHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final AccountModel currentAccount = PreferenceUtil.getAccountFromSharedPreferences(mContext);

        final TaskModel object = dataSet.get(position);
        if (object != null) {
            switch (object.type) {

                case TaskModel.SHOW_FORM_CREATE:
                    ((TaskFormHolder) holder).showTaskname.setText(object.getTaskName());
                    ((TaskFormHolder) holder).showOldID.setTag(object.getOldTaskId());
                    final Button showDeadline = ((TaskFormHolder) holder).showDateDeadline;
                    showDeadline.setTag(object.getDeadline());
                    final Button showTimeDeadline = ((TaskFormHolder) holder).showTimeDeadline;
                    showTimeDeadline.setTag(object.getDeadline());
                    ((TaskFormHolder) holder).showAssignee.setTag(object.getAssignee());
                    ((TaskFormHolder) holder).showDescription.setText(object.getDescription());
                    ((TaskFormHolder) holder).btnCreate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            taskModel.setCreatedTime(Instant.now());
                            taskModel.setAccountCreated(currentAccount.getAccountId());
                            taskModel.setAssignee(currentAccount.getAccountId());
//                            createTask();
                        }
                    });

                    final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                            DateFormat.getDateInstance(DateFormat.LONG, Locale.US);
                    final Calendar newCalendar = Calendar.getInstance();
                    final DatePickerDialog pickDate = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            Calendar newDate = Calendar.getInstance();
                            newDate.set(year, monthOfYear, dayOfMonth);
                            String date = dateFormat.format(newDate.getTime());
                            showDeadline.setText(date);
                            taskModel.setDate(date);
                        }
                    }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

                    showDeadline.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pickDate.show();
                        }
                    });
                    final DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.US);
                    final Calendar newClock = Calendar.getInstance();
                    final TimePickerDialog pickTime = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            Calendar newClock = Calendar.getInstance();
                            newClock.set(0, 0, 0, hourOfDay, minute);
                            String time = timeFormat.format(newClock.getTime());
                            showTimeDeadline.setText(time);
                            taskModel.setTime(time);
                        }
                    }, newClock.get(Calendar.HOUR_OF_DAY), newClock.get(Calendar.MINUTE), true);
                    showTimeDeadline.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pickTime.show();
                        }
                    });
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
                    ((TaskFormHolder) holder).showTaskname.setText(object.getTaskName());
                    ((TaskFormHolder) holder).showIDtask.setText(object.getTaskId().toString());
                    ((TaskFormHolder) holder).showOldID.setTag(object.getTaskName());
                    ((TaskFormHolder) holder).showDateDeadline.setTag(object.getDeadline());
                    ((TaskFormHolder) holder).showNote.setText("");
                    ((TaskFormHolder) holder).showDescription.setText(object.getDescription());
                    ((TaskFormHolder) holder).showStartdate.setText(splitStartdate.substring(0, 19).replace("T", "\n"));
                    ((TaskFormHolder) holder).showEnddate.setText(splitEnddate.substring(0, 19).replace("T", "\n"));
                    ((TaskFormHolder) holder).showResult.setText(object.getResult());
                    ((TaskFormHolder) holder).showStatus.setTag(object.getStatus());
                    ((TaskFormHolder) holder).showCreator.setTag(object.getAccountCreated());
//                    ((UpdateTaskHolder) holder).btnImg.setTag(object.getAssignee());
//                    ((UpdateTaskHolder) holder).showImgResolution.setTag(object.getAssignee());
                    ((TaskFormHolder) holder).showReviewer.setText(object.getReviewerId().toString());
                    ((TaskFormHolder) holder).showConfirm.setTag(object.getConfirmId());
                    ((TaskFormHolder) holder).showDateReview.setText(object.getReviewTime().toString());
                    ((TaskFormHolder) holder).showMark.setTag(object.getAssignee());
                    ((TaskFormHolder) holder).showReview.setText(object.getManagerComment());
                    break;
                case TaskModel.SHOW_REVIEW_TASK:
                    String startdate = object.getStartTime().toString();
                    String enddate = object.getEndTime().toString();
                    String dateReview = object.getReviewTime().toString();
                    ((TaskFormHolder) holder).showTaskname.setText(object.getTaskName());
                    ((TaskFormHolder) holder).showIDtask.setText(object.getTaskId().toString());
                    ((TaskFormHolder) holder).showOldID.setTag(object.getTaskName());
                    ((TaskFormHolder) holder).showDateDeadline.setTag(object.getDeadline());
                    ((TaskFormHolder) holder).showNote.setText("");
                    ((TaskFormHolder) holder).showDescription.setText(object.getDescription());
                    ((TaskFormHolder) holder).showStartdate.setText(startdate.substring(0, 19).replace("T", "\n"));
                    ((TaskFormHolder) holder).showEnddate.setText(enddate.substring(0, 19).replace("T", "\n"));
                    ((TaskFormHolder) holder).showResult.setText(object.getResult());
                    ((TaskFormHolder) holder).showStatus.setTag(object.getStatus());
                    ((TaskFormHolder) holder).showCreator.setTag(object.getAccountCreated());
                    ((TaskFormHolder) holder).showReviewer.setText(dateReview.substring(0, 19).replace("T", "\n"));
                    ((TaskFormHolder) holder).showConfirm.setTag(object.getConfirmId());
                    ((TaskFormHolder) holder).showDateReview.setText(object.getReviewTime().toString());
                    ((TaskFormHolder) holder).showMark.setTag(object.getAssignee());
                    ((TaskFormHolder) holder).showReview.setText(object.getManagerComment());
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
