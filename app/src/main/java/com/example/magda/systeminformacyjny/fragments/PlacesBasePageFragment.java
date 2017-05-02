package com.example.magda.systeminformacyjny.fragments;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Toast;

import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.base.BaseFragment;
import com.example.magda.systeminformacyjny.base.Lifecycle;
import com.example.magda.systeminformacyjny.databinding.ItemsLayoutBinding;
import com.example.magda.systeminformacyjny.models.Category;
import com.example.magda.systeminformacyjny.network.ErrorResponse;
import com.example.magda.systeminformacyjny.network.SuccessResponse;
import com.example.magda.systeminformacyjny.utils.RecyclerViewCategoriesAdapter;
import com.example.magda.systeminformacyjny.view_models.ActivityCategoriesViewModel;
import com.example.magda.systeminformacyjny.view_models.FragmentCategoriesViewModel;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;

import static com.example.magda.systeminformacyjny.network.ErrorResponse.DOWNLOAD_ERROR;
import static com.example.magda.systeminformacyjny.network.SuccessResponse.DOWNLOAD_SUCCESS;
import static com.example.magda.systeminformacyjny.utils.Constants.ERROR_INFO_VIEW_HOLDER;
import static com.example.magda.systeminformacyjny.utils.Constants.FULL_SCREEN_PROGRESS_BAR;

/**
 * Created by JB on 2017-04-25.
 */

public class PlacesBasePageFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private ArrayList<Category> categories;
    private RecyclerViewCategoriesAdapter recyclerViewAdapter;
    private FragmentCategoriesViewModel viewModel;

    private static final String CATEGORIES_TAG = "categories";
    private static final String ERROR_MESSAGE = "Bład pobierania danych";

    public static PlacesBasePageFragment getInstance() { return new PlacesBasePageFragment(); }

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ItemsLayoutBinding binding = DataBindingUtil.inflate(inflater, R.layout.items_layout, null, false);
        binding.setShowToolbar(false);
        if(savedInstanceState != null) {
            categories = (ArrayList<Category>) savedInstanceState.getSerializable(CATEGORIES_TAG);
        }else {
            categories = new ArrayList<>();
            categories.add(new Category(FULL_SCREEN_PROGRESS_BAR));
        }

        viewModel = new FragmentCategoriesViewModel(this);
        viewModel.setCategories(categories);
        recyclerView = binding.recyclerView;
        setUpRecyclerView();
        viewModel.download();
        return binding.getRoot();
    }

    private void setUpRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewCategoriesAdapter(recyclerView, categories,
                false, null, viewModel, getContext());
        SlideInRightAnimator itemAnimation = new SlideInRightAnimator(new AccelerateInterpolator());
        recyclerView.setItemAnimator(itemAnimation);
        layoutManager.scrollToPosition(0);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    public void recyclerViewNotify() {
        recyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    protected Lifecycle.ViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(SuccessResponse successResponse) {
        switch(successResponse.getSuccessType()) {
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
}
