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
import com.example.magda.systeminformacyjny.models.Place;
import com.example.magda.systeminformacyjny.network.ErrorResponse;
import com.example.magda.systeminformacyjny.network.SuccessResponse;
import com.example.magda.systeminformacyjny.utils.RecyclerViewMainPlacesAdapter;
import com.example.magda.systeminformacyjny.utils.RecyclerViewPlacesAdapter;
import com.example.magda.systeminformacyjny.view_models.ActivityPlacesViewModel;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;

import static com.example.magda.systeminformacyjny.network.ErrorResponse.DOWNLOAD_ERROR;
import static com.example.magda.systeminformacyjny.network.SuccessResponse.DOWNLOAD_SUCCESS;

/**
 * Created by piotrek on 22.04.17.
 */

public class PlacesActivity extends BaseActivity{

    private ArrayList<Place> places;
    private ActivityPlacesViewModel viewModel;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private RecyclerViewPlacesAdapter recyclerViewAdapter;
    private Long namespaceId;

    private String TITLE = "Podlokalizacje";
    private static final String ERROR_INFO = "Błąd połączenia";
    public static final String NAMESPACE_ID_TAG = "namespaceId";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ItemsLayoutBinding binding = DataBindingUtil.setContentView(this, R.layout.items_layout);
        binding.setShowToolbar(true);
        this.namespaceId = getIntent().getLongExtra(NAMESPACE_ID_TAG, -1);
        places = new ArrayList<>();
        recyclerView = binding.recyclerView;
        toolbar = binding.toolbarLayout.toolbar;
        toolbar.setTitle(TITLE);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        viewModel = new ActivityPlacesViewModel(this);
        viewModel.setPlaces(places);
        viewModel.setNamespaceId(namespaceId);
        setUpRecyclerView();
        viewModel.downloadPlaces();
    }

    private void setUpRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewPlacesAdapter(recyclerView, places,
                false, null, viewModel, this);
        SlideInRightAnimator itemAnimation = new SlideInRightAnimator(new AccelerateInterpolator());
        recyclerView.setItemAnimator(itemAnimation);
        layoutManager.scrollToPosition(0);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Lifecycle.ViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void notifyRecyclerView() {
       recyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccess(SuccessResponse successResponse) {
        if(successResponse.getSuccessType() == DOWNLOAD_SUCCESS) {
            recyclerViewAdapter.notifyDataSetChanged();
        }else {
            throw new IllegalArgumentException("Wrong success response code");
        }
    }

    @Override
    public void onError(ErrorResponse errorResponse) {
        if(errorResponse.getErrorType() == DOWNLOAD_ERROR) {
            recyclerViewAdapter.notifyDataSetChanged();
            showToast(ERROR_INFO);
        }else {
            throw new IllegalArgumentException("Wrong error response code");
        }
    }
}
