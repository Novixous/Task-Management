package app.com.taskmanagement.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import app.com.taskmanagement.R;
import app.com.taskmanagement.model.AccountModel;

public class CardAccountAdapter extends RecyclerView.Adapter {
    private ArrayList<AccountModel> dataSet;
    Fragment fragment;
    Context mContext;
    private OnItemClicked onItemClickedListener;

    public CardAccountAdapter(Context mContext, Fragment fragment) {
        this.dataSet = new ArrayList<>();
        this.fragment = fragment;
        this.mContext = mContext;
    }

    public AccountModel getItem(int position) {
        return dataSet.get(position);
    }

    public interface OnItemClicked {
        void onClicked(int position);
    }

    public void setOnItemClickedListener(OnItemClicked onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

    public static class CardAccountHolder extends RecyclerView.ViewHolder {
        TextView txtAccountName, txtRoleAccount, txtGroupAccount;

        public CardAccountHolder(@NonNull View itemView) {
            super(itemView);
            this.txtAccountName = (TextView) itemView.findViewById(R.id.txtAccountName);
            this.txtRoleAccount = (TextView) itemView.findViewById(R.id.txtRoleAccount);
            this.txtGroupAccount = (TextView) itemView.findViewById(R.id.txtGroupName);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_account_fragment, parent, false);
        return new CardAccountHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final AccountModel object = dataSet.get(position);
        if (object != null) {
            ((CardAccountHolder) holder).txtAccountName.setText(object.getFullName());
            ((CardAccountHolder) holder).txtRoleAccount.setText(object.getRoleId().toString());
            ((CardAccountHolder) holder).txtGroupAccount.setText(object.getGroupId().toString());
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();

    }
}
