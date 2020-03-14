package app.com.taskmanagement.adapters;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Scroller;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import app.com.taskmanagement.R;
import app.com.taskmanagement.model.AccountModel;
import app.com.taskmanagement.model.TaskModel;
import app.com.taskmanagement.model.response.UserListReponse;
import app.com.taskmanagement.util.GsonRequest;
import app.com.taskmanagement.util.PreferenceUtil;

public class TaskAdapter extends RecyclerView.Adapter {
    private ArrayList<TaskModel> dataSet;
    Context mContext;
    int total_types;
    TaskModel taskModel;
    private List<AccountModel> listMembers;
    private AccountModel currentAccount;
    Boolean dataLoaded;

    public TaskAdapter(ArrayList<TaskModel> data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();
        this.taskModel = new TaskModel();
        currentAccount = PreferenceUtil.getAccountFromSharedPreferences(mContext);
        dataLoaded = false;
    }

    public static class ShowCardTaskHolder extends RecyclerView.ViewHolder {
        TextView valueTaskName, valueAssignee, valueStatus, valueDeadline;

        public ShowCardTaskHolder(@NonNull View itemView) {
            super(itemView);
            this.valueTaskName = (TextView) itemView.findViewById(R.id.valuetxtTaskName);
            this.valueAssignee = (TextView) itemView.findViewById(R.id.valuetxtAssignee);
            this.valueStatus = (TextView) itemView.findViewById(R.id.valuetxtStatus);
            this.valueDeadline = (TextView) itemView.findViewById(R.id.valuetxtDeadline);
        }
    }

    public static class TaskFormHolder extends RecyclerView.ViewHolder {
        TextView valueIDtask, valueNote, valueStartdate, valueEnddate, valueCreator, valueReviewer, valueDateReview;
        EditText valueDescription, valueTaskname, valueReview, valueResult;
        Spinner valueOldID, valueStatus, valueAssignee, valueConfirm;
        ImageButton btnImg;
        ImageView valueImgResolution;
        NumberPicker valueMark;
        Button valueDateDeadline, valueTimeDeadline;
        Button btnCreate, btnUpdate, btnApprove, btnDecline;

        public TaskFormHolder(@NonNull View itemView) {
            super(itemView);
            this.valueTaskname = (EditText) itemView.findViewById(R.id.valueTaskName);
            this.valueIDtask = (TextView) itemView.findViewById(R.id.valueIDTask);
            this.valueOldID = (Spinner) itemView.findViewById(R.id.valueIDOldTask);
            this.valueDateDeadline = (Button) itemView.findViewById(R.id.valueDateDeadline);
            this.valueTimeDeadline = (Button) itemView.findViewById(R.id.valueTimeDeadline);
            this.valueCreator = (TextView) itemView.findViewById(R.id.valueCreator);
            this.valueAssignee = (Spinner) itemView.findViewById(R.id.valueAssignee);
            this.valueDescription = (EditText) itemView.findViewById(R.id.valueDescription);

            this.valueNote = (TextView) itemView.findViewById(R.id.valueNote);
            this.valueReviewer = (TextView) itemView.findViewById(R.id.valueReviewer);
            this.valueConfirm = (Spinner) itemView.findViewById(R.id.valueConfirm);
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
                R.id.valueStatus,
                R.id.txtNote,
                R.id.valueNote,
                R.id.lineStatus,
                R.id.txtDateStart,
                R.id.valueDateStart,
                R.id.txtDateEnd,
                R.id.valueDateEnd,
                R.id.lineDate,
                R.id.txtImgResolution,
                R.id.btnImg,
                R.id.valueImgResolution,
                R.id.linePhoto,
                R.id.txtResult,
                R.id.valueResult,
                R.id.lineResult,
                R.id.txtReviewer,
                R.id.valueReviewer,
                R.id.lineReviewer,
                R.id.txtConfirm,
                R.id.valueConfirm,
                R.id.lineConfirm,
                R.id.txtMark,
                R.id.valueMark,
                R.id.txtDateReview,
                R.id.valueDateReview,
                R.id.lineMark,
                R.id.txtReview,
                R.id.valueReview,
                R.id.lineReview,
                R.id.btnUpdateTask,
                R.id.btnApprove,
                R.id.btnDecline,
                R.id.valueIDTask,
                R.id.txtIDTask,
                R.id.txtIdOldTask,
                R.id.valueIDOldTask,
                R.id.lineID
        };
        Integer[] id_not_show_update = {
                R.id.btnCreateTask,
                R.id.btnApprove,
                R.id.btnDecline,
        };
        Integer[] id_not_show_review = {
                R.id.btnCreateTask,
        };
        Integer[] create_edit_textview = {
                R.id.valueDescription,
                R.id.valueTaskName,
        };
        Integer[] update_edit_textview = {
                R.id.valueResult
        };
        Integer[] update_review_textview = {
                R.id.valueReview
        };
        switch (viewType) {
            case TaskModel.SHOW_FORM_CREATE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_show_task, parent, false);
                for (int i = 0; i < id_not_show_create_task.length; i++) {
                    view.findViewById(id_not_show_create_task[i]).setVisibility(View.GONE);
                }
                for (int i = 0; i < create_edit_textview.length; i++) {
                    final EditText temp = view.findViewById(create_edit_textview[i]);
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
                return new TaskFormHolder(view);
            case TaskModel.SHOW_REVIEW_TASK:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_show_task, parent, false);
                for (int i = 0; i < id_not_show_review.length; i++) {
                    view.findViewById(id_not_show_review[i]).setVisibility(View.GONE);
                }
                return new TaskFormHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final TaskModel object = dataSet.get(position);
        if (object != null) {
            switch (object.type) {

                case TaskModel.SHOW_FORM_CREATE:
//                    -Taskname-
                    ((TaskFormHolder) holder).valueTaskname.setText(object.getTaskName());
//                    -Choose date and time of deadline-
                    final Button valueDeadline = ((TaskFormHolder) holder).valueDateDeadline;
                    final Button valueTimeDeadline = ((TaskFormHolder) holder).valueTimeDeadline;
//                    Choose date
                    final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    final Calendar newCalendar = Calendar.getInstance();
                    final DatePickerDialog pickDate = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            Calendar newDate = Calendar.getInstance();
                            newDate.set(year, monthOfYear, dayOfMonth);
                            String date = dateFormat.format(newDate.getTime());
                            valueDeadline.setText(date);
                            taskModel.setDate(date);
                        }
                    }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                    valueDeadline.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pickDate.show();
                            pickDate.getDatePicker().setMinDate(System.currentTimeMillis());
                        }
                    });
