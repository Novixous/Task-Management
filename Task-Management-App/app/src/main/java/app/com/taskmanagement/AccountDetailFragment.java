package app.com.taskmanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import java.util.HashMap;

import app.com.taskmanagement.model.AccountModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountDetailFragment extends Fragment {
    private AccountModel accountModel;
    private EditText edtId, edtFullname, edtUsername, edtEmail, edtPhone;
    private Spinner spinnerActive, spinnerGroup, spinnerRole;

    public AccountDetailFragment(AccountModel accountModel) {
        this.accountModel = accountModel;
    }

    private HashMap<Long, String> roleList;

    public AccountDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_account_detail, container, false);

        edtId = rootView.findViewById(R.id.edtId);
        edtId.setText(accountModel.getAccountId().toString());
        edtFullname = rootView.findViewById(R.id.edtFullname);
        edtFullname.setText(accountModel.getFullName());
        edtUsername = rootView.findViewById(R.id.edtUsername);
        edtUsername.setText(accountModel.getUsername());
        edtEmail = rootView.findViewById(R.id.edtEmail);
        edtEmail.setText(accountModel.getEmail());
        edtPhone = rootView.findViewById(R.id.edtPhone);
        edtPhone.setText(accountModel.getPhone());
        spinnerActive = rootView.findViewById(R.id.spinnerActive);
        spinnerActive.setTag(accountModel.isDeactivated());
        spinnerGroup = rootView.findViewById(R.id.spinnerGroup);
        spinnerGroup.setTag(accountModel.getGroupId());
        spinnerRole = rootView.findViewById(R.id.spinnerRole);
        spinnerRole.setTag(accountModel.getRoleId());

        return rootView;
    }
}
