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
import com.example.magda.systeminformacyjny.models.Category;
import com.example.magda.systeminformacyjny.utils.RecyclerViewCategoriesAdapter;
import com.example.magda.systeminformacyjny.view_models.ActivityCategoriesViewModel;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;

import static com.example.magda.systeminformacyjny.utils.Constants.FULL_SCREEN_PROGRESS_BAR;

/**
 * Created by piotrek on 10.04.17.
 */

public class CategoryActivity extends BaseActivity{

    private RecyclerView recyclerView;
    private ArrayList<Category> categories;
    private RecyclerViewCategoriesAdapter recyclerViewAdapter;
    private ActivityCategoriesViewModel viewModel;

    private static final String CATEGORIES_TAG = "categories";
    private static final String TITLE = "Kategorie";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ItemsLayoutBinding binding = DataBindingUtil.setContentView(this, R.layout.items_layout);
        if(savedInstanceState != null) {
            categories = (ArrayList<Category>) savedInstanceState.getSerializable(CATEGORIES_TAG);
        }else {
            categories = new ArrayList<>();
            categories.add(new Category(FULL_SCREEN_PROGRESS_BAR));
        }

        Toolbar toolbar = binding.toolbarLayout.toolbar;
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(TITLE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        viewModel = new ActivityCategoriesViewModel(this);
        viewModel.setCategories(categories);
        recyclerView = binding.recyclerView;
        setUpRecyclerView();
        viewModel.download();
    }

    private void setUpRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewCategoriesAdapter(recyclerView, categories,
                false, null, viewModel);
        SlideInRightAnimator itemAnimation = new SlideInRightAnimator(new AccelerateInterpolator());
        recyclerView.setItemAnimator(itemAnimation);
        layoutManager.scrollToPosition(0);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(CATEGORIES_TAG, categories);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
