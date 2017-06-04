package com.example.magda.systeminformacyjny.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Toast;

import com.example.magda.systeminformacyjny.base.BaseFragment;
import com.example.magda.systeminformacyjny.base.Lifecycle;
import com.example.magda.systeminformacyjny.databinding.ItemsLayoutBinding;
import com.example.magda.systeminformacyjny.models.MainPlace;
import com.example.magda.systeminformacyjny.network.ErrorResponse;
import com.example.magda.systeminformacyjny.network.SuccessResponse;
import com.example.magda.systeminformacyjny.utils.RecyclerViewMainPlacesAdapter;
import com.example.magda.systeminformacyjny.view_models.FragmentVisitedPlacesViewModel;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;

/**
 * Created by piotrek on 04.06.17.
 */

public class VisitedPlacesPageFragment extends BaseFragment{

    private RecyclerView recyclerView;
    private RecyclerViewMainPlacesAdapter recyclerViewAdapter;
    private FragmentVisitedPlacesViewModel viewModel;
    private ArrayList<MainPlace> mainPlaces;

    public static VisitedPlacesPageFragment getInstance() {
        return new VisitedPlacesPageFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ItemsLayoutBinding binding = ItemsLayoutBinding.inflate(inflater, container, false);
        this.recyclerView = binding.recyclerView;
        this.mainPlaces = new ArrayList<>();
        this.viewModel = new FragmentVisitedPlacesViewModel(this);
        this.viewModel.setMainPlaces(mainPlaces);
        setUpRecyclerView();
        this.viewModel.download();
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

    @Override
    public void onSuccess(SuccessResponse successResponse) {
        recyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(ErrorResponse errorResponse) {
        recyclerViewAdapter.notifyDataSetChanged();
    }

    private void setUpRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewMainPlacesAdapter(mainPlaces, null, viewModel, getContext(),
                false);
        SlideInRightAnimator itemAnimation = new SlideInRightAnimator(new AccelerateInterpolator());
        recyclerView.setItemAnimator(itemAnimation);
        layoutManager.scrollToPosition(0);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    public void notifyRecyclerView() {
        recyclerViewAdapter.notifyDataSetChanged();
    }
}
