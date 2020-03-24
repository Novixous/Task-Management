package app.com.taskmanagement.adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
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
import app.com.taskmanagement.model.request.TaskCreateRequest;
import app.com.taskmanagement.model.response.LoginResponse;
import app.com.taskmanagement.model.response.TaskResponse;
import app.com.taskmanagement.util.GsonRequest;
import app.com.taskmanagement.util.PreferenceUtil;
import app.com.taskmanagement.util.SingletonRequestQueue;
import app.com.taskmanagement.util.TimeUtil;

public class DetailTodoAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private Fragment fragment;
    public static final int ROLE_USER = 0;
    public static final int ROLE_MANAGER = 1;
    public static final int ROLE_ADMIN = 2;
    private int currentStatus;


    private static final Integer[] ID_NOT_SHOW_GENERAL = {
            R.id.btnClose,
            R.id.btnApprove,
            R.id.btnDecline,
            R.id.btnCreateTask,
            R.id.btnCloneTask,
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
    private static final Integer[] ID_NOT_EDITABLE_GENERAL = {
            R.id.valueTaskName,
            R.id.valueDescription,
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

    public DetailTodoAdapter(Context context, Fragment fragment, Long taskId, HashMap<Long, String> approveList, HashMap<Long, String> roleList, HashMap<Long, String> statusList) {
        this.mContext = context;
        this.fragment = fragment;
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
        TextView valueDateStart, valueDateEnd;
        ImageButton btnImg;
        ImageView valueImgResolution;
        EditText valueResult;
        TextView txtReviewer;
        TextView valueReviewer;
        TextView txtMark;
        NumberPicker valueMark;
        TextView txtDateReview;
        TextView valueDateReview;
        TextView txtReview;
        EditText valueReview;
        TextView valueModifiedBy, valueModifiedAt;
        View lineReviewer, lineConfirm, lineReview;

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

            this.valueDateStart = (TextView) itemView.findViewById(R.id.valueDateStart);
            this.valueDateEnd = (TextView) itemView.findViewById(R.id.valueDateEnd);
            this.btnImg = (ImageButton) itemView.findViewById(R.id.btnImg);
            this.valueImgResolution = (ImageView) itemView.findViewById(R.id.valueImgResolution);
            this.valueResult = (EditText) itemView.findViewById(R.id.valueResult);
            this.txtReviewer = (TextView) itemView.findViewById(R.id.txtReviewer);
            this.valueReviewer = (TextView) itemView.findViewById(R.id.valueReviewer);
            this.txtMark = (TextView) itemView.findViewById(R.id.txtMark);
            this.valueMark = (NumberPicker) itemView.findViewById(R.id.valueMark);
            this.txtDateReview = (TextView) itemView.findViewById(R.id.txtDateReview);
            this.valueDateReview = (TextView) itemView.findViewById(R.id.valueDateReview);
            this.txtReview = (TextView) itemView.findViewById(R.id.txtReview);
            this.valueReview = (EditText) itemView.findViewById(R.id.valueReview);
            this.valueModifiedBy = (TextView) itemView.findViewById(R.id.valueModifiedBy);
            this.valueModifiedAt = (TextView) itemView.findViewById(R.id.valueModifedAt);

            this.lineReviewer = (View) itemView.findViewById(R.id.lineReviewer);
            this.lineConfirm = (View) itemView.findViewById(R.id.lineConfirm);
            this.lineReview = (View) itemView.findViewById(R.id.lineReview);

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
        for (int i = 0; i < ID_NOT_EDITABLE_GENERAL.length; i++) {
            view.findViewById(ID_NOT_EDITABLE_GENERAL[i]).setEnabled(false);
        }
        switch (currentAccount.getRoleId().intValue()) {
            case ROLE_USER:

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
            //set deadline

            valueDeadline.setText(LocalDateTime.ofInstant(taskModel.getDeadline(), ZoneId.of("GMT")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            valueTimeDeadline.setText(LocalDateTime.ofInstant(taskModel.getDeadline(), ZoneId.of("GMT")).format(DateTimeFormatter.ofPattern("HH:mm")));
//                  Set status
            HashMap<Long, String> temp = new HashMap<>(statusList);

            if (taskModel.getStatus().intValue() == 0) {
                temp.remove(Long.valueOf(2));
                temp.remove(Long.valueOf(4));
            }
            if (taskModel.getStatus().intValue() == 1) {
                temp.remove(Long.valueOf(0));
                temp.remove(Long.valueOf(4));
            }
            if (taskModel.getStatus().intValue() == 4) {
                temp.remove(Long.valueOf(0));
                temp.remove(Long.valueOf(1));
            }
            final Collection<String> statusValues = temp.values();
            ArrayList<String> listOfStatus = new ArrayList<String>(statusValues);
            ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, listOfStatus);
            ((TaskFormHolder) holder).valueStatus.setAdapter(statusAdapter);
            ((TaskFormHolder) holder).valueStatus.setSelection(listOfStatus.indexOf(temp.get(taskModel.getStatus())));
            ((TaskFormHolder) holder).valueStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String value = ((TaskFormHolder) holder).valueStatus.getItemAtPosition(position).toString();
                    BiMap<Long, String> statusBiMap = HashBiMap.create(statusList);
                    currentStatus = statusBiMap.inverse().get(value).intValue();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            if (currentAccount.getRoleId() > 0 && !taskModel.getAssignee().equals(currentAccount.getAccountId())) {
                ((TaskFormHolder) holder).valueStatus.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
            }
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
            //                  Set start date
            ((TaskFormHolder) holder).valueDateStart.setText(taskModel.getStartTime() != null ? DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm").format(taskModel.getStartTime().atZone(ZoneId.of("GMT"))) : "");
            ((TaskFormHolder) holder).valueDateEnd.setText(taskModel.getEndTime() != null ? DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm").format(taskModel.getEndTime().atZone(ZoneId.of("GMT"))) : "");

            //                  Set select img listener
            if (currentAccount.getRoleId() > 0 && !taskModel.getAssignee().equals(currentAccount.getAccountId())) {
                ((TaskFormHolder) holder).btnImg.setEnabled(false);
            }
            if (!taskModel.getStatus().equals(Long.valueOf(0))) {
                ((TaskFormHolder) holder).btnImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ActivityCompat.checkSelfPermission(fragment.getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            fragment.requestPermissions(
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    2000);
                        } else {
                            startGallery();
                        }
                    }
                });
            }
            //                  Set image to resolution image
            if (imageResolution != null) {
                ((TaskFormHolder) holder).valueImgResolution.setImageBitmap(imageResolution);
            }
            //                  Result
            if (currentAccount.getRoleId() > 0 && !taskModel.getAssignee().equals(currentAccount.getAccountId())) {
                ((TaskFormHolder) holder).valueResult.setEnabled(false);
            }
            if (taskModel.getStatus().equals(Long.valueOf(4))) {
                ((TaskFormHolder) holder).txtReviewer.setVisibility(View.VISIBLE);
                ((TaskFormHolder) holder).valueReviewer.setVisibility(View.VISIBLE);
                ((TaskFormHolder) holder).lineReviewer.setVisibility(View.VISIBLE);
                ((TaskFormHolder) holder).txtMark.setVisibility(View.VISIBLE);
                ((TaskFormHolder) holder).valueMark.setVisibility(View.VISIBLE);
                ((TaskFormHolder) holder).valueMark.setEnabled(false);
                ((TaskFormHolder) holder).txtDateReview.setVisibility(View.VISIBLE);
                ((TaskFormHolder) holder).valueDateReview.setVisibility(View.VISIBLE);
                ((TaskFormHolder) holder).lineConfirm.setVisibility(View.VISIBLE);
                ((TaskFormHolder) holder).txtReview.setVisibility(View.VISIBLE);
                ((TaskFormHolder) holder).valueReview.setVisibility(View.VISIBLE);
                ((TaskFormHolder) holder).valueReview.setEnabled(false);
                ((TaskFormHolder) holder).lineReview.setVisibility(View.VISIBLE);
                //                  Reviewer
                ((TaskFormHolder) holder).valueReviewer.setText(reviewer != null ? reviewer.getFullName() : "");
                //                  Mark

                ((TaskFormHolder) holder).valueMark.setMinValue(0);
                ((TaskFormHolder) holder).valueMark.setMaxValue(10);
                if (taskModel.getMark() != null) {
                    ((TaskFormHolder) holder).valueMark.setValue(taskModel.getMark().intValue());
                }
                //                  Date reviewed
                ((TaskFormHolder) holder).valueDateReview.setText(taskModel.getReviewTime() != null ? DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm").format(taskModel.getReviewTime().atZone(ZoneId.of("GMT"))) : "");
                //                  Review content
                ((TaskFormHolder) holder).valueReview.setText(taskModel.getManagerComment() != null ? taskModel.getManagerComment() : "");
            }
            if (taskModel.getStatus().equals(Long.valueOf(0))) {
                ((TaskFormHolder) holder).valueResult.setEnabled(false);
            } else {
                ((TaskFormHolder) holder).valueResult.setText(taskModel.getResult() != null ? taskModel.getResult() : "");
            }
            if (currentAccount.getRoleId() > 0 && !taskModel.getAssignee().equals(currentAccount.getAccountId())) {
                ((TaskFormHolder) holder).btnUpdate.setVisibility(View.GONE);
            }
            //                  last modified
            ((TaskFormHolder) holder).valueModifiedBy.setText(lastModifiedName);
            ((TaskFormHolder) holder).valueModifiedAt.setText(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm").format(taskModel.getEditedAt().atZone(ZoneId.of("GMT"))));

            ((TaskFormHolder) holder).btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TaskModel taskUpdate = new TaskModel();
                    taskUpdate.setTaskId(taskModel.getTaskId());
                    taskUpdate.setStatus(Long.valueOf(currentStatus));
                    taskUpdate.setResult(((TaskFormHolder) holder).valueResult.getText().toString());
                    taskUpdate.setEditedBy(currentAccount.getAccountId());
                    if (imageResolution != null) {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        imageResolution.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        taskUpdate.setImgResolutionUrl(encoded);
                    }
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
                if (taskModel.getStatus().equals(Long.valueOf(4))) {
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

    private void startGallery() {
        Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        cameraIntent.setType("image/*");
        if (cameraIntent.resolveActivity(fragment.getActivity().getPackageManager()) != null) {
            fragment.startActivityForResult(cameraIntent, 1000);
        }
    }
}
