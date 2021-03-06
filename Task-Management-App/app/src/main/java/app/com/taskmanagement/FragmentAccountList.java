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

import app.com.taskmanagement.adapters.CardAccountAdapter;
import app.com.taskmanagement.model.AccountModel;


public class FragmentAccountList extends Fragment {
    private RecyclerView recyclerView;
    private Button btnCreate;
    private HashMap<Long, String> roleList;

    public FragmentAccountList(HashMap<Long, String> roleList) {
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
        View rootView = inflater.inflate(R.layout.fragment_list_account, container, false);
        getActivity().setTitle("Account List");

        final CardAccountAdapter cardAccountAdapter = new CardAccountAdapter(this.getActivity(), this, roleList);
        cardAccountAdapter.setOnItemClickedListener(new CardAccountAdapter.OnItemClicked() {
            @Override
            public void onClicked(int position) {
                AccountModel accountModel = cardAccountAdapter.getItem(position);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                        new FragmentAccountDetail(accountModel, roleList))
                        .addToBackStack(null).commit();
                getActivity().setTitle("Group Detail");
            }
        });
        recyclerView = rootView.findViewById(R.id.accountlist_recycler);
        StaggeredGridLayoutManager lm =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cardAccountAdapter);
        btnCreate = rootView.findViewById(R.id.btnCreateAccount);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                        new FragmentCreateAccount(roleList)).addToBackStack(null).commit();
                getActivity().setTitle("Create account");
            }
        });
        return rootView;
    }
}
