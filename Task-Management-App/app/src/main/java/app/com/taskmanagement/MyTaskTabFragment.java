package app.com.taskmanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.NetworkError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.HashMap;

import app.com.taskmanagement.model.TaskList;
import app.com.taskmanagement.model.TaskModel;
import app.com.taskmanagement.util.GsonRequest;
import app.com.taskmanagement.util.SingletonRequestQueue;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyTaskTabFragment extends Fragment {

    public static final String ARG_OBJECT = "object";
    public static final String BASE_URL = "http://123.20.89.86:8080";
    public String tabTitle;
    public ArrayList<TaskModel> taskList = new ArrayList<>();

    public MyTaskTabFragment(String tabTitle) {
        this.tabTitle = tabTitle;
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
        ((TextView) view.findViewById(R.id.text1))
                .setText(Integer.toString(args.getInt(ARG_OBJECT)));
        switch (tabTitle) {
            case "Todo":
                break;
            case "Finished":
                break;
            case "Pending":
                getTaskList("assignee", Long.valueOf(1));
                break;
        }
    }

    private void getTaskList(String fieldName, Long value) {
        taskList.clear();
        RequestQueue mRequestQueue = SingletonRequestQueue.getInstance(getActivity().getApplicationContext()).getRequestQueue();
        String url = String.format(BASE_URL + "/task/getTaskListByFieldId?fieldName=%s&value=%d", fieldName, value);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");

        GsonRequest<TaskList> gsonRequest = new GsonRequest<>(url, TaskList.class, headers, new Response.Listener<TaskList>() {
            @Override
            public void onResponse(TaskList response) {
                taskList.addAll(response.taskList);
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
