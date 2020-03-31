package app.com.taskmanagement.adapters;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
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

public class CardTaskTodoAdapter extends RecyclerView.Adapter {
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


    public CardTaskTodoAdapter(Context context, HashMap<Long, String> approveList, HashMap<Long, String> roleList, HashMap<Long, String> statusList) {
        this.approveList = approveList;
        this.roleList = roleList;
        this.statusList = statusList;
        this.assigneeMap = new HashMap<>();
        this.dataSet = new ArrayList<>();
        this.mContext = context;
        currentAccount = PreferenceUtil.getAccountFromSharedPreferences(mContext);
        this.currentStatus = -1;
        this.currentUser = -1;
        getTodoTaskList(currentAccount.getRoleId(), null, null);
    }

    public static class ShowCardTaskHolder extends RecyclerView.ViewHolder {
        TextView valueId, valueTaskName, valueAssignee, valueStatus, valueDeadline;
        LinearLayout cardTask;

        public ShowCardTaskHolder(@NonNull View itemView) {
            super(itemView);
            this.valueId = itemView.findViewById(R.id.valueId);
            this.cardTask = (LinearLayout) itemView.findViewById(R.id.card_task);
            this.valueTaskName = (TextView) itemView.findViewById(R.id.valuetxtTaskName);
            this.valueAssignee = (TextView) itemView.findViewById(R.id.valuetxtAssignee);
            this.valueStatus = (TextView) itemView.findViewById(R.id.valuetxtStatus);
            this.valueDeadline = (TextView) itemView.findViewById(R.id.valuetxtDeadline);
        }
    }

    public static class SearchCardHolder extends RecyclerView.ViewHolder {
        Button btnFrom, btnTo, btnSearch, btnReset;
        Button valueStatus;
        Button valueUser;
        Button expandableSearchButton;
        ExpandableLinearLayout expandableLinearLayout;

