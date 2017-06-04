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
import com.example.magda.systeminformacyjny.network.ErrorResponse;
import com.example.magda.systeminformacyjny.network.SuccessResponse;
import com.example.magda.systeminformacyjny.network.items.PathName;
import com.example.magda.systeminformacyjny.utils.Constants;
import com.example.magda.systeminformacyjny.utils.RecyclerViewCurrentPathAdapter;
import com.example.magda.systeminformacyjny.view_models.ActivityMyPathsViewModel;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;

import static com.example.magda.systeminformacyjny.utils.Constants.CURRENT_ROAD;

/**
 * Created by piotrek on 03.06.17.
 */

public class MyPathsActivity extends BaseActivity{

    public static final String PATH_ID_TAG = "idPath";
    public static final String TOOLBAR_TITLE = "toolbarTitle";
    private long pathId;
    private RecyclerView recyclerView;
    private ArrayList<PathName> pathNames;
    private ActivityMyPathsViewModel viewModel;
    private RecyclerViewCurrentPathAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ItemsLayoutBinding binding = DataBindingUtil.setContentView(this, R.layout.items_layout);
        String toolbarTitle = getIntent().getStringExtra(TOOLBAR_TITLE);
        binding.setShowToolbar(true);
        this.recyclerView = binding.recyclerView;
        this.pathId = getIntent().getLongExtra(PATH_ID_TAG, -1L);
        this.pathNames = new ArrayList<>();
        this.viewModel = new ActivityMyPathsViewModel(this);
        this.viewModel.setPathNames(pathNames);
        this.viewModel.setPathId(pathId);
        setUpRecyclerView();
        Toolbar toolbar = binding.toolbarLayout.toolbar;
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(toolbarTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        viewModel.download();
    }

    private void setUpRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewCurrentPathAdapter(pathNames, viewModel);
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

    @Override
    public void onSuccess(SuccessResponse successResponse) {
        recyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(ErrorResponse errorResponse) {
        recyclerViewAdapter.notifyDataSetChanged();
    }
}
