package app.com.taskmanagement.Adapters;

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

public class AccountAdapter extends RecyclerView.Adapter {
    private ArrayList<AccountModel> dataSet;
    Context mContext;
    int total_types;

    public AccountAdapter(ArrayList<AccountModel> data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case AccountModel.SHOW_PROFILE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_create_update_account, parent, false);
                return new AccountAdapter.CreateUpdateAccountHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final AccountModel object = dataSet.get(position);
        if (object != null) {
            switch (object.type) {
                case AccountModel.SHOW_PROFILE:
                    ((CreateUpdateAccountHolder) holder).txtFullname.setText(object.getFullName());
                    ((CreateUpdateAccountHolder) holder).txtUsername.setText(object.getUsername());
                    ((CreateUpdateAccountHolder) holder).txtEmail.setText(object.getEmail());
                    ((CreateUpdateAccountHolder) holder).txtPhone.setText(object.getPhone());
                    ((CreateUpdateAccountHolder) holder).txtRole.setTag(object.getRoleId());
                    ((CreateUpdateAccountHolder) holder).txtActive.setTag(object.isDeactivated());
                    break;
                case AccountModel.CHANGE_PASSWORD:
                    ((ChangePasswordHolder) holder).txtPassword.setText(object.getPassword());
                    ((ChangePasswordHolder) holder).txtConfirmPwd.setText("");
            }
        }
    }

    public static class CreateUpdateAccountHolder extends RecyclerView.ViewHolder {
        TextView txtFullname, txtUsername, txtEmail, txtPhone;
        Spinner txtRole, txtActive;

        public CreateUpdateAccountHolder(@NonNull View itemView) {
            super(itemView);
            txtFullname = (TextView) itemView.findViewById(R.id.txtFullname);
            txtUsername = (TextView) itemView.findViewById(R.id.txtUsername);
            txtEmail = (TextView) itemView.findViewById(R.id.txtEmail);
            txtPhone = (TextView) itemView.findViewById(R.id.txtPhone);
            txtRole = (Spinner) itemView.findViewById(R.id.txtRole);
            txtActive = (Spinner) itemView.findViewById(R.id.txtActive);
        }
    }

    public static class ChangePasswordHolder extends RecyclerView.ViewHolder {
        TextView txtPassword, txtConfirmPwd;

        public ChangePasswordHolder(@NonNull View itemView) {
            super(itemView);
            txtPassword = (TextView) itemView.findViewById(R.id.txtPassword);
            txtConfirmPwd = (TextView) itemView.findViewById(R.id.txtConfirmPwd);
        }
    }

    @Override
    public int getItemViewType(int position) {

        switch (dataSet.get(position).type) {
            case 0:
                return AccountModel.SHOW_PROFILE;
            case 1:
                return AccountModel.CHANGE_PASSWORD;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