//                    Choose time
                    final DateFormat timeFormat = new SimpleDateFormat("HH:mm");
                    final Calendar newClock = Calendar.getInstance();
                    final TimePickerDialog pickTime = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            Calendar newClock = Calendar.getInstance();
                            newClock.set(0, 0, 0, hourOfDay, minute);
                            String time = timeFormat.format(newClock.getTime());
                            valueTimeDeadline.setText(time);
                            taskModel.setTime(time);
                        }
                    }, newClock.get(Calendar.HOUR_OF_DAY), newClock.get(Calendar.MINUTE), true);
                    valueTimeDeadline.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pickTime.show();
                        }
                    });
//                    -Choose Assignee-
                    ((TaskFormHolder) holder).valueAssignee.setTag(object.getAssignee());
                    ((TaskFormHolder) holder).valueAssignee.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (!dataLoaded) {
                                getUserList();
                                return false;
                            } else {
                                return false;
                            }
                        }
                    });
                    List<String> spinnerItems = new ArrayList<>();
                    if (listMembers != null) {
                        for (AccountModel account : listMembers) {
                            spinnerItems.add(account.getFullName());
                        }
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext,
                            android.R.layout.simple_spinner_item, spinnerItems);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    ((TaskFormHolder) holder).valueAssignee.setAdapter(dataAdapter);
//                    -Description-
                    ((TaskFormHolder) holder).valueDescription.setText(object.getDescription());
//                    -Time created-
                    taskModel.setCreatedTime(Instant.now());
//                    -creator-
                    taskModel.setAccountCreated(currentAccount.getAccountId());
                    ((TaskFormHolder) holder).valueCreator.setText(currentAccount.getFullName());
//                    -status default-
                    taskModel.setStatus(new Long(0));
//                    -confirm default-
                    taskModel.setConfirmId(new Long(0));
