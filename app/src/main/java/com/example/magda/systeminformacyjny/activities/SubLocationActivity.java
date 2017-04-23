package com.example.magda.systeminformacyjny.activities;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.databinding.ActivityLocationBinding;
import com.example.magda.systeminformacyjny.models.Place;
import com.example.magda.systeminformacyjny.utils.SubPlaceViewPager;

/**
 * Created by piotrek on 23.04.17.
 */

public class SubLocationActivity extends AppCompatActivity{

    private ActivityLocationBinding activityLocationBinding;
    private Toolbar toolbar;
    private static final int NUM_PAGES = 3;
    private Place place;

    public static final String PLACE_TAG = "place";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLocationBinding = DataBindingUtil.setContentView(this, R.layout.activity_location);
        place = (Place) getIntent().getExtras().getSerializable(PLACE_TAG);
        toolbar = activityLocationBinding.toolbarLayout.toolbar;
        toolbar.setTitle(place.getName());
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        activityLocationBinding.viewPager.setAdapter(new SubPlaceViewPager(getSupportFragmentManager(), NUM_PAGES,
                place));
        activityLocationBinding.tabLayout.setupWithViewPager(activityLocationBinding.viewPager);
        activityLocationBinding.setPlace(place);
        activityLocationBinding.setShowButtons(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
