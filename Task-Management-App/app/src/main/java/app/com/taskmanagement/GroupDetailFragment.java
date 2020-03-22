package app.com.taskmanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.HashMap;

import app.com.taskmanagement.model.Group;
import app.com.taskmanagement.model.response.LoginResponse;
import app.com.taskmanagement.util.GsonRequest;
import app.com.taskmanagement.util.SingletonRequestQueue;


/**
 * A simple {@link Fragment} subclass.
 */
public class GroupDetailFragment extends Fragment {
    private Group group;
    private EditText edtGroupId, edtGroupName, edtGroupDescription, edtGroupCreator;

    public GroupDetailFragment(Group group) {
        this.group = group;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_group_detail, container, false);
        edtGroupId = rootView.findViewById(R.id.edtGroupId);
        edtGroupName = rootView.findViewById(R.id.edtGroupName);
        edtGroupDescription = rootView.findViewById(R.id.edtGroupDescription);
        edtGroupCreator = rootView.findViewById(R.id.edtGroupCreator);

        edtGroupId.setText(group.getGroupId().toString());
        edtGroupName.setText(group.getGroupName());
        edtGroupDescription.setText(group.getDescription());
        getGroupCreatorName(group.getCreator());


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
