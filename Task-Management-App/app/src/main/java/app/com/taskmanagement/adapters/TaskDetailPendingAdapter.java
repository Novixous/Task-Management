package app.com.taskmanagement.adapters;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import app.com.taskmanagement.R;
import app.com.taskmanagement.model.AccountModel;
import app.com.taskmanagement.model.TaskModel;
import app.com.taskmanagement.model.request.TaskCreateRequest;
import app.com.taskmanagement.model.response.LoginResponse;
import app.com.taskmanagement.model.response.TaskResponse;
import app.com.taskmanagement.util.GsonRequest;
import app.com.taskmanagement.util.PreferenceUtil;
import app.com.taskmanagement.util.SingletonRequestQueue;
import app.com.taskmanagement.util.TimeUtil;

public class TaskDetailPendingAdapter extends RecyclerView.Adapter {
    private Context mContext;
    public static final int ROLE_USER = 0;
    public static final int ROLE_MANAGER = 1;
    public static final int ROLE_ADMIN = 2;

    private static final Integer[] ID_NOT_SHOW_DETAIL_USER = {
            R.id.btnApprove,
            R.id.btnDecline,
    };
    private static final Integer[] ID_NOT_SHOW_GENERAL = {
            R.id.btnClose,
            R.id.btnCreateTask,
            R.id.btnCloneTask,
            R.id.txtDateStart,
            R.id.valueDateStart,
            R.id.txtDateEnd,
            R.id.valueDateEnd,
            R.id.lineDate,
            R.id.txtImgResolution,
            R.id.btnImg,
            R.id.linePhoto,
            R.id.txtResult,
            R.id.valueResult,
            R.id.lineResult,
            R.id.txtReviewer,
            R.id.valueReviewer,
            R.id.lineReviewer,
            R.id.txtMark,
            R.id.valueMark,
            R.id.txtDateReview,
            R.id.valueDateReview,
            R.id.lineConfirm,
            R.id.txtReview,
            R.id.valueReview,
            R.id.lineReview
    };

    private TaskModel taskModel;
    private AccountModel currentAccount;

    private AccountModel creator;
    private AccountModel assignee;
    private String lastModifiedName;
    private HashMap<Long, String> approveList;
    private HashMap<Long, String> roleList;
    private HashMap<Long, String> statusList;
    private Boolean dataLoaded;

