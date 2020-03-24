package app.com.taskmanagement.adapters;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import app.com.taskmanagement.R;
import app.com.taskmanagement.TaskDetailFragment;
import app.com.taskmanagement.model.AccountModel;
import app.com.taskmanagement.model.TaskModel;
import app.com.taskmanagement.model.response.TaskListResponse;
import app.com.taskmanagement.model.response.TaskResponse;
import app.com.taskmanagement.util.GsonRequest;
import app.com.taskmanagement.util.PreferenceUtil;
import app.com.taskmanagement.util.SingletonRequestQueue;
import app.com.taskmanagement.util.TimeUtil;

public class TaskCardPendingAdapter extends RecyclerView.Adapter {
    public static final int ROLE_USER = 0;
    public static final int ROLE_MANAGER = 1;
    public static final int ROLE_ADMIN = 2;
    private Context mContext;
    private ArrayList<TaskModel> dataSet;

    private HashMap<Long, String> approveList;
    private HashMap<Long, String> roleList;
    private HashMap<Long, String> statusList;
    private HashMap<Long, String> assigneeMap;

    private AccountModel currentAccount;

    private int currentStatus;
    private int currentUser;


    public TaskCardPendingAdapter(Context context, HashMap<Long, String> approveList, HashMap<Long, String> roleList, HashMap<Long, String> statusList) {
        this.approveList = approveList;
        this.roleList = roleList;
        this.statusList = statusList;
        this.assigneeMap = new HashMap<>();
        this.dataSet = new ArrayList<>();
        TaskModel temp = new TaskModel();
        temp.setType(TaskModel.SEARCH_CARD);
        dataSet.add(temp);
        this.mContext = context;
        currentAccount = PreferenceUtil.getAccountFromSharedPreferences(mContext);
        this.currentStatus = -1;
        this.currentUser = -1;
        getPendingTaskList(currentAccount.getRoleId(), null, null);
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

    public static class SearchCardHolder extends RecyclerView.ViewHolder {
        Button btnFrom, btnTo, btnSearch, btnReset;
        TextView searchTxtStatus;
        Spinner spinnerStatus, spinnerUser;


        public SearchCardHolder(@NonNull View itemView) {
            super(itemView);
            this.searchTxtStatus = itemView.findViewById(R.id.searchTxtStatus);
            this.btnFrom = itemView.findViewById(R.id.btnFrom);
            this.btnTo = itemView.findViewById(R.id.btnTo);
            this.btnSearch = itemView.findViewById(R.id.btnSearch);
            this.btnReset = itemView.findViewById(R.id.btnReset);
            this.spinnerStatus = itemView.findViewById(R.id.spinnerStatus);
            this.spinnerUser = itemView.findViewById(R.id.spinnerUser);
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TaskModel.TASK_CARD:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_task_fragment, parent, false);
                return new ShowCardTaskHolder(view);
            case TaskModel.SEARCH_CARD:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.form_search_fragment, parent, false);
                if (currentAccount.getRoleId().equals(Long.valueOf(0))) {
                    view.findViewById(R.id.spinnerUser).setVisibility(View.GONE);
                }
                return new SearchCardHolder(view);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final TaskModel object = dataSet.get(position);
        switch (object.type) {
            case TaskModel.SEARCH_CARD:
                final Button btnFrom = ((SearchCardHolder) holder).btnFrom;
                final Button btnTo = ((SearchCardHolder) holder).btnTo;
                final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                final Calendar newCalendar = Calendar.getInstance();
                final DatePickerDialog pickDateFrom = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        String date = dateFormat.format(newDate.getTime());
                        btnFrom.setText(date);
                    }
                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                btnFrom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pickDateFrom.show();
                    }
                });
                final DatePickerDialog pickDateTo = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        String date = dateFormat.format(newDate.getTime());
                        btnTo.setText(date);
                    }
                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                btnTo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pickDateTo.show();
                    }
                });
                HashMap<Long, String> temp = new HashMap<>();
                temp.put(Long.valueOf(-1), "None");
                temp.putAll(statusList);
                final Collection<String> statusValues = temp.values();
                ArrayList<String> listOfStatus = new ArrayList<String>(statusValues);
                ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, listOfStatus);
                ((SearchCardHolder) holder).spinnerStatus.setAdapter(statusAdapter);
                ((SearchCardHolder) holder).spinnerStatus.setSelection(listOfStatus.indexOf(temp.get(Long.valueOf(currentStatus))));
                ((SearchCardHolder) holder).spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String value = ((SearchCardHolder) holder).spinnerStatus.getItemAtPosition(position).toString();
                        if (!value.equals("None")) {
                            BiMap<Long, String> statusBiMap = HashBiMap.create(statusList);
                            currentStatus = statusBiMap.inverse().get(value).intValue();
                        } else {
                            currentStatus = -1;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                //user spinner
                //user spinner
                HashMap<Long, String> tempUser = new HashMap<>();
                tempUser.put(Long.valueOf(-1), "None");
                tempUser.putAll(assigneeMap);
                final Collection<String> userValues = tempUser.values();
                ArrayList<String> listOfUsers = new ArrayList<String>(userValues);
                ArrayAdapter<String> userAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, listOfUsers);
                ((SearchCardHolder) holder).spinnerUser.setAdapter(userAdapter);
                ((SearchCardHolder) holder).spinnerUser.setSelection(listOfUsers.indexOf(tempUser.get(Long.valueOf(currentUser))));
                ((SearchCardHolder) holder).spinnerUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String value = ((SearchCardHolder) holder).spinnerUser.getItemAtPosition(position).toString();
                        if (!value.equals("None")) {
                            BiMap<Long, String> userBimap = HashBiMap.create(assigneeMap);
                            currentUser = userBimap.inverse().get(value).intValue();
                        } else {
                            currentUser = -1;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                ((SearchCardHolder) holder).btnSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String from = null;
                        String to = null;
                        if (!((SearchCardHolder) holder).btnFrom.getText().toString().isEmpty()) {
                            from = ((SearchCardHolder) holder).btnFrom.getText().toString();
                        }
                        if (!((SearchCardHolder) holder).btnTo.getText().toString().isEmpty()) {
                            to = ((SearchCardHolder) holder).btnTo.getText().toString();
                        }
                        getPendingTaskList(currentAccount.getRoleId(), from, to);
                    }
                });
                ((SearchCardHolder) holder).btnReset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnFrom.setText("");
                        btnTo.setText("");
                        ((SearchCardHolder) holder).spinnerStatus.setSelection(0);
                    }
                });
                ((SearchCardHolder) holder).spinnerStatus.setVisibility(View.GONE);
                ((SearchCardHolder) holder).searchTxtStatus.setVisibility(View.GONE);
                break;
            case TaskModel.TASK_CARD:
                String splitDeadline = object.getDeadline().toString();
                ((ShowCardTaskHolder) holder).cardTask.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content_frame,
                                        new TaskDetailFragment
                                                (approveList,
                                                        roleList,
                                                        statusList,
                                                        dataSet.get(position).getTaskId(),
                                                        TaskDetailFragment.MODE_PENDING)).addToBackStack(null).commit();

                    }
                });
                ((ShowCardTaskHolder) holder).valueTaskName.setText(object.getTaskName());
                ((ShowCardTaskHolder) holder).valueAssignee.setText(assigneeMap.get(object.getAssignee()));
                ((ShowCardTaskHolder) holder).valueStatus.setText(statusList.get(object.getStatus()));
                ((ShowCardTaskHolder) holder).valueDeadline.setText(splitDeadline.substring(0, 19).replace("T", "\n"));
                break;
        }


    }

    @Override
    public int getItemCount() {
        return dataSet.size();

    }

    @Override
    public int getItemViewType(int position) {

        switch (dataSet.get(position).type) {
            case 0:
                return TaskModel.SEARCH_CARD;
            case 1:
                return TaskModel.TASK_CARD;
        }
        return 0;
    }

    public void getPendingTaskList(Long roleId, String from, String to) {
        RequestQueue requestQueue = SingletonRequestQueue.getInstance(mContext.getApplicationContext()).getRequestQueue();
        HashMap<String, String> headers = new HashMap<>();
        String url = "";
        switch (roleId.intValue()) {
            case ROLE_USER:
                if (from == null && to == null && currentStatus == -1) {
                    url = mContext.getResources().getString(R.string.BASE_URL) + "/task/getTaskListByFieldId?fieldName=assignee&value=" + currentAccount.getAccountId() + "&fieldName2=approve_id&value2=" + Long.valueOf(0) + "&isClosed=false";
                } else {
                    url = mContext.getResources().getString(R.string.BASE_URL) + "/task/getTaskListByFieldId?fieldName=assignee&value=" + currentAccount.getAccountId() + "&fieldName2=approve_id&value2=" + Long.valueOf(0) + "&isClosed=false" + (from != null ? "&from=" + from : "") + (to != null ? "&to=" + to : "");
                }
                break;
            case ROLE_MANAGER:
                if (from == null && to == null && currentStatus == -1) {
                    url = mContext.getResources().getString(R.string.BASE_URL) + "/task/getTaskListByFieldId?fieldName=group_id&value=" + currentAccount.getGroupId() + "&fieldName2=approve_id&value2=" + Long.valueOf(0) + "&isClosed=false";
                } else {
                    url = mContext.getResources().getString(R.string.BASE_URL) + "/task/getTaskListByFieldId?fieldName=group_id&value=" + currentAccount.getGroupId() + "&fieldName2=approve_id&value2=" + Long.valueOf(0) + "&isClosed=false" + (from != null ? "&from=" + from : "") + (to != null ? "&to=" + to : "");
                }
                break;
            case ROLE_ADMIN:
                if (from == null && to == null && currentStatus == -1) {
                    url = mContext.getResources().getString(R.string.BASE_URL) + "/task/getTaskListByFieldId?fieldName=approve_id&value=" + Long.valueOf(0) + "&isClosed=false";
                } else {
                    url = mContext.getResources().getString(R.string.BASE_URL) + "/task/getTaskListByFieldId?fieldName=approve_id&value=" + Long.valueOf(0) + "&isClosed=false" + (from != null ? "&from=" + from : "") + (to != null ? "&to=" + to : "");
                }
                break;
        }
        GsonRequest<TaskListResponse> gsonRequest = new GsonRequest<>(url, TaskListResponse.class, headers, new Response.Listener<TaskListResponse>() {
            @Override
            public void onResponse(TaskListResponse response) {
                List<TaskResponse> responses = new ArrayList<>();
                responses.addAll(response.getTaskList());
                List<TaskModel> taskModels = TimeUtil.convertTaskResponseToTask(responses);
                dataSet = new ArrayList<>();
                TaskModel temp = new TaskModel();
                temp.setType(TaskModel.SEARCH_CARD);
                dataSet.add(temp);
                for (TaskModel task : taskModels) {
                    task.setType(TaskModel.TASK_CARD);
                    dataSet.add(task);
                }
                if (currentUser != -1) {
                    for (int i = dataSet.size() - 1; i >= 0; i--) {
                        if (dataSet.get(i).getAssignee() != null) {
                            if (!dataSet.get(i).getAssignee().equals(Long.valueOf(currentUser))) {
                                dataSet.remove(i);
                            }
                        }
                    }
                }
                assigneeMap.putAll(response.getAssigneeList());
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
