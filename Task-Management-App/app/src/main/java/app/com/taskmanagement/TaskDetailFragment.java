package app.com.taskmanagement;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;

import app.com.taskmanagement.adapters.TaskDetailClosedAdapter;
import app.com.taskmanagement.adapters.TaskDetailFinishedAdapter;
import app.com.taskmanagement.adapters.TaskDetailPendingAdapter;
import app.com.taskmanagement.adapters.TaskDetailTodoAdapter;
import app.com.taskmanagement.model.TaskModel;

public class TaskDetailFragment extends Fragment {
    public static final int MODE_PENDING = 0;
    public static final int MODE_TODO = 1;
    public static final int MODE_FINISHED = 2;
    public static final int MODE_CLOSED = 3;

    private ArrayList<TaskModel> gridViewModelArrayList;
    private HashMap<Long, String> approveList;
    private HashMap<Long, String> roleList;
    private HashMap<Long, String> statusList;
    private int mode;
    private Long taskId;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter detailTaskAdapter;

    public TaskDetailFragment(HashMap<Long, String> approveList, HashMap<Long, String> roleList, HashMap<Long, String> statusList, Long taskId, int mode) {
        this.approveList = approveList;
        this.roleList = roleList;
        this.statusList = statusList;
        this.taskId = taskId;
        this.mode = mode;
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
        detailTaskAdapter = null;
        switch (mode) {
            case MODE_PENDING:
                detailTaskAdapter = new TaskDetailPendingAdapter(this.getActivity(), taskId, approveList, roleList, statusList);
                break;
            case MODE_TODO:
                detailTaskAdapter = new TaskDetailTodoAdapter(this.getActivity(), this, taskId, approveList, roleList, statusList);
                break;
            case MODE_FINISHED:
                detailTaskAdapter = new TaskDetailFinishedAdapter(this.getActivity(), taskId, approveList, roleList, statusList);
                break;
            case MODE_CLOSED:
                detailTaskAdapter = new TaskDetailClosedAdapter(this.getActivity(), taskId, approveList, roleList, statusList);
                break;
        }
        recyclerView = rootView.findViewById(R.id.viewRecycler);
        StaggeredGridLayoutManager lm =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(detailTaskAdapter);
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1000) {
                Uri returnUri = data.getData();
                Bitmap bitmapImage = null;
                try {
                    bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), returnUri);
                    ((TaskDetailTodoAdapter) detailTaskAdapter).setImageResolution(bitmapImage);
                    detailTaskAdapter.notifyDataSetChanged();

                } catch (Exception e) {

                }
            }
        }
    }
}
