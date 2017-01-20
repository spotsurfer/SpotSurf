package com.bam.spotsurf.activity;

/**
 * Created by bmerm on 10/4/2016.
 */

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.bam.spotsurf.R;
import com.bam.spotsurf.fragnav.SL_Fragment;
import com.bam.spotsurf.fragnav.SL_PriceFragment;
import com.bam.spotsurf.fragnav.SL_SummaryFragment;
import com.bam.spotsurf.fragnav.SL_TimeFragment;

public class PageViewActivity extends FragmentActivity {
    MyPageAdapter pageAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_view);

        List<Fragment> fragments = getFragments();

        pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);

        ViewPager pager = (ViewPager)findViewById(R.id.viewpager);
        pager.setAdapter(pageAdapter);

    }

    private List<Fragment> getFragments(){
        List<Fragment> fList = new ArrayList<Fragment>();

        fList.add(SL_Fragment.newInstance());
        fList.add(SL_TimeFragment.newInstance());
        fList.add(SL_PriceFragment.newInstance());
        fList.add(SL_SummaryFragment.newInstance());

        return fList;
    }

    private class MyPageAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        public MyPageAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }
        @Override
        public Fragment getItem(int position) {
            return this.fragments.get(position);
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }
    }
}