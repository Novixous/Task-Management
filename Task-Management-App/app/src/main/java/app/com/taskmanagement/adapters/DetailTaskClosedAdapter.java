package app.com.taskmanagement.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.Gson;
import com.warkiz.widget.IndicatorSeekBar;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
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

public class DetailTaskClosedAdapter extends RecyclerView.Adapter {
    private Context mContext;
    public static final int ROLE_USER = 0;
    public static final int ROLE_MANAGER = 1;
    public static final int ROLE_ADMIN = 2;
    private int currentStatus;


    private static final Integer[] ID_NOT_SHOW_GENERAL = {
            R.id.btnCreateTask,
            R.id.btnClose,
            R.id.btnUpdateTask,
            R.id.btnCloneTask,
            R.id.btnApprove,
            R.id.btnDecline
    };
    private static final Integer[] ID_NOT_EDITABLE_GENERAL = {
            R.id.valueTaskName,
            R.id.valueDescription,
            R.id.btnImg,
            R.id.valueResult,
            R.id.valueReview
    };


    private TaskModel taskModel;
    private AccountModel currentAccount;

    private AccountModel creator;
    private AccountModel assignee;
    private AccountModel reviewer;
    private String lastModifiedName;
    private HashMap<Long, String> approveList;
    private HashMap<Long, String> roleList;
    private HashMap<Long, String> statusList;
    private Boolean dataLoaded;
    private Bitmap imageResolution;

    public void setImageResolution(Bitmap imageResolution) {
        this.imageResolution = imageResolution;
    }

