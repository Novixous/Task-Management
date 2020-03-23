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
import app.com.taskmanagement.model.response.TaskList;
import app.com.taskmanagement.model.response.TaskResponse;
import app.com.taskmanagement.util.GsonRequest;
import app.com.taskmanagement.util.PreferenceUtil;
import app.com.taskmanagement.util.SingletonRequestQueue;
import app.com.taskmanagement.util.TimeUtil;

public class CardTaskClosedAdapter extends RecyclerView.Adapter {


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

    public CardTaskClosedAdapter(Context context, HashMap<Long, String> approveList, HashMap<Long, String> roleList, HashMap<Long, String> statusList) {
        this.approveList = approveList;
        this.roleList = roleList;
        this.statusList = statusList;
        this.assigneeMap = new HashMap<>();
        this.dataSet = new ArrayList<>();
        this.mContext = context;
        currentAccount = PreferenceUtil.getAccountFromSharedPreferences(mContext);
        this.currentStatus = -1;
        getFinishedTaskList(currentAccount.getRoleId(), null, null);
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
        Spinner spinnerStatus;


        public SearchCardHolder(@NonNull View itemView) {
            super(itemView);
            this.btnFrom = itemView.findViewById(R.id.btnFrom);
            this.btnTo = itemView.findViewById(R.id.btnTo);
            this.btnSearch = itemView.findViewById(R.id.btnSearch);
            this.btnReset = itemView.findViewById(R.id.btnReset);
            this.spinnerStatus = itemView.findViewById(R.id.spinnerStatus);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TaskModel.TASK_CARD:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_show_card_task, parent, false);
                return new CardTaskFinishedAdapter.ShowCardTaskHolder(view);
            case TaskModel.SEARCH_CARD:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_card, parent, false);
                return new CardTaskFinishedAdapter.SearchCardHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final TaskModel object = dataSet.get(position);
        switch (object.type) {
            case TaskModel.SEARCH_CARD:
                final Button btnFrom = ((CardTaskFinishedAdapter.SearchCardHolder) holder).btnFrom;
                final Button btnTo = ((CardTaskFinishedAdapter.SearchCardHolder) holder).btnTo;
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
                ((CardTaskFinishedAdapter.SearchCardHolder) holder).spinnerStatus.setAdapter(statusAdapter);
                ((CardTaskFinishedAdapter.SearchCardHolder) holder).spinnerStatus.setSelection(listOfStatus.indexOf(temp.get(Long.valueOf(currentStatus))));
                ((CardTaskFinishedAdapter.SearchCardHolder) holder).spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String value = ((CardTaskFinishedAdapter.SearchCardHolder) holder).spinnerStatus.getItemAtPosition(position).toString();
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
                ((CardTaskFinishedAdapter.SearchCardHolder) holder).btnSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String from = null;
                        String to = null;
                        if (!((CardTaskFinishedAdapter.SearchCardHolder) holder).btnFrom.getText().toString().isEmpty()) {
                            from = ((CardTaskFinishedAdapter.SearchCardHolder) holder).btnFrom.getText().toString();
                        }
                        if (!((CardTaskFinishedAdapter.SearchCardHolder) holder).btnTo.getText().toString().isEmpty()) {
                            to = ((CardTaskFinishedAdapter.SearchCardHolder) holder).btnTo.getText().toString();
                        }
                        getFinishedTaskList(currentAccount.getRoleId(), from, to);
                    }
                });
                ((CardTaskFinishedAdapter.SearchCardHolder) holder).btnReset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnFrom.setText("");
                        btnTo.setText("");
                        ((CardTaskFinishedAdapter.SearchCardHolder) holder).spinnerStatus.setSelection(0);
                    }
                });
                break;
            case TaskModel.TASK_CARD:
                String splitDeadline = object.getDeadline().toString();
                ((CardTaskFinishedAdapter.ShowCardTaskHolder) holder).cardTask.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content_frame,
                                        new TaskDetailFragment
                                                (approveList,
                                                        roleList,
                                                        statusList,
                                                        dataSet.get(position).getTaskId(),
                                                        TaskDetailFragment.MODE_CLOSED)).addToBackStack(null).commit();

                    }
                });
                ((CardTaskFinishedAdapter.ShowCardTaskHolder) holder).valueTaskName.setText(object.getTaskName());
                ((CardTaskFinishedAdapter.ShowCardTaskHolder) holder).valueAssignee.setText(assigneeMap.get(object.getAssignee()));
                ((CardTaskFinishedAdapter.ShowCardTaskHolder) holder).valueStatus.setText(statusList.get(object.getStatus()));
                ((CardTaskFinishedAdapter.ShowCardTaskHolder) holder).valueDeadline.setText(splitDeadline.substring(0, 19).replace("T", "\n"));
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

    public void getFinishedTaskList(Long roleId, String from, String to) {
        RequestQueue requestQueue = SingletonRequestQueue.getInstance(mContext.getApplicationContext()).getRequestQueue();
        HashMap<String, String> headers = new HashMap<>();
        String url = "";
        switch (roleId.intValue()) {
            case ROLE_USER:
                if (from == null && to == null && currentStatus == -1) {
                    url = mContext.getResources().getString(R.string.BASE_URL) + "/task/getTaskListByFieldId?fieldName=assignee&value=" + currentAccount.getAccountId() + "&isClosed=true";
                } else if (currentStatus == -1) {
                    url = mContext.getResources().getString(R.string.BASE_URL) + "/task/getTaskListByFieldId?fieldName=assignee&value=" + currentAccount.getAccountId() + "&isClosed=true" + (from != null ? "&from=" + from : "") + (to != null ? "&to=" + to : "");
                } else {
                    url = mContext.getResources().getString(R.string.BASE_URL) + "/task/getTaskListByFieldId?fieldName=assignee&value=" + currentAccount.getAccountId() + "&isClosed=true" + "&fieldName2=status_id&value2=" + Long.valueOf(currentStatus) + (from != null ? "&from=" + from : "") + (to != null ? "&to=" + to : "");
                }
                break;
            case ROLE_MANAGER:
                if (from == null && to == null && currentStatus == -1) {
                    url = mContext.getResources().getString(R.string.BASE_URL) + "/task/getTaskListByFieldId?fieldName=group_id&value=" + currentAccount.getGroupId() + "&isClosed=true";
                } else if (currentStatus == -1) {
                    url = mContext.getResources().getString(R.string.BASE_URL) + "/task/getTaskListByFieldId?fieldName=group_id&value=" + currentAccount.getGroupId() + "&isClosed=true" + (from != null ? "&from=" + from : "") + (to != null ? "&to=" + to : "");
                } else {
                    url = mContext.getResources().getString(R.string.BASE_URL) + "/task/getTaskListByFieldId?fieldName=group_id&value=" + currentAccount.getGroupId() + "&isClosed=true" + "&fieldName2=status_id&value2=" + Long.valueOf(currentStatus) + (from != null ? "&from=" + from : "") + (to != null ? "&to=" + to : "");
                }
                break;
            case ROLE_ADMIN:
                if (from == null && to == null && currentStatus == -1) {
                    url = mContext.getResources().getString(R.string.BASE_URL) + "/task/getTaskListByFieldId?splitClosed=&isClosed=true";
                } else if (currentStatus == -1) {
                    url = mContext.getResources().getString(R.string.BASE_URL) + "/task/getTaskListByFieldId?splitClosed=&isClosed=true" + (from != null ? "&from=" + from : "") + (to != null ? "&to=" + to : "");
                } else {
                    url = mContext.getResources().getString(R.string.BASE_URL) + "/task/getTaskListByFieldId?fieldName=status_id&value=" + Long.valueOf(currentStatus) + "&isClosed=true" + (from != null ? "&from=" + from : "") + (to != null ? "&to=" + to : "");
                }
                break;
        }

        GsonRequest<TaskList> gsonRequest = new GsonRequest<>(url, TaskList.class, headers, new Response.Listener<TaskList>() {
            @Override
            public void onResponse(TaskList response) {
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