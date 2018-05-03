package com.wbteam.YYzhiyue.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.wbteam.YYzhiyue.ui.fragment.AppointmentFragment;
import com.wbteam.YYzhiyue.ui.fragment.MessageFragment;
import com.wbteam.YYzhiyue.ui.fragment.MineFragment;
import com.wbteam.YYzhiyue.ui.fragment.NearbyFragment;
import com.wbteam.YYzhiyue.ui.fragment.RewardFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/10/30.
 */

public class HomeViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> frList;

    public HomeViewPagerAdapter(FragmentManager fm) {
        super(fm);
        frList = new ArrayList<Fragment>();
        frList.add(new NearbyFragment());
        frList.add(new AppointmentFragment());
        frList.add(new RewardFragment());
        frList.add(new MessageFragment());
        frList.add(new MineFragment());

    }

    @Override
    public Fragment getItem(int position) {
        // return frList.get(position);
        if (position == 0) {
            return frList.get(0);
        } else if (position == 1) {
            return frList.get(1);
        } else if (position == 2) {
            return frList.get(2);
        } else if (position == 3) {
            return frList.get(3);
        } else if (position == 4) {
            return frList.get(4);
        }
        return null;
    }

    @Override
    public int getCount() {
        return frList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }
}
