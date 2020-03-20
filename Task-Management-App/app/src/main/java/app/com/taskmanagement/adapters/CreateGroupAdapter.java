package app.com.taskmanagement.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import app.com.taskmanagement.R;
import app.com.taskmanagement.model.Group;

public class CreateGroupAdapter extends RecyclerView.Adapter {
    private ArrayList<Group> dataSet;
    Context mContext;

    public CreateGroupAdapter(ArrayList<Group> dataSet, Context mContext) {
        this.dataSet = dataSet;
        this.mContext = mContext;
    }

    public static class CreateGroupHolder extends RecyclerView.ViewHolder {
        EditText txtGroupName, txtDesGroup, txtCreator;
        Button btnCreate;

        public CreateGroupHolder(@NonNull View itemView) {
            super(itemView);
            this.txtGroupName = (EditText) itemView.findViewById(R.id.edtNameGroup);
            this.txtDesGroup = (EditText) itemView.findViewById(R.id.edtDesGroup);
            this.txtCreator = (EditText) itemView.findViewById(R.id.edtCreatorGroup);
            this.btnCreate = (Button) itemView.findViewById(R.id.btnCreateGroup);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_create_new_group, parent, false);
        return new CreateGroupHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Group object = dataSet.get(position);
        if (object != null) {
            ((CreateGroupHolder) holder).txtGroupName.setText(object.getGroupName());
            ((CreateGroupHolder) holder).txtDesGroup.setText(object.getDescription());
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
