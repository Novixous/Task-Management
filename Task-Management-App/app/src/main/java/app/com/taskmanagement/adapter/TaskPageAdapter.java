package app.com.taskmanagement.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import app.com.taskmanagement.UserTaskTabFragment;

public class TaskPageAdapter extends FragmentPagerAdapter {
    private String[] tabTitles = new String[]{
            "Pending",
            "Todo",
            "Finished"
    };

    public TaskPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new UserTaskTabFragment(tabTitles[i]);
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
