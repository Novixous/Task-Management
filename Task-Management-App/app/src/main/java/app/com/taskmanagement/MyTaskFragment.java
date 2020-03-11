package app.com.taskmanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import app.com.taskmanagement.adapter.TaskPageAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyTaskFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    TaskPageAdapter taskPageAdapter;


    public MyTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_my_task, container, false);
        tabLayout = rootView.findViewById(R.id.tabLayout);
        viewPager = rootView.findViewById(R.id.viewPager);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        taskPageAdapter = new TaskPageAdapter(getChildFragmentManager());
        viewPager.setAdapter(taskPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
