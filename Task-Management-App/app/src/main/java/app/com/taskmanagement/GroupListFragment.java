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

import app.com.taskmanagement.adapters.GroupCardAdapter;
import app.com.taskmanagement.model.GroupModel;


public class GroupListFragment extends Fragment {
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
        View rootView = inflater.inflate(R.layout.group_list_fragment, container, false);
        getActivity().setTitle("Group List");

        final GroupCardAdapter groupCardAdapter = new GroupCardAdapter(this.getActivity(), this);
        groupCardAdapter.setOnItemClickedListener(new GroupCardAdapter.OnItemClicked() {
            @Override
            public void onClicked(int position) {
                GroupModel groupModel = groupCardAdapter.getItem(position);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                        new GroupDetailFragment(groupModel))
                        .addToBackStack(null).commit();
                getActivity().setTitle("Group Detail");
            }
        });
        recyclerView = rootView.findViewById(R.id.grouplist_recycler);
        StaggeredGridLayoutManager lm =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(groupCardAdapter);
        btnCreate = rootView.findViewById(R.id.btnCreateGroup);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                        new GroupCreateFragment()).addToBackStack(null).commit();
                getActivity().setTitle("Create group");
            }
        });
        return rootView;
    }
}
