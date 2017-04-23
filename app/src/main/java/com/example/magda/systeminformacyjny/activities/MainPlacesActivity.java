package com.example.magda.systeminformacyjny.activities;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.animation.AccelerateInterpolator;
import android.widget.Toast;

import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.base.BaseActivity;
import com.example.magda.systeminformacyjny.base.Lifecycle;
import com.example.magda.systeminformacyjny.databinding.ItemsLayoutBinding;
import com.example.magda.systeminformacyjny.models.IPlaceItem;
import com.example.magda.systeminformacyjny.models.MainPlace;
import com.example.magda.systeminformacyjny.network.ErrorResponse;
import com.example.magda.systeminformacyjny.network.SuccessResponse;
import com.example.magda.systeminformacyjny.utils.RecyclerViewPlacesAdapter;
import com.example.magda.systeminformacyjny.view_models.ActivityMainPlacesListViewModel;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;

import static com.example.magda.systeminformacyjny.network.ErrorResponse.DOWNLOAD_ERROR;
import static com.example.magda.systeminformacyjny.network.SuccessResponse.DOWNLOAD_SUCCESS;
import static com.example.magda.systeminformacyjny.utils.Constants.ERROR_INFO_VIEW_HOLDER;

/**
 * Created by Wojciech on 13.04.2017.
 */

public class MainPlacesActivity extends BaseActivity {

    private ActivityMainPlacesListViewModel viewModel;
    private RecyclerView recyclerView;
    private ArrayList<MainPlace> mainPlaces;
    private RecyclerViewPlacesAdapter recyclerViewAdapter;
    private String title;
    private Long categoryId;

    public static final String TITLE = "categoryTitle";
    public static final String CATEGORY_ID = "categoryId";
    public static final int LOCATION_ACTIVITY_CODE = 10000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ItemsLayoutBinding binding = DataBindingUtil.setContentView(this, R.layout.items_layout);
        viewModel = new ActivityMainPlacesListViewModel(this);
        this.title = getIntent().getStringExtra(TITLE);
        this.categoryId = getIntent().getLongExtra(CATEGORY_ID, -1L);
        recyclerView = binding.recyclerView;
        mainPlaces = new ArrayList<>();
        Toolbar toolbar = binding.toolbarLayout.toolbar;
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setUpRecyclerView();
        viewModel.setCategoryId(categoryId);
        viewModel.setMainPlaces(mainPlaces);
        viewModel.setCategoryName(title);
        viewModel.downloadMainPLaces();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(CATEGORY_ID, categoryId);
        outState.putString(TITLE, title);
    }

    private void setUpRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewPlacesAdapter(recyclerView, mainPlaces,
                false, null, viewModel, this);
        SlideInRightAnimator itemAnimation = new SlideInRightAnimator(new AccelerateInterpolator());
        recyclerView.setItemAnimator(itemAnimation);
        layoutManager.scrollToPosition(0);
        recyclerView.setAdapter(recyclerViewAdapter);
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
        if (successResponse.getSuccessType() == DOWNLOAD_SUCCESS) {
            notifyRecyclerViewAdapter();
        } else {
            throw new IllegalArgumentException("Wrong success response code " + successResponse.getSuccessType());
        }
    }

    @Override
    public void onError(ErrorResponse errorResponse) {
        if (errorResponse.getErrorType() == DOWNLOAD_ERROR) {
            mainPlaces.clear();
            mainPlaces.add(new MainPlace(ERROR_INFO_VIEW_HOLDER));
            notifyRecyclerViewAdapter();
        } else {
            throw new IllegalArgumentException("Wrong error response code " + errorResponse.getErrorType());
        }
    }

    public void notifyRecyclerViewAdapter() {
        recyclerViewAdapter.notifyDataSetChanged();
    }
}
