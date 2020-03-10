package app.com.taskmanagement.adapter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

import app.com.taskmanagement.MyTaskTabFragment;

public class TaskPageAdapter extends FragmentStatePagerAdapter {
    private String[] tabTitles = new String[]{
            "Pending",
            "Unfinished",
            "Finished"
    };

    public TaskPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new MyTaskTabFragment(tabTitles[i]);
        Bundle args = new Bundle();
        // Our object is just an integer :-P
        args.putInt(MyTaskTabFragment.ARG_OBJECT, i + 1);
        fragment.setArguments(args);
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