    public TaskDetailPendingAdapter(Context context, Long taskId, HashMap<Long, String> approveList, HashMap<Long, String> roleList, HashMap<Long, String> statusList) {
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
        EditText valueTaskName;
        TextView valueIDTask, valueOldID;
        Button valueDateDeadline, valueTimeDeadline;
        Spinner valueStatus;
        TextView valueNote, valueCreator;
        Spinner valueAssignee;
        EditText valueDescription;
        TextView valueModifiedBy, valueModifiedAt;

        Button btnUpdate, btnApprove, btnDecline;

        public TaskFormHolder(@NonNull View itemView) {
            super(itemView);

            this.valueTaskName = (EditText) itemView.findViewById(R.id.valueTaskName);
            this.valueIDTask = (TextView) itemView.findViewById(R.id.valueIDTask);
            this.valueOldID = (TextView) itemView.findViewById(R.id.valueIDOldTask);
            this.valueDateDeadline = (Button) itemView.findViewById(R.id.valueDateDeadline);
            this.valueTimeDeadline = (Button) itemView.findViewById(R.id.valueTimeDeadline);
            this.valueStatus = (Spinner) itemView.findViewById(R.id.valueStatus);
            this.valueNote = (TextView) itemView.findViewById(R.id.valueNote);
            this.valueCreator = (TextView) itemView.findViewById(R.id.valueCreator);
            this.valueAssignee = (Spinner) itemView.findViewById(R.id.valueAssignee);
            this.valueDescription = (EditText) itemView.findViewById(R.id.valueDescription);
            this.valueModifiedBy = (TextView) itemView.findViewById(R.id.valueModifiedBy);
            this.valueModifiedAt = (TextView) itemView.findViewById(R.id.valueModifedAt);

            this.btnUpdate = (Button) itemView.findViewById(R.id.btnUpdateTask);
            this.btnApprove = (Button) itemView.findViewById(R.id.btnApprove);
            this.btnDecline = (Button) itemView.findViewById(R.id.btnDecline);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_task_fragment, parent, false);
        for (int i = 0; i < ID_NOT_SHOW_GENERAL.length; i++) {
            view.findViewById(ID_NOT_SHOW_GENERAL[i]).setVisibility(View.GONE);
        }
        switch (currentAccount.getRoleId().intValue()) {
            case ROLE_USER:
                for (int i = 0; i < ID_NOT_SHOW_DETAIL_USER.length; i++) {
                    view.findViewById(ID_NOT_SHOW_DETAIL_USER[i]).setVisibility(View.GONE);
                }
                break;
            case ROLE_MANAGER:
                break;
            case ROLE_ADMIN:
                break;
        }
        return new TaskFormHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {


        if (dataLoaded) {
//                  Set task name
            ((TaskFormHolder) holder).valueTaskName.setText(taskModel.getTaskName().toString());
            //      Set task id
            ((TaskFormHolder) holder).valueIDTask.setText(taskModel.getTaskId().toString());
            //      Set task old id
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
                }
            }, newClock.get(Calendar.HOUR_OF_DAY), newClock.get(Calendar.MINUTE), true);
            valueTimeDeadline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pickTime.show();
                }
            });
            valueDeadline.setText(LocalDateTime.ofInstant(taskModel.getDeadline(), ZoneId.of("GMT")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            valueTimeDeadline.setText(LocalDateTime.ofInstant(taskModel.getDeadline(), ZoneId.of("GMT")).format(DateTimeFormatter.ofPattern("HH:mm")));
//                  Set status
            Collection<String> statusValues = statusList.values();
            ArrayList<String> listOfStatus = new ArrayList<String>(statusValues);
            ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, listOfStatus);
            ((TaskFormHolder) holder).valueStatus.setAdapter(statusAdapter);
            ((TaskFormHolder) holder).valueStatus.setSelection(taskModel.getStatus().intValue());
            ((TaskFormHolder) holder).valueStatus.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
//                  Set note
            String note = "";
            if (taskModel.getEndTime() == null) {
                if (LocalDateTime.now().isAfter(taskModel.getDeadline().atZone(ZoneId.of("GMT+0")).toLocalDateTime())) {
                    note = TimeUtil.ConvertSectoDay(Duration.between(taskModel.getDeadline().atZone(ZoneId.of("GMT+0")).toLocalDateTime(), LocalDateTime.now()).getSeconds()) + "late!";
                } else {
                    note = TimeUtil.ConvertSectoDay(Duration.between(LocalDateTime.now(), taskModel.getDeadline().atZone(ZoneId.of("GMT+0")).toLocalDateTime()).getSeconds()) + "ahead deadline";
                }
            } else {
                if (taskModel.getEndTime().isAfter(taskModel.getDeadline())) {
                    note = TimeUtil.ConvertSectoDay(Duration.between(taskModel.getDeadline(), taskModel.getDeadline()).getSeconds()) + " late!";
                } else {
                    note = TimeUtil.ConvertSectoDay(Duration.between(taskModel.getEndTime(), taskModel.getDeadline()).getSeconds()) + " ahead deadline!";
                }
            }
            //      Set note
            ((TaskFormHolder) holder).valueNote.setText(note);
            //      Set assignee
            List<String> assigneeSpinnerItems = new ArrayList<>();
            assigneeSpinnerItems.add("(" + assignee.getAccountId() + ")" + assignee.getFullName());
            ArrayAdapter<String> assigneeAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, assigneeSpinnerItems);
            ((TaskFormHolder) holder).valueAssignee.setAdapter(assigneeAdapter);
            ((TaskFormHolder) holder).valueAssignee.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });

