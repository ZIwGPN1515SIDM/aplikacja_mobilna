package com.example.magda.systeminformacyjny.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.databinding.ActivityLocationBinding;
import com.example.magda.systeminformacyjny.fragments.ActivityLocationFragment;
import com.example.magda.systeminformacyjny.utils.ViewPagerAdapter;

import static com.example.magda.systeminformacyjny.R.id.viewPager;

/**
 * Created by JB on 2017-04-09.
 */

public class LocationActivity extends AppCompatActivity{

    private static final int NUM_PAGES = 3;
    ActivityLocationBinding activityLocationBinding;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        activityLocationBinding = DataBindingUtil.setContentView(this, R.layout.activity_location);
        activityLocationBinding.viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), NUM_PAGES));
        activityLocationBinding.tabLayout.setupWithViewPager(activityLocationBinding.viewPager);
    }


    @Override
    public void onBackPressed() {
        if(activityLocationBinding.viewPager.getCurrentItem() == 0){
            super.onBackPressed();
        } else {
            activityLocationBinding.viewPager.setCurrentItem(activityLocationBinding.viewPager.getCurrentItem() - 1);
        }
    }
}
