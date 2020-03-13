package app.com.taskmanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

import app.com.taskmanagement.adapters.TaskAdapter;
import app.com.taskmanagement.model.TaskModel;


public class FragmentCreateNewTask extends Fragment {
    ArrayList<TaskModel> gridViewModelArrayList;
    private RecyclerView recyclerView;
    TextView valueTaskName, valueDescription;
    private Button valueDateDeadline;
    private Button valueTimeDeadline;
    private Button btnCreateTask;



    public FragmentCreateNewTask() {
        // Required empty public constructor
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
//        btnCreateTask = (Button)rootView.findViewById(R.id.btnCreateTask);
//        btnCreateTask.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        gridViewModel = new TaskModel(TaskModel.SHOW_FORM_CREATE, null, null, "", null, null, null, null, "", null, null, null, null, null);

        gridViewModelArrayList.add(gridViewModel);

        TaskAdapter taskAdapter = new TaskAdapter(gridViewModelArrayList, this.getActivity());
        recyclerView = rootView.findViewById(R.id.viewRecycler);
        StaggeredGridLayoutManager lm =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(taskAdapter);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        valueDateDeadline = view.findViewById(R.id.valueDateDeadline);
        valueTimeDeadline = view.findViewById(R.id.valueTimeDeadline);
    }


}
