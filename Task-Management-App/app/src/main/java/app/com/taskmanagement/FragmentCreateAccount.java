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

import app.com.taskmanagement.adapters.CardGroupAdapter;
import app.com.taskmanagement.adapters.CreateAccountAdapter;
import app.com.taskmanagement.model.AccountModel;
import app.com.taskmanagement.model.Group;

public class FragmentCreateAccount extends Fragment {

    public FragmentCreateAccount() {
        // Required empty public constructor
    }

    ArrayList<AccountModel> gridViewModelArrayList;
    private RecyclerView recyclerView;

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
        AccountModel gridViewModel = null;
        gridViewModel = new AccountModel(1L,"thuct","Cao Thu", "090000000","abc@gmail.com",false,2L,1L);
        gridViewModelArrayList.add(gridViewModel);

        CreateAccountAdapter createAccountAdapter = new CreateAccountAdapter(gridViewModelArrayList, this.getActivity());
        recyclerView = rootView.findViewById(R.id.viewRecycler);
        StaggeredGridLayoutManager lm =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(createAccountAdapter);
        return rootView;
    }
}
