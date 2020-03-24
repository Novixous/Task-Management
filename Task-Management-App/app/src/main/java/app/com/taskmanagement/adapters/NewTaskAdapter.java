package app.com.taskmanagement.adapters;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import app.com.taskmanagement.R;
import app.com.taskmanagement.TaskFragment;
import app.com.taskmanagement.model.AccountModel;
import app.com.taskmanagement.model.GroupModel;
import app.com.taskmanagement.model.TaskModel;
import app.com.taskmanagement.model.request.TaskCreateRequest;
import app.com.taskmanagement.model.response.GroupResponse;
import app.com.taskmanagement.model.response.UserListReponse;
import app.com.taskmanagement.util.GsonRequest;
import app.com.taskmanagement.util.PreferenceUtil;
import app.com.taskmanagement.util.SingletonRequestQueue;

public class NewTaskAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private TaskModel taskModel;
    private List<AccountModel> listMembers;
    private List<GroupModel> groupModelList;
    private AccountModel currentAccount;
    private Boolean dataLoaded;
    private AccountModel assignee;
    private HashMap<Long, String> approveList;
    private HashMap<Long, String> roleList;
    private HashMap<Long, String> statusList;

    public NewTaskAdapter(Context context, HashMap<Long, String> approveList, HashMap<Long, String> roleList, HashMap<Long, String> statusList) {
        this.approveList = approveList;
        this.roleList = roleList;
        this.statusList = statusList;
        this.mContext = context;
        this.taskModel = new TaskModel();
        currentAccount = PreferenceUtil.getAccountFromSharedPreferences(mContext);
        this.assignee = null;
        this.groupModelList = null;
        dataLoaded = false;
        if (!currentAccount.getRoleId().equals(Long.valueOf(2))) {
            getUserList(currentAccount.getGroupId());
        } else {
            getGroups();
        }
    }

    public NewTaskAdapter(Context context, HashMap<Long, String> approveList, HashMap<Long, String> roleList, HashMap<Long, String> statusList, TaskModel taskModel) {
        this.approveList = approveList;
        this.roleList = roleList;
        this.statusList = statusList;
        this.mContext = context;
        this.taskModel = new TaskModel();
        this.taskModel.setOldTaskId(taskModel.getTaskId());
        this.taskModel.setTaskName(taskModel.getTaskName());
        this.taskModel.setDescription(taskModel.getDescription());
        currentAccount = PreferenceUtil.getAccountFromSharedPreferences(mContext);
        this.assignee = null;
        this.groupModelList = null;
        dataLoaded = false;
        if (!currentAccount.getRoleId().equals(Long.valueOf(2))) {
            getUserList(currentAccount.getGroupId());
        } else {
            getGroups();
        }
    }


    public static class TaskFormHolder extends RecyclerView.ViewHolder {
        TextView valueIDtask, valueOldID, valueCreator;
        EditText valueDescription, valueTaskname;
        Button valueAssignee;
        Spinner valueGroup;
        Button valueDateDeadline, valueTimeDeadline;
        Button btnCreate;

        public TaskFormHolder(@NonNull View itemView) {
            super(itemView);
            this.valueTaskname = (EditText) itemView.findViewById(R.id.valueTaskName);
            this.valueIDtask = (TextView) itemView.findViewById(R.id.valueIDTask);
            this.valueOldID = (TextView) itemView.findViewById(R.id.valueIDOldTask);
            this.valueDateDeadline = (Button) itemView.findViewById(R.id.valueDateDeadline);
            this.valueTimeDeadline = (Button) itemView.findViewById(R.id.valueTimeDeadline);
            this.valueCreator = (TextView) itemView.findViewById(R.id.valueCreator);
            this.valueGroup = (Spinner) itemView.findViewById(R.id.valueGroup);
            this.valueAssignee = (Button) itemView.findViewById(R.id.valueAssignee);
            this.valueDescription = (EditText) itemView.findViewById(R.id.valueDescription);
            this.btnCreate = (Button) itemView.findViewById(R.id.btnCreateTask);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        Integer[] id_not_show_create_task = {
                R.id.lastModifedContainer,
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
                R.id.lineConfirm,
                R.id.txtMark,
                R.id.valueMark,
                R.id.txtDateReview,
                R.id.valueDateReview,
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
                R.id.lineID,
                R.id.btnCloneTask,
                R.id.btnClose
        };


        switch (viewType) {
            case TaskModel.SHOW_FORM_CREATE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_show_task, parent, false);
                for (int i = 0; i < id_not_show_create_task.length; i++) {
                    view.findViewById(id_not_show_create_task[i]).setVisibility(View.GONE);
                }

                switch (currentAccount.getRoleId().intValue()) {
                    case 0:
                        view.findViewById(R.id.valueAssignee).setVisibility(View.GONE);
                        view.findViewById(R.id.txtAssignee).setVisibility(View.GONE);
                        view.findViewById(R.id.lineAssignee).setVisibility(View.GONE);
                        break;
                    case 2:
                        view.findViewById(R.id.txtGroup).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.valueGroup).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.lineGroup).setVisibility(View.VISIBLE);

                }
                return new TaskFormHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return 2;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        //              Task Name if from clone
        ((TaskFormHolder) holder).valueTaskname.setText(taskModel.getTaskName() != null ? taskModel.getTaskName() : "");
        //              Task description if from clone
        ((TaskFormHolder) holder).valueDescription.setText(taskModel.getDescription() != null ? taskModel.getDescription() : "");

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
//                    -Choose group
        List<String> spinnerGroupItems = new ArrayList<>();
        if (groupModelList != null) {
            for (GroupModel groupModel : groupModelList) {
                spinnerGroupItems.add(groupModel.getGroupName());
            }
        }
        if (listMembers == null) {
            ArrayAdapter<String> groupAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, spinnerGroupItems);
            groupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ((TaskFormHolder) holder).valueGroup.setAdapter(groupAdapter);
            ((TaskFormHolder) holder).valueGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    taskModel.setGroupId(groupModelList.get(position).getGroupId());
                    getUserList(groupModelList.get(position).getGroupId());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

