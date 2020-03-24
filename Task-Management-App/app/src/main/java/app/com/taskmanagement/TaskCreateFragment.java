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

import app.com.taskmanagement.adapters.NewTaskAdapter;
import app.com.taskmanagement.model.TaskModel;


public class TaskCreateFragment extends Fragment {
    private ArrayList<TaskModel> gridViewModelArrayList;
    private RecyclerView recyclerView;
    private HashMap<Long, String> approveList;
    private HashMap<Long, String> roleList;
    private HashMap<Long, String> statusList;
    private TaskModel taskModel;


    public TaskCreateFragment(HashMap<Long, String> approveList, HashMap<Long, String> roleList, HashMap<Long, String> statusList) {
        this.approveList = approveList;
        this.roleList = roleList;
        this.statusList = statusList;
        this.taskModel = null;
    }

    public TaskCreateFragment(HashMap<Long, String> approveList, HashMap<Long, String> roleList, HashMap<Long, String> statusList, TaskModel taskModel) {
        this.approveList = approveList;
        this.roleList = roleList;
        this.statusList = statusList;
        this.taskModel = taskModel;
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
        gridViewModelArrayList = new ArrayList();

        TaskModel gridViewModel = null;
        gridViewModel = new TaskModel(TaskModel.SHOW_FORM_CREATE);

        gridViewModelArrayList.add(gridViewModel);
        NewTaskAdapter newTaskAdapter;
        if (taskModel == null) {
            newTaskAdapter = new NewTaskAdapter(this.getActivity(), approveList, roleList, statusList);
        } else {
            newTaskAdapter = new NewTaskAdapter(this.getActivity(), approveList, roleList, statusList, taskModel);
        }
        recyclerView = rootView.findViewById(R.id.viewRecycler);
        StaggeredGridLayoutManager lm =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(newTaskAdapter);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}
