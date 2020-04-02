package app.com.taskmanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.HashMap;

import app.com.taskmanagement.model.GroupModel;
import app.com.taskmanagement.model.response.LoginResponse;
import app.com.taskmanagement.util.GsonRequest;
import app.com.taskmanagement.util.SingletonRequestQueue;


/**
 * A simple {@link Fragment} subclass.
 */
public class GroupDetailFragment extends Fragment {
    private GroupModel groupModel;
    private EditText edtGroupId, edtGroupName, edtGroupDescription, edtGroupCreator;

    public GroupDetailFragment(GroupModel groupModel) {
        this.groupModel = groupModel;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.group_detail_fragment, container, false);
        edtGroupId = rootView.findViewById(R.id.edtGroupId);
        edtGroupName = rootView.findViewById(R.id.edtGroupName);
        edtGroupName.setEnabled(false);
        edtGroupDescription = rootView.findViewById(R.id.edtGroupDescription);
        edtGroupDescription.setEnabled(false);
        edtGroupCreator = rootView.findViewById(R.id.edtGroupCreator);

        edtGroupId.setText(groupModel.getGroupId().toString());
        edtGroupName.setText(groupModel.getGroupName());
        edtGroupDescription.setText(groupModel.getDescription());
        getGroupCreatorName(groupModel.getCreator());

        return rootView;
    }

    public void getGroupCreatorName(Long accountId) {
        RequestQueue requestQueue = SingletonRequestQueue.getInstance(getActivity().getApplicationContext()).getRequestQueue();
        HashMap<String, String> headers = new HashMap<>();
        String url = getResources().getString(R.string.BASE_URL) + "/account?id=" + accountId;
        GsonRequest<LoginResponse> gsonRequest = new GsonRequest<>(url, LoginResponse.class, headers, new Response.Listener<LoginResponse>() {
            @Override
            public void onResponse(LoginResponse response) {
                edtGroupCreator.setText(response.account.getFullName());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Connection Time Out", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(gsonRequest);
    }

}
