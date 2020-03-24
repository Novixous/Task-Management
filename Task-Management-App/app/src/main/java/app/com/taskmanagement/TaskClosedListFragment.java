package app.com.taskmanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;

import app.com.taskmanagement.adapters.TaskCardClosedAdapter;
import app.com.taskmanagement.model.response.TaskResponse;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskClosedListFragment extends Fragment {
    public static final String ARG_OBJECT = "object";
    public String tabTitle;
    public ArrayList<TaskResponse> taskList = new ArrayList<>();
    HashMap<Long, String> approveList = new HashMap<>();
    HashMap<Long, String> roleList = new HashMap<>();
    HashMap<Long, String> statusList = new HashMap<>();
    private RecyclerView recyclerView;

    public TaskClosedListFragment(String tabTitle, HashMap<Long, String> approveList, HashMap<Long, String> roleList, HashMap<Long, String> statusList) {
        this.tabTitle = tabTitle;
        this.approveList = approveList;
        this.roleList = roleList;
        this.statusList = statusList;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_task_tab, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TaskCardClosedAdapter cardTaskAdapter = new TaskCardClosedAdapter(this.getActivity(), approveList, roleList, statusList);
        recyclerView = view.findViewById(R.id.tabShowCard);
        StaggeredGridLayoutManager lm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cardTaskAdapter);
    }
}