        public SearchCardHolder(@NonNull View itemView) {
            super(itemView);
            this.btnFrom = itemView.findViewById(R.id.btnFrom);
            this.btnTo = itemView.findViewById(R.id.btnTo);
            this.btnSearch = itemView.findViewById(R.id.btnSearch);
            this.btnReset = itemView.findViewById(R.id.btnReset);
            this.valueStatus = itemView.findViewById(R.id.valueStatus);
            this.valueUser = itemView.findViewById(R.id.valueUser);
            this.expandableSearchButton = itemView.findViewById(R.id.expandableSearchButton);
            this.expandableLinearLayout = itemView.findViewById(R.id.expandableLayout);
            this.expandableSearchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    expandableLinearLayout.toggle();
                    if (expandableLinearLayout.isExpanded()) {
                        expandableSearchButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.arrow_down_float, 0);
                    } else {
                        expandableSearchButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.arrow_up_float, 0);
                    }
                }
            });
        }
    }


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
                    view.findViewById(R.id.valueUser).setVisibility(View.GONE);
                    view.findViewById(R.id.searchTxtUser).setVisibility(View.GONE);
                }
                return new SearchCardHolder(view);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        final TaskModel object = dataSet.get(position);
        switch (object.type) {
            case TaskModel.SEARCH_CARD:
                if(currentAccount.getRoleId().intValue() == 0){
                    ExpandableLinearLayout.LayoutParams layoutParams = (ExpandableLinearLayout.LayoutParams) ((SearchCardHolder) holder).expandableLinearLayout.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, -500);
                    ((SearchCardHolder) holder).expandableLinearLayout.setLayoutParams(layoutParams);
                }
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
                        pickDateFrom.getDatePicker().setMinDate(System.currentTimeMillis());
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
                        pickDateTo.getDatePicker().setMinDate(System.currentTimeMillis());
                    }
                });
                HashMap<Long, String> temp = new HashMap<>();
                temp.put(Long.valueOf(-1), "None");
                temp.putAll(statusList);
                temp.remove(Long.valueOf(2));
                temp.remove(Long.valueOf(3));
                final Collection<String> statusValues = temp.values();
                ArrayList<String> listOfStatus = new ArrayList<String>(statusValues);
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Choose status");
                // add a radio button list
                final String[] dialogStatusItems = listOfStatus.toArray(new String[0]);


                int checkedItem = listOfStatus.indexOf(temp.get(Long.valueOf(currentStatus)));
                ((SearchCardHolder) holder).valueStatus.setText(dialogStatusItems[checkedItem]);
                builder.setSingleChoiceItems(dialogStatusItems, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String value = dialogStatusItems[which];
                        if (!value.equals("None")) {
                            BiMap<Long, String> statusBiMap = HashBiMap.create(statusList);
                            currentStatus = statusBiMap.inverse().get(value).intValue();
                        } else {
                            currentStatus = -1;
                        }
                        ((SearchCardHolder) holder).valueStatus.setText(value);
                    }
                });
                // add OK and Cancel buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("Cancel", null);

                ((SearchCardHolder) holder).valueStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // create and show the alert dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });


                //user spinner
                HashMap<Long, String> tempUser = new HashMap<>();
                tempUser.put(Long.valueOf(-1), "None");
                tempUser.putAll(assigneeMap);
                final Collection<String> userValues = tempUser.values();
                ArrayList<String> listOfUsers = new ArrayList<String>(userValues);
                final AlertDialog.Builder userBuilder = new AlertDialog.Builder(mContext);
                userBuilder.setTitle("Choose assignee");
                final String[] dialogAssigneeItems = listOfUsers.toArray(new String[0]);

                int userCheckedItem = listOfUsers.indexOf(tempUser.get(Long.valueOf(currentUser)));
                ((SearchCardHolder) holder).valueUser.setText(dialogAssigneeItems[userCheckedItem]);
                userBuilder.setSingleChoiceItems(dialogAssigneeItems, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String value = dialogAssigneeItems[which];
                        if (!value.equals("None")) {
                            BiMap<Long, String> assigneeBimap = HashBiMap.create(assigneeMap);
                            currentUser = assigneeBimap.inverse().get(value).intValue();
                        } else {
                            currentUser = -1;
                        }
                        ((SearchCardHolder) holder).valueUser.setText(value);
                    }
                });

                // add OK and Cancel buttons
                userBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                userBuilder.setNegativeButton("Cancel", null);

                ((SearchCardHolder) holder).valueUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // create and show the alert dialog
                        AlertDialog dialog = userBuilder.create();
                        dialog.show();
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
                        getTodoTaskList(currentAccount.getRoleId(), from, to);
                    }
                });
                ((SearchCardHolder) holder).btnReset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnFrom.setText("");
                        btnTo.setText("");
                        ((SearchCardHolder) holder).valueStatus.setText("None");
                        currentStatus = -1;
                        ((SearchCardHolder) holder).valueUser.setText("None");
                        currentUser = -1;
                    }
                });
                ((SearchCardHolder)holder).expandableLinearLayout.initLayout();
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
                                                        TaskDetailFragment.MODE_TODO)).addToBackStack(null).commit();

                    }
                });
                ((ShowCardTaskHolder) holder).valueId.setText(object.getTaskId().toString());
                ((ShowCardTaskHolder) holder).valueTaskName.setText(object.getTaskName());
                ((ShowCardTaskHolder) holder).valueAssignee.setText(assigneeMap.get(object.getAssignee()));
                ((ShowCardTaskHolder) holder).valueStatus.setText(statusList.get(object.getStatus()));
                ((ShowCardTaskHolder) holder).valueDeadline.setText(splitDeadline.substring(0, 19).replace("T", " "));
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

    public void getTodoTaskList(Long roleId, String from, String to) {
        RequestQueue requestQueue = SingletonRequestQueue.getInstance(mContext.getApplicationContext()).getRequestQueue();
        HashMap<String, String> headers = new HashMap<>();
        String url = "";
        switch (roleId.intValue()) {
            case ROLE_USER:
                if (from == null && to == null && currentStatus == -1) {
                    url = mContext.getResources().getString(R.string.BASE_URL) + "/task/getTaskListByFieldId?fieldName=assignee&value=" + currentAccount.getAccountId() + "&fieldName2=approve_id&value2=" + Long.valueOf(1) + "&split3=and(&fieldName3=status_id&value3=" + Long.valueOf(0) + "&split4=or&fieldName4=status_id&value4=" + Long.valueOf(1) + "&split5=or&fieldName5=status_id&value5=" + Long.valueOf(4) + "&splitClosed=)and&isClosed=false";
                } else if (currentStatus == -1) {
                    url = mContext.getResources().getString(R.string.BASE_URL) + "/task/getTaskListByFieldId?fieldName=assignee&value=" + currentAccount.getAccountId() + "&fieldName2=approve_id&value2=" + Long.valueOf(1) + "&split3=and(&fieldName3=status_id&value3=" + Long.valueOf(0) + "&split4=or&fieldName4=status_id&value4=" + Long.valueOf(1) + "&split5=or&fieldName5=status_id&value5=" + Long.valueOf(4) + "&splitClosed=)and&isClosed=false" + (from != null ? "&from=" + from : "") + (to != null ? "&to=" + to : "");
                } else {
                    url = mContext.getResources().getString(R.string.BASE_URL) + "/task/getTaskListByFieldId?fieldName=assignee&value=" + currentAccount.getAccountId() + "&fieldName2=approve_id&value2=" + Long.valueOf(1) + "&fieldName3=status_id&value3=" + Long.valueOf(currentStatus) + "&isClosed=false" + (from != null ? "&from=" + from : "") + (to != null ? "&to=" + to : "");
                }
                break;
            case ROLE_MANAGER:
                if (from == null && to == null && currentStatus == -1) {
                    url = mContext.getResources().getString(R.string.BASE_URL) + "/task/getTaskListByFieldId?fieldName=group_id&value=" + currentAccount.getGroupId() + "&fieldName2=approve_id&value2=" + Long.valueOf(1) + "&split3=and(&fieldName3=status_id&value3=" + Long.valueOf(0) + "&split4=or&fieldName4=status_id&value4=" + Long.valueOf(1) + "&split5=or&fieldName5=status_id&value5=" + Long.valueOf(4) + "&splitClosed=)and&isClosed=false";
                } else if (currentStatus == -1) {
                    url = mContext.getResources().getString(R.string.BASE_URL) + "/task/getTaskListByFieldId?fieldName=group_id&value=" + currentAccount.getGroupId() + "&fieldName2=approve_id&value2=" + Long.valueOf(1) + "&split3=and(&fieldName3=status_id&value3=" + Long.valueOf(0) + "&split4=or&fieldName4=status_id&value4=" + Long.valueOf(1) + "&split5=or&fieldName5=status_id&value5=" + Long.valueOf(4) + "&splitClosed=)and&isClosed=false" + (from != null ? "&from=" + from : "") + (to != null ? "&to=" + to : "");
                } else {
                    url = mContext.getResources().getString(R.string.BASE_URL) + "/task/getTaskListByFieldId?fieldName=group_id&value=" + currentAccount.getGroupId() + "&fieldName2=approve_id&value2=" + Long.valueOf(1) + "&fieldName3=status_id&value3=" + Long.valueOf(currentStatus) + "&isClosed=false" + (from != null ? "&from=" + from : "") + (to != null ? "&to=" + to : "");
                }
                break;
            case ROLE_ADMIN:
                if (from == null && to == null && currentStatus == -1) {
                    url = mContext.getResources().getString(R.string.BASE_URL) + "/task/getTaskListByFieldId?fieldName=approve_id&value=" + Long.valueOf(1) + "&split2=and(&fieldName2=status_id&value2=" + Long.valueOf(0) + "&split3=or&fieldName3=status_id&value3=" + Long.valueOf(1) + "&split4=or&fieldName4=status_id&value4=" + Long.valueOf(4) + "&splitClosed=)and&isClosed=false";
                } else if (currentStatus == -1) {
                    url = mContext.getResources().getString(R.string.BASE_URL) + "/task/getTaskListByFieldId?fieldName=approve_id&value=" + Long.valueOf(1) + "&split2=and(&fieldName2=status_id&value2=" + Long.valueOf(0) + "&split3=or&fieldName3=status_id&value3=" + Long.valueOf(1) + "&split4=or&fieldName4=status_id&value4=" + Long.valueOf(4) + "&splitClosed=)and&isClosed=false" + (from != null ? "&from=" + from : "") + (to != null ? "&to=" + to : "");
                } else {
                    url = mContext.getResources().getString(R.string.BASE_URL) + "/task/getTaskListByFieldId?fieldName=approve_id&value=" + Long.valueOf(1) + "&fieldName2=status_id&value2=" + Long.valueOf(currentStatus) + "&isClosed=false" + (from != null ? "&from=" + from : "") + (to != null ? "&to=" + to : "");
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
