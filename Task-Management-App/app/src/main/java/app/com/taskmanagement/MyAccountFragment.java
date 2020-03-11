package app.com.taskmanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import app.com.taskmanagement.Adapters.AccountAdapter;
import app.com.taskmanagement.Adapters.TaskAdapter;
import app.com.taskmanagement.model.AccountModel;
import app.com.taskmanagement.model.TaskModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccountFragment extends Fragment {
    ArrayList<AccountModel> gridViewModelArrayList;
    private RecyclerView recyclerView;

    public MyAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recycler, container, false);
        gridViewModelArrayList = new ArrayList();
        AccountModel gridViewModel = null;
        gridViewModel = new AccountModel(AccountModel.SHOW_PROFILE,null,"","","","",
                "","","","",false,null,null);
        gridViewModelArrayList.add(gridViewModel);

        AccountAdapter accountAdapter = new AccountAdapter(gridViewModelArrayList, this.getActivity().getApplicationContext());
        recyclerView = rootView.findViewById(R.id.viewRecycler);
        StaggeredGridLayoutManager lm =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(accountAdapter);
        return rootView;
    }
}
