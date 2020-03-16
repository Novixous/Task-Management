package app.com.taskmanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;

import app.com.taskmanagement.adapters.NewTaskAdapter;
import app.com.taskmanagement.adapters.UpdateTaskAdapter;
import app.com.taskmanagement.model.TaskModel;

public class UserUpdateTaskFragment extends Fragment {
    ArrayList<TaskModel> gridViewModelArrayList;
    HashMap<Long, String> approveList;
    HashMap<Long, String> roleList;
    HashMap<Long, String> statusList;
    Long taskId;
    private RecyclerView recyclerView;

    public UserUpdateTaskFragment(HashMap<Long, String> approveList, HashMap<Long, String> roleList, HashMap<Long, String> statusList, Long taskId) {
        this.approveList = approveList;
        this.roleList = roleList;
        this.statusList = statusList;
        this.taskId = taskId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recycler, container, false);
        UpdateTaskAdapter updateTaskAdapter = new UpdateTaskAdapter(this.getActivity(), taskId, approveList, roleList, statusList);
        recyclerView = rootView.findViewById(R.id.viewRecycler);
        StaggeredGridLayoutManager lm =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(updateTaskAdapter);
        return rootView;
    }
}
