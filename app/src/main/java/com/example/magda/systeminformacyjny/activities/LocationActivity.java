package com.example.magda.systeminformacyjny.activities;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.databinding.ActivityLocationBinding;
import com.example.magda.systeminformacyjny.models.MainPlace;
import com.example.magda.systeminformacyjny.utils.ViewPagerAdapter;
import com.example.magda.systeminformacyjny.view_models.ActivityLocationViewModel;

/**
 * Created by JB on 2017-04-09.
 */

public class LocationActivity extends AppCompatActivity{

    private static final int NUM_PAGES = 3;
    private ActivityLocationBinding activityLocationBinding;
    private MainPlace mainPlace;
    private Toolbar toolbar;

    public static final String MAIN_PLACE_TAG = "mainPlace";

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        activityLocationBinding = DataBindingUtil.setContentView(this, R.layout.activity_location);
        mainPlace = (MainPlace) getIntent().getExtras().getSerializable(MAIN_PLACE_TAG);
        activityLocationBinding.setViewModel(new ActivityLocationViewModel());
        activityLocationBinding.setMainLocation(mainPlace);
        activityLocationBinding.viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), NUM_PAGES,
                mainPlace.getNamespace(), mainPlace));
        activityLocationBinding.tabLayout.setupWithViewPager(activityLocationBinding.viewPager);
        toolbar = activityLocationBinding.toolbarLayout.toolbar;
        toolbar.setTitle(mainPlace.getName());
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
