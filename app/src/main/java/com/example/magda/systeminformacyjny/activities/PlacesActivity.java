package com.example.magda.systeminformacyjny.activities;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.view.CollapsibleActionView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.base.BaseActivity;
import com.example.magda.systeminformacyjny.base.Lifecycle;
import com.example.magda.systeminformacyjny.databinding.ItemsLayoutBinding;
import com.example.magda.systeminformacyjny.models.Place;
import com.example.magda.systeminformacyjny.network.ErrorResponse;
import com.example.magda.systeminformacyjny.network.SuccessResponse;
import com.example.magda.systeminformacyjny.view_models.ActivityPlacesViewModel;

import java.util.ArrayList;

/**
 * Created by piotrek on 22.04.17.
 */

public class PlacesActivity extends BaseActivity{

    private ArrayList<Place> places;
    private ActivityPlacesViewModel viewModel;
    private RecyclerView recyclerView;
    private Toolbar toolbar;

    private String TITLE = "Podlokalizacje";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ItemsLayoutBinding binding = DataBindingUtil.setContentView(this, R.layout.items_layout);
        recyclerView = binding.recyclerView;
        toolbar = binding.toolbarLayout.toolbar;
        toolbar.setTitle(TITLE);
        toolbar.setTitleTextColor(Color.WHITE);

    }

    @Override
    protected Lifecycle.ViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onSuccess(SuccessResponse successResponse) {

    }

    @Override
    public void onError(ErrorResponse errorResponse) {

    }
}
