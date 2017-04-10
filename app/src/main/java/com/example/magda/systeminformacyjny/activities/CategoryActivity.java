package com.example.magda.systeminformacyjny.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AccelerateInterpolator;

import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.databinding.ItemsLayoutBinding;
import com.example.magda.systeminformacyjny.models.Category;
import com.example.magda.systeminformacyjny.utils.Constants;
import com.example.magda.systeminformacyjny.utils.RecyclerViewCategoriesAdapter;
import com.example.magda.systeminformacyjny.utils.RecyclerViewMainPlacesAdapter;
import com.example.magda.systeminformacyjny.view_models.ActivityCategoriesViewModel;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;

import static com.example.magda.systeminformacyjny.utils.Constants.FULL_SCREEN_PROGRESS_BAR;

/**
 * Created by piotrek on 10.04.17.
 */

public class CategoryActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private ArrayList<Category> categories;
    private RecyclerViewCategoriesAdapter recyclerViewAdapter;
    private ActivityCategoriesViewModel viewModel;

    private static final String CATEGORIES_TAG = "categories";

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
        viewModel = new ActivityCategoriesViewModel(this);
        viewModel.setCategories(categories);
        recyclerView = binding.recyclerView;
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewCategoriesAdapter(recyclerView, categories,
                true, viewModel, viewModel);
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
}
