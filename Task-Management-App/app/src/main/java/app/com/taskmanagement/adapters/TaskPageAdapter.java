package app.com.taskmanagement.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.HashMap;

import app.com.taskmanagement.TaskTabFragment;

public class TaskPageAdapter extends FragmentPagerAdapter {
    HashMap<Long, String> approveList = new HashMap<>();
    HashMap<Long, String> roleList = new HashMap<>();
    HashMap<Long, String> statusList = new HashMap<>();
    private String[] tabTitles = new String[]{
            "Ongoing",
            "Finishing",
            "Pending"
    };

    public TaskPageAdapter(@NonNull FragmentManager fm, HashMap<Long, String> approveList, HashMap<Long, String> roleList, HashMap<Long, String> statusList) {
        super(fm);
        this.approveList = approveList;
        this.roleList = roleList;
        this.statusList = statusList;
    }


    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new TaskTabFragment(tabTitles[i],approveList,roleList,statusList);
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

}