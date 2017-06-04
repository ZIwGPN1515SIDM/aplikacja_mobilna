package com.example.magda.systeminformacyjny.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Toast;

import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.base.BaseFragment;
import com.example.magda.systeminformacyjny.base.Lifecycle;
import com.example.magda.systeminformacyjny.databinding.ItemsLayoutBinding;
import com.example.magda.systeminformacyjny.network.ErrorResponse;
import com.example.magda.systeminformacyjny.network.SuccessResponse;
import com.example.magda.systeminformacyjny.network.items.PathName;
import com.example.magda.systeminformacyjny.network.items.PathResponse;
import com.example.magda.systeminformacyjny.utils.RecyclerViewCategoriesAdapter;
import com.example.magda.systeminformacyjny.utils.RecyclerViewPathNamesAdapter;
import com.example.magda.systeminformacyjny.view_models.FragmentMyPathsViewModel;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;

/**
 * Created by piotrek on 01.06.17.
 */

public class MyPathsFragment extends BaseFragment{

    private ArrayList<PathResponse> pathNames;
    private FragmentMyPathsViewModel viewModel;
    private RecyclerView recyclerView;
    private RecyclerViewPathNamesAdapter recyclerViewAdapter;

    public static MyPathsFragment getInstance() {
        return new MyPathsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ItemsLayoutBinding binding = ItemsLayoutBinding.inflate(inflater, container, false);
        binding.setShowToolbar(false);
        this.pathNames = new ArrayList<>();
        this.viewModel = new FragmentMyPathsViewModel(this);
        recyclerView = binding.recyclerView;
        setUpRecyclerView();
        viewModel.setPathNames(pathNames);
        viewModel.download();
        return binding.getRoot();
    }

    @Override
    protected Lifecycle.ViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void setUpRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewPathNamesAdapter(pathNames, viewModel);
        SlideInRightAnimator itemAnimation = new SlideInRightAnimator(new AccelerateInterpolator());
        recyclerView.setItemAnimator(itemAnimation);
        layoutManager.scrollToPosition(0);
        recyclerView.setAdapter(recyclerViewAdapter);
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