    public DetailTaskClosedAdapter(Context context, Long taskId, HashMap<Long, String> approveList, HashMap<Long, String> roleList, HashMap<Long, String> statusList) {
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
        Button valueStatus;
        TextView valueNote, valueCreator;
        Button valueAssignee;
        EditText valueDescription;
        TextView valueDateStart, valueDateEnd;
        ImageButton btnImg;
        ImageView valueImgResolution;
        EditText valueResult;
        TextView valueReviewer;
        IndicatorSeekBar valueMark;
        TextView valueDateReview;
        EditText valueReview;

        TextView valueModifiedBy, valueModifiedAt;


        Button btnUpdate, btnApprove, btnDecline, btnClose, btnClone;

        public TaskFormHolder(@NonNull View itemView) {
            super(itemView);

            this.valueTaskName = (EditText) itemView.findViewById(R.id.valueTaskName);
            this.valueIDTask = (TextView) itemView.findViewById(R.id.valueIDTask);
            this.valueOldID = (TextView) itemView.findViewById(R.id.valueIDOldTask);
            this.valueDateDeadline = (Button) itemView.findViewById(R.id.valueDateDeadline);
            this.valueTimeDeadline = (Button) itemView.findViewById(R.id.valueTimeDeadline);
            this.valueStatus = (Button) itemView.findViewById(R.id.valueStatus);
            this.valueNote = (TextView) itemView.findViewById(R.id.valueNote);
            this.valueCreator = (TextView) itemView.findViewById(R.id.valueCreator);
            this.valueAssignee = (Button) itemView.findViewById(R.id.valueAssignee);
            this.valueDescription = (EditText) itemView.findViewById(R.id.valueDescription);

            this.valueDateStart = (TextView) itemView.findViewById(R.id.valueDateStart);
            this.valueDateEnd = (TextView) itemView.findViewById(R.id.valueDateEnd);
            this.btnImg = (ImageButton) itemView.findViewById(R.id.btnImg);
            this.valueImgResolution = (ImageView) itemView.findViewById(R.id.valueImgResolution);
            this.valueResult = (EditText) itemView.findViewById(R.id.valueResult);
            this.valueReviewer = (TextView) itemView.findViewById(R.id.valueReviewer);
            this.valueMark = (IndicatorSeekBar) itemView.findViewById(R.id.valueMark);
            this.valueDateReview = (TextView) itemView.findViewById(R.id.valueDateReview);
            this.valueReview = (EditText) itemView.findViewById(R.id.valueReview);
            this.valueModifiedBy = (TextView) itemView.findViewById(R.id.valueModifiedBy);
            this.valueModifiedAt = (TextView) itemView.findViewById(R.id.valueModifedAt);

            this.btnUpdate = (Button) itemView.findViewById(R.id.btnUpdateTask);
            this.btnApprove = (Button) itemView.findViewById(R.id.btnApprove);
            this.btnDecline = (Button) itemView.findViewById(R.id.btnDecline);

            this.btnClose = (Button) itemView.findViewById(R.id.btnClose);
            this.btnClone = (Button) itemView.findViewById(R.id.btnCloneTask);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_show_task, parent, false);
        for (int i = 0; i < ID_NOT_SHOW_GENERAL.length; i++) {
            view.findViewById(ID_NOT_SHOW_GENERAL[i]).setVisibility(View.GONE);
        }
        for (int i = 0; i < ID_NOT_EDITABLE_GENERAL.length; i++) {
            view.findViewById(ID_NOT_EDITABLE_GENERAL[i]).setEnabled(false);
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
            //set deadline
            valueDeadline.setText(LocalDateTime.ofInstant(taskModel.getDeadline(), ZoneId.of("GMT")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            valueTimeDeadline.setText(LocalDateTime.ofInstant(taskModel.getDeadline(), ZoneId.of("GMT")).format(DateTimeFormatter.ofPattern("HH:mm")));
//                  Set status
            HashMap<Long, String> temp = new HashMap<>(statusList);
            final Collection<String> statusValues = temp.values();
            ArrayList<String> listOfStatus = new ArrayList<String>(statusValues);
            ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, listOfStatus);
            final String[] dialogStatusItems = listOfStatus.toArray(new String[0]);
            int checkedItem = listOfStatus.indexOf(statusList.get(taskModel.getStatus()));
            ((TaskFormHolder) holder).valueStatus.setText(dialogStatusItems[checkedItem]);
            String value = dialogStatusItems[checkedItem];
            BiMap<Long, String> statusBiMap = HashBiMap.create(statusList);
            currentStatus = statusBiMap.inverse().get(value).intValue();
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
            final String[] dialogAssigneeItems = assigneeSpinnerItems.toArray(new String[0]);
            ((TaskFormHolder) holder).valueAssignee.setText(dialogAssigneeItems[0]);
//                  Set Creator
            ((TaskFormHolder) holder).valueCreator.setText(creator.getFullName());
            //                  Set Description
            ((TaskFormHolder) holder).valueDescription.setText(taskModel.getDescription());
            //                  Set start date
            ((TaskFormHolder) holder).valueDateStart.setText(taskModel.getStartTime() != null ? DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(taskModel.getStartTime().atZone(ZoneId.of("GMT"))) : "");
            ((TaskFormHolder) holder).valueDateEnd.setText(taskModel.getEndTime() != null ? DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(taskModel.getEndTime().atZone(ZoneId.of("GMT"))) : "");

            //                  Set image to resolution image
            if (imageResolution != null) {
                ((TaskFormHolder) holder).valueImgResolution.setImageBitmap(imageResolution);
            }
            //                  Result
            ((TaskFormHolder) holder).valueResult.setText(taskModel.getResult());
            //                  Reviewer
            ((TaskFormHolder) holder).valueReviewer.setText(reviewer != null ? reviewer.getFullName() : "");
            //                  Mark

            if (taskModel.getMark() != null) {
                ((TaskFormHolder) holder).valueMark.setProgress(taskModel.getMark().intValue());
            }
            ((TaskFormHolder) holder).valueMark.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
            //                  Date reviewed
            ((TaskFormHolder) holder).valueDateReview.setText(taskModel.getReviewTime() != null ? DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(taskModel.getReviewTime().atZone(ZoneId.of("GMT"))) : "");
            //                  Review content
            ((TaskFormHolder) holder).valueReview.setText(taskModel.getManagerComment() != null ? taskModel.getManagerComment() : "");
            //                  last modified
            ((TaskFormHolder) holder).valueModifiedBy.setText(lastModifiedName);
            ((TaskFormHolder) holder).valueModifiedAt.setText(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(taskModel.getEditedAt().atZone(ZoneId.of("GMT"))));

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
                if (taskModel.getImgResolutionUrl() != null) {
                    byte[] decodedString = Base64.decode(taskModel.getImgResolutionUrl(), Base64.DEFAULT);
                    imageResolution = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                }
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
                if (taskModel.getReviewerId() != null) {
                    getReviewerById(taskModel.getReviewerId());
                } else {
                    dataLoaded = true;
                    notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Connection Time Out", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(gsonRequest);
    }

    public void getReviewerById(Long accountId) {
        RequestQueue requestQueue = SingletonRequestQueue.getInstance(mContext.getApplicationContext()).getRequestQueue();
        HashMap<String, String> headers = new HashMap<>();
        String url = mContext.getResources().getString(R.string.BASE_URL) + "/account?id=" + accountId;
        GsonRequest<LoginResponse> gsonRequest = new GsonRequest<>(url, LoginResponse.class, headers, new Response.Listener<LoginResponse>() {
            @Override
            public void onResponse(LoginResponse response) {
                reviewer = response.account;
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

}