//                    -Choose Assignee-
        List<String> spinnerAssigneeItems = new ArrayList<>();
        if (listMembers != null) {
            for (AccountModel account : listMembers) {
                spinnerAssigneeItems.add("(" + account.getAccountId() + ") " + account.getFullName());
            }
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Choose assignee");
        // add a radio button list
        if (spinnerAssigneeItems.size() > 0) {
            final String[] dialogAssigneeItems = spinnerAssigneeItems.toArray(new String[0]);
            int checkedItem = 0;
            ((TaskFormHolder) holder).valueAssignee.setText(dialogAssigneeItems[0]);
            taskModel.setAssignee(listMembers.get(checkedItem).getAccountId());
            builder.setSingleChoiceItems(dialogAssigneeItems, checkedItem, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    taskModel.setAssignee(listMembers.get(which).getAccountId());
                    ((TaskFormHolder) holder).valueAssignee.setText(dialogAssigneeItems[which]);
                }
            });

            // add OK and Cancel buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setNegativeButton("Cancel", null);

            ((TaskFormHolder) holder).valueAssignee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

        }
//                  show name creator
        ((TaskFormHolder) holder).valueCreator.setText(currentAccount.getFullName());
//                    -Button Create-
        ((TaskFormHolder) holder).btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                          -Taskname-
//                            String edtTaskName=((TaskFormHolder) holder).valueTaskname.getText().toString();
                taskModel.setTaskName(((TaskFormHolder) holder).valueTaskname.getText().toString());
//                          -Description-
                taskModel.setDescription(((TaskFormHolder) holder).valueDescription.getText().toString());
//                          -Time created-
                taskModel.setCreatedTime(Instant.now());
//                          -creator-
                taskModel.setAccountCreated(currentAccount.getAccountId());
//                          -status default-
                taskModel.setStatus(new Long(0));
//                          -closed-
                taskModel.setClosed(false);
//                            edited by
                taskModel.setEditedBy(currentAccount.getAccountId());
                taskModel.setEditedAt(Instant.now());
                switch (currentAccount.getRoleId().intValue()) {
                    case 0://User
                        taskModel.setAssignee(currentAccount.getAccountId());
                        taskModel.setGroupId(currentAccount.getGroupId());
                        taskModel.setApprovedId(new Long(0)); //not consider
                        createNewTask(taskModel);
                        break;
                    case 1://Manager
                        taskModel.setGroupId(currentAccount.getGroupId());
                        taskModel.setApprovedId(new Long(1)); //approve
                        createNewTask(taskModel);
                        break;
                    case 2://Admin
                        taskModel.setApprovedId(new Long(1)); //approve
                        createNewTask(taskModel);
                        break;
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return 1;
    }

    public void getUserList(Long groupId) {
        String url = String.format(mContext.getResources().getString(R.string.BASE_URL) + "/accounts?fieldName=group_id&fieldValue=" + groupId);
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

    public void createNewTask(TaskModel taskModel) {
        String url = String.format(mContext.getResources().getString(R.string.BASE_URL) + "/task");
        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        RequestQueue requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        Gson gson = new Gson();
        List<TaskModel> taskModels = new ArrayList<>();
        taskModels.add(taskModel);
        TaskCreateRequest taskCreateRequest = new TaskCreateRequest(taskModels);
        String body = gson.toJson(taskCreateRequest);
        GsonRequest<Integer> taskResponseCreateRequest = new GsonRequest<>(Request.Method.POST, url, Integer.class, header, body, new Response.Listener<Integer>() {
            @Override
            public void onResponse(Integer response) {
                if (response.intValue() >= 0) {
                    Toast.makeText(mContext, "Create successfully!", Toast.LENGTH_LONG);
                    ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new TaskFragment(approveList, roleList, statusList)).commit();
                    ((AppCompatActivity) mContext).setTitle("My Task");
                } else if (response.intValue() == -2) {
                    Toast.makeText(mContext, "Please input both date and time of deadline!", Toast.LENGTH_LONG).show();
                } else if (response.intValue() == -3) {
                    Toast.makeText(mContext, "Task name must not be blank", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(mContext, "Error creating task", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.println(Log.ERROR, "", "");
                Toast.makeText(mContext, "Create failed!", Toast.LENGTH_LONG);
            }
        });
        requestQueue.add(taskResponseCreateRequest);
    }

    public void getGroups() {
        String url = String.format(mContext.getResources().getString(R.string.BASE_URL) + "/groups");
        HashMap<String, String> header = new HashMap<>();
        RequestQueue requestQueue = SingletonRequestQueue.getInstance(mContext).getRequestQueue();
        GsonRequest<GroupResponse> userListReponseGsonRequest = new GsonRequest<>(url, GroupResponse.class, header, new Response.Listener<GroupResponse>() {
            @Override
            public void onResponse(GroupResponse response) {
                groupModelList = new ArrayList<>();
                groupModelList.addAll(response.getData());
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
