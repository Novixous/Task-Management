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

import java.util.HashMap;

import app.com.taskmanagement.adapters.AccountCardAdapter;
import app.com.taskmanagement.model.AccountModel;


public class AccountListFragment extends Fragment {
    private RecyclerView recyclerView;
    private Button btnCreate;
    private HashMap<Long, String> roleList;

    public AccountListFragment(HashMap<Long, String> roleList) {
        this.roleList = roleList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.account_list_fragment, container, false);
        getActivity().setTitle("Account List");

        final AccountCardAdapter AccountCardAdapter = new AccountCardAdapter(this.getActivity(), this, roleList);
        AccountCardAdapter.setOnItemClickedListener(new AccountCardAdapter.OnItemClicked() {
            @Override
            public void onClicked(int position) {
                AccountModel accountModel = AccountCardAdapter.getItem(position);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                        new AccountDetailFragment(accountModel, roleList))
                        .addToBackStack(null).commit();
                getActivity().setTitle("Account Detail");
            }
        });
        recyclerView = rootView.findViewById(R.id.accountlist_recycler);
        StaggeredGridLayoutManager lm =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(AccountCardAdapter);
        btnCreate = rootView.findViewById(R.id.btnCreateAccount);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                        new AccountCreateFragment(roleList)).commit();
                getActivity().setTitle("Create account");
            }
        });
        return rootView;
    }
}