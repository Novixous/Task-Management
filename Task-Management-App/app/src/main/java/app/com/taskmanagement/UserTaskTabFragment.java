package app.com.taskmanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.NetworkError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.HashMap;

import app.com.taskmanagement.adapters.CardTaskFinishedAdapter;
import app.com.taskmanagement.adapters.CardTaskPendingAdapter;
import app.com.taskmanagement.adapters.CardTaskTodoAdapter;
import app.com.taskmanagement.model.TaskModel;
import app.com.taskmanagement.model.response.TaskList;
import app.com.taskmanagement.model.response.TaskResponse;
import app.com.taskmanagement.util.GsonRequest;
import app.com.taskmanagement.util.PreferenceUtil;
import app.com.taskmanagement.util.SingletonRequestQueue;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserTaskTabFragment extends Fragment {

    public static final String ARG_OBJECT = "object";
    public String tabTitle;
    public ArrayList<TaskResponse> taskList = new ArrayList<>();
    HashMap<Long, String> approveList = new HashMap<>();
    HashMap<Long, String> roleList = new HashMap<>();
    HashMap<Long, String> statusList = new HashMap<>();
    private RecyclerView recyclerView;

    public UserTaskTabFragment(String tabTitle, HashMap<Long, String> approveList, HashMap<Long, String> roleList, HashMap<Long, String> statusList) {
        this.tabTitle = tabTitle;
        this.approveList = approveList;
        this.roleList = roleList;
        this.statusList = statusList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_task_tab, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
//        ((TextView) view.findViewById(R.id.text1))
//                .setText(Integer.toString(args.getInt(ARG_OBJECT)));
        TaskModel gridViewModel;
        RecyclerView.Adapter cardTaskAdapter;
        StaggeredGridLayoutManager lm;
        switch (tabTitle) {
            case "Todo":
                cardTaskAdapter = new CardTaskTodoAdapter(this.getActivity(), approveList, roleList, statusList);
                recyclerView = view.findViewById(R.id.tabShowCard);
                lm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(lm);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(cardTaskAdapter);
                break;
            case "Finished":
                cardTaskAdapter = new CardTaskFinishedAdapter(this.getActivity(), approveList, roleList, statusList);
                recyclerView = view.findViewById(R.id.tabShowCard);
                lm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(lm);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(cardTaskAdapter);
                break;
            case "Pending":
                cardTaskAdapter = new CardTaskPendingAdapter(this.getActivity(), approveList, roleList, statusList);
                recyclerView = view.findViewById(R.id.tabShowCard);
                lm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(lm);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(cardTaskAdapter);
                getTaskList("assignee", PreferenceUtil.getAccountFromSharedPreferences(getActivity()).getAccountId());
                break;
        }
    }

    private void getTaskList(String fieldName, Long value) {
        taskList.clear();
        RequestQueue mRequestQueue = SingletonRequestQueue.getInstance(getActivity().getApplicationContext()).getRequestQueue();
        String url = String.format(getResources().getString(R.string.BASE_URL) + "/task/getTaskListByFieldId?fieldName=%s&value=%d", fieldName, value);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");

        GsonRequest<TaskList> gsonRequest = new GsonRequest<>(url, TaskList.class, headers, new Response.Listener<TaskList>() {
            @Override
            public void onResponse(TaskList response) {
                taskList.addAll(response.getTaskList());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    Toast.makeText(getActivity().getApplicationContext(), "No network available", Toast.LENGTH_LONG).show();
                } else {
                    if (getContext() != null)
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

        mRequestQueue.add(gsonRequest);
    }
}
