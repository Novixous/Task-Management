package app.com.taskmanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyTaskFragment extends Fragment {
    TabLayout tabLayout;

    public MyTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_my_task, container, false);
        tabLayout = rootView.findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Unfinished"));
        tabLayout.addTab(tabLayout.newTab().setText("Finished"));
        return rootView;
    }
}
