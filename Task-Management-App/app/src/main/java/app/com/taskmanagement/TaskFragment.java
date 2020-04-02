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

import java.util.HashMap;

import app.com.taskmanagement.adapters.TaskPageAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;
    public TaskPageAdapter taskPageAdapter;
    HashMap<Long, String> approveList;
    HashMap<Long, String> roleList;
    HashMap<Long, String> statusList;


    public TaskFragment(HashMap<Long, String> approveList, HashMap<Long, String> roleList, HashMap<Long, String> statusList) {
        this.approveList = approveList;
        this.roleList = roleList;
        this.statusList = statusList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.task_fragment, container, false);
        tabLayout = rootView.findViewById(R.id.tabLayout);
        viewPager = rootView.findViewById(R.id.viewPager);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        taskPageAdapter = new TaskPageAdapter(getChildFragmentManager(), approveList, roleList, statusList);
        viewPager.setAdapter(taskPageAdapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int i, final float v, final int i2) {
            }

            @Override
            public void onPageSelected(final int i) {
                taskPageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(final int i) {
            }
        });
    }


}
