package app.com.taskmanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

import app.com.taskmanagement.adapters.CardGroupAdapter;
import app.com.taskmanagement.model.Group;


public class FragmentGroupList extends Fragment {
    ArrayList<Group> gridViewModelArrayList;
    private RecyclerView recyclerView;
    private Button btnCreate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list_group, container, false);
        gridViewModelArrayList = new ArrayList();
        Group gridViewModel = null;
        gridViewModel = new Group(1L, "Haha", "hihihi", 1L);
        gridViewModelArrayList.add(gridViewModel);

        CardGroupAdapter cardGroupAdapter = new CardGroupAdapter(gridViewModelArrayList, this.getActivity());
        recyclerView = rootView.findViewById(R.id.grouplist_recycler);
        StaggeredGridLayoutManager lm =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cardGroupAdapter);
        btnCreate = rootView.findViewById(R.id.btnCreateGroup);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new FragmentCreateNewGroup()).commit();
                getActivity().setTitle("Create group");
            }
        });
        return rootView;
    }
}
