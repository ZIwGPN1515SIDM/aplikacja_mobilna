package com.example.magda.systeminformacyjny.activities;

import android.content.Intent;
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
import com.example.magda.systeminformacyjny.models.Category;
import com.example.magda.systeminformacyjny.models.MainPlace;
import com.example.magda.systeminformacyjny.network.ErrorResponse;
import com.example.magda.systeminformacyjny.network.SuccessResponse;
import com.example.magda.systeminformacyjny.utils.RecyclerViewCategoriesAdapter;
import com.example.magda.systeminformacyjny.view_models.ActivityCategoriesViewModel;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;

import static com.example.magda.systeminformacyjny.network.ErrorResponse.DOWNLOAD_ERROR;
import static com.example.magda.systeminformacyjny.network.SuccessResponse.DOWNLOAD_SUCCESS;
import static com.example.magda.systeminformacyjny.utils.Constants.CURRENT_ROAD_ACTIVITY_REQUEST_CODE;
import static com.example.magda.systeminformacyjny.utils.Constants.SHOW_SETTINGS_MAIN_PLACE_ITEM;
import static com.example.magda.systeminformacyjny.utils.Constants.CURRENT_ROAD;
import static com.example.magda.systeminformacyjny.utils.Constants.ERROR_INFO_VIEW_HOLDER;
import static com.example.magda.systeminformacyjny.utils.Constants.FULL_SCREEN_PROGRESS_BAR;

/**
 * Created by piotrek on 10.04.17.
 */

public class CategoriesActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private ArrayList<Category> categories;
    private RecyclerViewCategoriesAdapter recyclerViewAdapter;
    private ActivityCategoriesViewModel viewModel;
    private ArrayList<MainPlace> currentRoad;
    private boolean settingsMainPlaceButton;

    private static final String CATEGORIES_TAG = "categories";
    private static final String TITLE = "Kategorie";
    private static final String ERROR_MESSAGE = "Bład pobierania danych";
    private static final String CURRENT_ROAD_TAG = "currentRoad";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ItemsLayoutBinding binding = DataBindingUtil.setContentView(this, R.layout.items_layout);
        binding.setShowToolbar(true);
        this.settingsMainPlaceButton = getIntent().getBooleanExtra(SHOW_SETTINGS_MAIN_PLACE_ITEM, false);
        if (savedInstanceState != null) {
            categories = (ArrayList<Category>) savedInstanceState.getSerializable(CATEGORIES_TAG);
            currentRoad = (ArrayList<MainPlace>) savedInstanceState.getSerializable(CURRENT_ROAD_TAG);
        } else {
            categories = new ArrayList<>();
            categories.add(new Category(FULL_SCREEN_PROGRESS_BAR));
            currentRoad = (ArrayList<MainPlace>) getIntent().getSerializableExtra(CURRENT_ROAD);
        }

        Toolbar toolbar = binding.toolbarLayout.toolbar;
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(TITLE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.currentRoad = new ArrayList<>();
        viewModel = new ActivityCategoriesViewModel(this);
        viewModel.setCategories(categories);
        recyclerView = binding.recyclerView;
        setUpRecyclerView();
        viewModel.download();
    }

    private void setUpRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewCategoriesAdapter(categories, viewModel, this);
        SlideInRightAnimator itemAnimation = new SlideInRightAnimator(new AccelerateInterpolator());
        recyclerView.setItemAnimator(itemAnimation);
        layoutManager.scrollToPosition(0);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(CATEGORIES_TAG, categories);
        outState.putSerializable(CURRENT_ROAD_TAG, currentRoad);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CURRENT_ROAD_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                currentRoad = (ArrayList<MainPlace>) data.getSerializableExtra(CURRENT_ROAD);
            }
        } else
            super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected Lifecycle.ViewModel getViewModel() {
        return viewModel;
    }

    public void recyclerViewNotify() {
        recyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(SuccessResponse successResponse) {
        switch (successResponse.getSuccessType()) {
            case DOWNLOAD_SUCCESS:
                recyclerViewAdapter.notifyDataSetChanged();
                break;
            default:
                throw new IllegalArgumentException("Wrong success type " + successResponse.getSuccessType());
        }
    }

    @Override
    public void onError(ErrorResponse errorResponse) {
        switch (errorResponse.getErrorType()) {
            case DOWNLOAD_ERROR:
                categories.clear();
                categories.add(new Category(ERROR_INFO_VIEW_HOLDER));
                recyclerViewAdapter.notifyDataSetChanged();
                showToast(ERROR_MESSAGE);
                break;
            default:
                throw new IllegalArgumentException("Wrong error type " + errorResponse.getErrorType());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent();
            intent.putExtra(CURRENT_ROAD, currentRoad);
            setResult(RESULT_OK, intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public ArrayList<MainPlace> getCurrentRoad() {
        return currentRoad;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(CURRENT_ROAD, currentRoad);
        setResult(RESULT_OK, intent);
        finish();
    }

    public boolean isSettingsMainPlaceButton() {
        return settingsMainPlaceButton;
    }
}
