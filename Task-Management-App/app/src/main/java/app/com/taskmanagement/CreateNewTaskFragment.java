package app.com.taskmanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.time.Instant;
import java.util.ArrayList;

import app.com.taskmanagement.Adapters.TaskAdapter;
import app.com.taskmanagement.model.TaskModel;


public class CreateNewTaskFragment extends Fragment {
    ArrayList<TaskModel> gridViewModelArrayList;
    private RecyclerView recyclerView;

    public CreateNewTaskFragment() {
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
        gridViewModel = new TaskModel(TaskModel.SHOW_FORM_CREATE,null,null,"",Instant.now(),Instant.now(),null,null,"",null,null,null);
        gridViewModelArrayList.add(gridViewModel);

        TaskAdapter taskAdapter = new TaskAdapter(gridViewModelArrayList, this.getActivity().getApplicationContext());
        recyclerView = rootView.findViewById(R.id.viewRecycler);
        StaggeredGridLayoutManager lm =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(taskAdapter);
        return rootView;

    }
}
