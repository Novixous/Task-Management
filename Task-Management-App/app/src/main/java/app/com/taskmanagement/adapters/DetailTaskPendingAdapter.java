package app.com.taskmanagement.adapters;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import app.com.taskmanagement.R;
import app.com.taskmanagement.model.AccountModel;
import app.com.taskmanagement.model.TaskModel;
import app.com.taskmanagement.model.response.LoginResponse;
import app.com.taskmanagement.model.response.TaskResponse;
import app.com.taskmanagement.util.GsonRequest;
import app.com.taskmanagement.util.PreferenceUtil;
import app.com.taskmanagement.util.SingletonRequestQueue;
import app.com.taskmanagement.util.TimeUtil;

public class DetailTaskPendingAdapter extends RecyclerView.Adapter {
    private AccountModel creator;
    private static final Integer[] id_not_show_update_user = {
            R.id.btnCreateTask,
            R.id.btnApprove,
            R.id.btnCloneTask,
            R.id.btnDecline,
    };
    Context mContext;
    TaskModel taskModel;
    private AccountModel currentAccount;
    Boolean dataLoaded;

    HashMap<Long, String> approveList;
    HashMap<Long, String> roleList;
    HashMap<Long, String> statusList;

    public DetailTaskPendingAdapter(Context context, Long taskId, HashMap<Long, String> approveList, HashMap<Long, String> roleList, HashMap<Long, String> statusList) {
        this.mContext = context;
        this.taskModel = new TaskModel();
        this.currentAccount = PreferenceUtil.getAccountFromSharedPreferences(mContext);
        this.dataLoaded = false;
        this.approveList = approveList;
        this.roleList = roleList;
        this.statusList = statusList;
        this.creator = new AccountModel();
        getTaskById(taskId);
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

            this.valueNote = (TextView) itemView.findViewById((R.id.valueNote));
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_show_task, parent, false);
        switch (currentAccount.getRoleId().intValue()) {
            case 0:
                for (int i = 0; i < id_not_show_update_user.length; i++) {
                    view.findViewById(id_not_show_update_user[i]).setVisibility(View.GONE);
                }
                break;
            case 1:
                break;
            case 2:
                break;
        }
        return new TaskFormHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


//                    String splitStartdate = object.getStartTime().toString();
//                    String splitEnddate = object.getEndTime().toString();
        if (dataLoaded) {

            ((TaskFormHolder) holder).valueTaskname.setText(taskModel.getTaskName().toString());
            ((TaskFormHolder) holder).valueIDtask.setText(taskModel.getTaskId().toString());
            ((TaskFormHolder) holder).valueOldID.setText(taskModel.getOldTaskId() != null ? taskModel.getOldTaskId().toString() : "None");

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
            valueDeadline.setText(dateFormat.format(Date.from(taskModel.getDeadline())));
            valueTimeDeadline.setText(timeFormat.format(Date.from(taskModel.getDeadline())));

            String note = "";

            if (taskModel.getEndTime() == null) {
                if (Instant.now().isAfter(taskModel.getDeadline())) {
                    note = TimeUtil.ConvertSectoDay(Duration.between(Instant.now(), taskModel.getDeadline()).getSeconds()) + " late!";
                } else {
                    note = TimeUtil.ConvertSectoDay(Duration.between(Instant.now(), taskModel.getDeadline()).getSeconds()) + "ahead deadline";
                }
            } else {
                if (taskModel.getEndTime().isAfter(taskModel.getDeadline())) {
                    note = TimeUtil.ConvertSectoDay(Duration.between(taskModel.getEndTime(), taskModel.getDeadline()).getSeconds()) + " late!";
                } else {
                    note = TimeUtil.ConvertSectoDay(Duration.between(taskModel.getEndTime(), taskModel.getDeadline()).getSeconds()) + " ahead deadline!";
                }
            }
            ((TaskFormHolder) holder).valueNote.setText(note);

            ((TaskFormHolder) holder).valueDescription.setText(taskModel.getDescription());
            ((TaskFormHolder) holder).valueStartdate.setText(taskModel.getStartTime() != null ? taskModel.getStartTime().toString() : "");
            ((TaskFormHolder) holder).valueEnddate.setText(taskModel.getEndTime() != null ? taskModel.getStartTime().toString() : "");
            ((TaskFormHolder) holder).valueResult.setText(taskModel.getResult() != null ? taskModel.getResult().toString() : "");

            Collection<String> statusValues = statusList.values();
            ArrayList<String> listOfStatus = new ArrayList<String>(statusValues);
            ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, listOfStatus);
            ((TaskFormHolder) holder).valueStatus.setAdapter(statusAdapter);
            ((TaskFormHolder) holder).valueStatus.setSelection(taskModel.getStatus().intValue());

            ((TaskFormHolder) holder).valueCreator.setText(creator.getFullName());
//                    ((UpdateTaskHolder) holder).btnImg.setTag(object.getAssignee());
//                    ((UpdateTaskHolder) holder).valueImgResolution.setTag(object.getAssignee());
            ((TaskFormHolder) holder).valueReviewer.setText("");
            ((TaskFormHolder) holder).valueDateReview.setText("");
            if (taskModel.getMark() == null) {
                ((TaskFormHolder) holder).valueMark.setVisibility(View.INVISIBLE);
            } else {
                ((TaskFormHolder) holder).valueMark.setValue(taskModel.getMark().intValue());
            }
            ((TaskFormHolder) holder).valueReview.setText("");
        }

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public void getTaskById(final Long taskId) {
        RequestQueue requestQueue = SingletonRequestQueue.getInstance(mContext.getApplicationContext()).getRequestQueue();
        HashMap<String, String> headers = new HashMap<>();
        String url = mContext.getResources().getString(R.string.BASE_URL) + "/task/" + taskId;
        GsonRequest<TaskResponse> gsonRequest = new GsonRequest<>(url, TaskResponse.class, headers, new Response.Listener<TaskResponse>() {
            @Override
            public void onResponse(TaskResponse response) {
                List<TaskResponse> responses = new ArrayList<>();
                responses.add(response);
                List<TaskModel> taskModels = TimeUtil.convertTaskResponseToTask(responses);
                taskModel = taskModels.get(0);
                getCreatorById(taskModel.getAccountCreated());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Connection Time Out", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(gsonRequest);
    }

    public void getCreatorById(Long accountId) {
        RequestQueue requestQueue = SingletonRequestQueue.getInstance(mContext.getApplicationContext()).getRequestQueue();
        HashMap<String, String> headers = new HashMap<>();
        String url = mContext.getResources().getString(R.string.BASE_URL) + "/account?id=" + accountId;
        GsonRequest<LoginResponse> gsonRequest = new GsonRequest<>(url, LoginResponse.class, headers, new Response.Listener<LoginResponse>() {
            @Override
            public void onResponse(LoginResponse response) {
                creator = response.account;
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