//                    -Button Create-
                    ((TaskFormHolder) holder).btnCreate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (currentAccount.getRoleId().intValue()) {
                                case 0://User
                                    taskModel.setAssignee(currentAccount.getAccountId());
                                    taskModel.setGroupId(currentAccount.getGroupId());
                                    taskModel.setApprovedId(new Long(0)); //not consider
                                    break;
                                case 1://Manager
                                    taskModel.setGroupId(currentAccount.getGroupId());
                                    taskModel.setApprovedId(new Long(1)); //approve
                                    break;
                                case 2://Admin
                                    taskModel.setApprovedId(new Long(1)); //approve
                                    break;
                            }
                        }
                    });
                    break;

                case TaskModel.SHOW_CARD_TASK:
                    String splitDeadline = object.getDeadline().toString();
                    ((ShowCardTaskHolder) holder).valueTaskName.setText(object.getTaskName());
                    ((ShowCardTaskHolder) holder).valueAssignee.setText(object.getAssignee().toString());
                    ((ShowCardTaskHolder) holder).valueStatus.setText(object.getStatus().toString());
                    ((ShowCardTaskHolder) holder).valueDeadline.setText(splitDeadline.substring(0, 19).replace("T", "\n"));
                    break;

                case TaskModel.SHOW_UPDATE_TASK:
                    String splitStartdate = object.getStartTime().toString();
                    String splitEnddate = object.getEndTime().toString();
                    ((TaskFormHolder) holder).valueTaskname.setText(object.getTaskName());
                    ((TaskFormHolder) holder).valueIDtask.setText(object.getTaskId().toString());
                    ((TaskFormHolder) holder).valueOldID.setTag(object.getTaskName());
                    ((TaskFormHolder) holder).valueDateDeadline.setTag(object.getDeadline());
                    ((TaskFormHolder) holder).valueNote.setText("");
                    ((TaskFormHolder) holder).valueDescription.setText(object.getDescription());
                    ((TaskFormHolder) holder).valueStartdate.setText(splitStartdate.substring(0, 19).replace("T", "\n"));
                    ((TaskFormHolder) holder).valueEnddate.setText(splitEnddate.substring(0, 19).replace("T", "\n"));
                    ((TaskFormHolder) holder).valueResult.setText(object.getResult());
                    ((TaskFormHolder) holder).valueStatus.setTag(object.getStatus());
                    ((TaskFormHolder) holder).valueCreator.setTag(object.getAccountCreated());
//                    ((UpdateTaskHolder) holder).btnImg.setTag(object.getAssignee());
//                    ((UpdateTaskHolder) holder).valueImgResolution.setTag(object.getAssignee());
                    ((TaskFormHolder) holder).valueReviewer.setText(object.getReviewerId().toString());
                    ((TaskFormHolder) holder).valueConfirm.setTag(object.getConfirmId());
                    ((TaskFormHolder) holder).valueDateReview.setText(object.getReviewTime().toString());
                    ((TaskFormHolder) holder).valueMark.setTag(object.getAssignee());
                    ((TaskFormHolder) holder).valueReview.setText(object.getManagerComment());
                    break;
                case TaskModel.SHOW_REVIEW_TASK:
                    String startdate = object.getStartTime().toString();
                    String enddate = object.getEndTime().toString();
                    String dateReview = object.getReviewTime().toString();
                    ((TaskFormHolder) holder).valueTaskname.setText(object.getTaskName());
                    ((TaskFormHolder) holder).valueIDtask.setText(object.getTaskId().toString());
                    ((TaskFormHolder) holder).valueOldID.setTag(object.getTaskName());
                    ((TaskFormHolder) holder).valueDateDeadline.setTag(object.getDeadline());
                    ((TaskFormHolder) holder).valueNote.setText("");
                    ((TaskFormHolder) holder).valueDescription.setText(object.getDescription());
                    ((TaskFormHolder) holder).valueStartdate.setText(startdate.substring(0, 19).replace("T", "\n"));
                    ((TaskFormHolder) holder).valueEnddate.setText(enddate.substring(0, 19).replace("T", "\n"));
                    ((TaskFormHolder) holder).valueResult.setText(object.getResult());
                    ((TaskFormHolder) holder).valueStatus.setTag(object.getStatus());
                    ((TaskFormHolder) holder).valueCreator.setTag(object.getAccountCreated());
                    ((TaskFormHolder) holder).valueReviewer.setText(dateReview.substring(0, 19).replace("T", "\n"));
                    ((TaskFormHolder) holder).valueConfirm.setTag(object.getConfirmId());
                    ((TaskFormHolder) holder).valueDateReview.setText(object.getReviewTime().toString());
                    ((TaskFormHolder) holder).valueMark.setTag(object.getAssignee());
                    ((TaskFormHolder) holder).valueReview.setText(object.getManagerComment());
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void getUserList() {
        String url = String.format(mContext.getResources().getString(R.string.BASE_URL) + "/accounts?fieldName=group_id&fieldValue=" + currentAccount.getGroupId());
        HashMap<String, String> header = new HashMap<>();
        RequestQueue requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        GsonRequest<UserListReponse> userListReponseGsonRequest = new GsonRequest<>(url, UserListReponse.class, header, new Response.Listener<UserListReponse>() {
            @Override
            public void onResponse(UserListReponse response) {
                listMembers = new ArrayList<>();
                listMembers.addAll(response.getAccountModels());
                dataLoaded = true;
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
