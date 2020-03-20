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
    private ArrayList<AccountModel> dataSet;
    Context mContext;
    int total_types;

    public CreateAccountAdapter(ArrayList<AccountModel> data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();
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
        final AccountModel object = dataSet.get(position);
        if (object != null) {
            ((CreateAccountHolder) holder).txtFullname.setText(object.getFullName());
            ((CreateAccountHolder) holder).txtUsername.setText(object.getUsername());
            ((CreateAccountHolder) holder).txtEmail.setText(object.getEmail());
            ((CreateAccountHolder) holder).txtPhone.setText(object.getPhone());
            ((CreateAccountHolder) holder).txtRole.setTag(object.getRoleId());
            ((CreateAccountHolder) holder).txtActive.setTag(object.isDeactivated());
            ((CreateAccountHolder)holder).txtGroup.setTag(object.getGroupId());
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
        return dataSet.size();
    }


}