//                  Set Creator
            ((TaskFormHolder) holder).valueCreator.setText(creator.getFullName());
            //                  Set Description
            ((TaskFormHolder) holder).valueDescription.setText(taskModel.getDescription());
            //                  last modified
            ((TaskFormHolder) holder).valueModifiedBy.setText(lastModifiedName);
            ((TaskFormHolder) holder).valueModifiedAt.setText(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm").format(taskModel.getEditedAt().atZone(ZoneId.of("GMT"))));

            ((TaskFormHolder) holder).btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TaskModel taskUpdate = new TaskModel();
                    taskUpdate.setTaskId(taskModel.getTaskId());
                    taskUpdate.setTaskName(((TaskFormHolder) holder).valueTaskName.getText().toString());
                    taskUpdate.setDate(((TaskFormHolder) holder).valueDateDeadline.getText().toString());
                    taskUpdate.setTime(((TaskFormHolder) holder).valueTimeDeadline.getText().toString());
                    taskUpdate.setDescription(((TaskFormHolder) holder).valueDescription.getText().toString());
                    taskUpdate.setEditedBy(currentAccount.getAccountId());
                    updateTask(taskUpdate);
                }
            });
            ((TaskFormHolder) holder).btnApprove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TaskModel taskUpdate = new TaskModel();
                    taskUpdate.setTaskId(taskModel.getTaskId());
                    taskUpdate.setApprovedId(Long.valueOf(1));
                    taskUpdate.setEditedBy(currentAccount.getAccountId());
                    updateTask(taskUpdate);
                }
            });
            ((TaskFormHolder) holder).btnDecline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TaskModel taskUpdate = new TaskModel();
                    taskUpdate.setTaskId(taskModel.getTaskId());
                    taskUpdate.setApprovedId(Long.valueOf(2));
                    taskUpdate.setClosed(true);
                    taskUpdate.setEditedBy(currentAccount.getAccountId());
                    updateTask(taskUpdate);
                }
            });

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
                getAssigneeById(taskModel.getAssignee());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Connection Time Out", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(gsonRequest);
    }

    public void getAssigneeById(Long accountId) {
        RequestQueue requestQueue = SingletonRequestQueue.getInstance(mContext.getApplicationContext()).getRequestQueue();
        HashMap<String, String> headers = new HashMap<>();
        String url = mContext.getResources().getString(R.string.BASE_URL) + "/account?id=" + accountId;
        GsonRequest<LoginResponse> gsonRequest = new GsonRequest<>(url, LoginResponse.class, headers, new Response.Listener<LoginResponse>() {
            @Override
            public void onResponse(LoginResponse response) {
                assignee = response.account;
                getLastModifiedById(taskModel.getEditedBy());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Connection Time Out", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(gsonRequest);
    }

    public void getLastModifiedById(Long accountId) {
        RequestQueue requestQueue = SingletonRequestQueue.getInstance(mContext.getApplicationContext()).getRequestQueue();
        HashMap<String, String> headers = new HashMap<>();
        String url = mContext.getResources().getString(R.string.BASE_URL) + "/account?id=" + accountId;
        GsonRequest<LoginResponse> gsonRequest = new GsonRequest<>(url, LoginResponse.class, headers, new Response.Listener<LoginResponse>() {
            @Override
            public void onResponse(LoginResponse response) {
                lastModifiedName = response.account.getFullName();
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

    public void updateTask(TaskModel taskModel) {
        String url = String.format(mContext.getResources().getString(R.string.BASE_URL) + "/task");
        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        RequestQueue requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        Gson gson = new Gson();
        List<TaskModel> taskModels = new ArrayList<>();
        taskModels.add(taskModel);
        TaskCreateRequest taskCreateRequest = new TaskCreateRequest(taskModels);
        String body = gson.toJson(taskCreateRequest);
        GsonRequest<Integer> taskResponseCreateRequest = new GsonRequest<>(Request.Method.PUT, url, Integer.class, header, body, new Response.Listener<Integer>() {
            @Override
            public void onResponse(Integer response) {
                Toast.makeText(mContext.getApplicationContext(), "Update successfully!", Toast.LENGTH_LONG);
                ((AppCompatActivity) mContext).getSupportFragmentManager().popBackStack();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.println(Log.ERROR, "", "");
                Toast.makeText(mContext.getApplicationContext(), "Update failed!", Toast.LENGTH_LONG);
            }
        });
        requestQueue.add(taskResponseCreateRequest);
    }

}
