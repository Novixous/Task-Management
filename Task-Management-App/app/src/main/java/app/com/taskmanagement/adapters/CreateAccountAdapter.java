package app.com.taskmanagement.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import app.com.taskmanagement.R;
import app.com.taskmanagement.model.AccountModel;

public class CreateAccountAdapter extends RecyclerView.Adapter {
    public static final int MODE_MYACCOUNT = 0;
    public static final int MODE_ACCOUNTDETAIL = 1;
    public static final int MODE_CREATEACCOUNT = 2;
    private AccountModel accountModel;
    Context mContext;
    int total_types;

    public CreateAccountAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_create_account, parent, false);
        return new CreateAccountHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (accountModel != null) {
            ((CreateAccountHolder) holder).txtFullname.setText(accountModel.getFullName());
            ((CreateAccountHolder) holder).txtUsername.setText(accountModel.getUsername());
            ((CreateAccountHolder) holder).txtEmail.setText(accountModel.getEmail());
            ((CreateAccountHolder) holder).txtPhone.setText(accountModel.getPhone());
            ((CreateAccountHolder) holder).txtRole.setTag(accountModel.getRoleId());
            ((CreateAccountHolder) holder).txtActive.setTag(accountModel.isDeactivated());
            ((CreateAccountHolder) holder).txtGroup.setTag(accountModel.getGroupId());
        }
    }

    public static class CreateAccountHolder extends RecyclerView.ViewHolder {
        TextView txtFullname, txtUsername, txtEmail, txtPhone;
        Spinner txtRole, txtActive, txtGroup;

        public CreateAccountHolder(@NonNull View itemView) {
            super(itemView);
            txtFullname = (TextView) itemView.findViewById(R.id.txtFullname);
            txtUsername = (TextView) itemView.findViewById(R.id.txtUsername);
            txtEmail = (TextView) itemView.findViewById(R.id.txtEmail);
            txtPhone = (TextView) itemView.findViewById(R.id.txtPhone);
            txtRole = (Spinner) itemView.findViewById(R.id.txtRole);
            txtActive = (Spinner) itemView.findViewById(R.id.txtActive);
            txtGroup = (Spinner) itemView.findViewById(R.id.txtGroup);
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }


}
