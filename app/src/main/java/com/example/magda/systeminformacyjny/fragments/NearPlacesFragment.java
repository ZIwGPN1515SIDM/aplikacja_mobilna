package com.example.magda.systeminformacyjny.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.example.magda.systeminformacyjny.databinding.FragmentNearPlacesBinding;
import com.example.magda.systeminformacyjny.models.NearPlace;
import com.example.magda.systeminformacyjny.network.ErrorResponse;
import com.example.magda.systeminformacyjny.network.SuccessResponse;
import com.example.magda.systeminformacyjny.utils.RecyclerViewNearPlacesAdapter;
import com.example.magda.systeminformacyjny.utils.RecyclerViewPathNamesAdapter;
import com.example.magda.systeminformacyjny.view_models.FragmentNearPlacesViewModel;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;

/**
 * Created by piotrek on 09.04.17.
 */

public class NearPlacesFragment extends BaseFragment{

    private FragmentNearPlacesViewModel viewModel;
    private RecyclerView recyclerView;
    private RecyclerViewNearPlacesAdapter recyclerViewAdapter;
    private ArrayList<NearPlace> nearPlaces;

    public static NearPlacesFragment getInstance() {
        return new NearPlacesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentNearPlacesBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_near_places, null, false);
        viewModel = new FragmentNearPlacesViewModel(this);
        recyclerView = binding.recyclerView;
        this.nearPlaces = new ArrayList<>();
        nearPlaces.add(null);
        this.viewModel.setNearPlaces(nearPlaces);
        setUpRecyclerView();
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onStop() {
        super.onStop();
        viewModel.onStop();
    }

    @Override
    protected Lifecycle.ViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.onStart();
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

    }

    private void setUpRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewNearPlacesAdapter(nearPlaces);
        SlideInRightAnimator itemAnimation = new SlideInRightAnimator(new AccelerateInterpolator());
        recyclerView.setItemAnimator(itemAnimation);
        layoutManager.scrollToPosition(0);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    public void notifyRecyclerView() {
        recyclerViewAdapter.notifyDataSetChanged();
    }
}
