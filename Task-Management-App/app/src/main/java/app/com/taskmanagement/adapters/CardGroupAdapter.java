package app.com.taskmanagement.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import app.com.taskmanagement.R;
import app.com.taskmanagement.model.Group;

public class CardGroupAdapter extends RecyclerView.Adapter {
    private ArrayList<Group> dataSet;
    Context mContext;

    public CardGroupAdapter(ArrayList<Group> dataSet, Context mContext) {
        this.dataSet = dataSet;
        this.mContext = mContext;
    }

    public static class CardGroupHolder extends RecyclerView.ViewHolder {
        TextView txtGroupName, txtDesGroup;

        public CardGroupHolder(@NonNull View itemView) {
            super(itemView);
            this.txtGroupName = (TextView) itemView.findViewById(R.id.txtGroupName);
            this.txtDesGroup = (TextView) itemView.findViewById(R.id.txtDesGroup);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_group_fragment, parent, false);
        return new CardGroupHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Group object = dataSet.get(position);
        if (object != null) {
            ((CardGroupHolder) holder).txtGroupName.setText(object.getGroupName());
            ((CardGroupHolder) holder).txtDesGroup.setText(object.getDescription());

        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
