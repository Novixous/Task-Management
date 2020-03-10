package app.com.taskmanagement;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowTaskFragment extends Fragment {
    TextView txtImgResolution;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_show_task, container, false);
        txtImgResolution = (TextView) getActivity().findViewById(R.id.txtImgResolution);
        txtImgResolution.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        return rootView;
    }
}
