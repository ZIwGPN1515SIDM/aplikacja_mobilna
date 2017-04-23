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
import com.example.magda.systeminformacyjny.databinding.FragmentCreateRouteBinding;
import com.example.magda.systeminformacyjny.models.MainPlace;
import com.example.magda.systeminformacyjny.utils.RecyclerViewPlacesAdapter;
import com.example.magda.systeminformacyjny.view_models.FragmentCreateRouteViewModel;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;

/**
 * Created by piotrek on 10.04.17.
 */

public class CreateRouteFragment extends Fragment{

    private ArrayList<MainPlace> mainPlaces;
    private static final String MAIN_PLACES_TAG = "mainPlaces";
    private FragmentCreateRouteViewModel viewModel;
    private RecyclerView recyclerView;
    private RecyclerViewPlacesAdapter recyclerViewAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState!= null) {
            mainPlaces = (ArrayList<MainPlace>) savedInstanceState.getSerializable(MAIN_PLACES_TAG);
        }else {
            mainPlaces = new ArrayList<>();
            mainPlaces.add(null);
        }

    }

    public static CreateRouteFragment getInstance() {
        return new CreateRouteFragment();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(MAIN_PLACES_TAG, mainPlaces);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentCreateRouteBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_route, null, false);
        viewModel = new FragmentCreateRouteViewModel(this);
        viewModel.setMainPlaces(mainPlaces);
        binding.setViewModel(viewModel);
        recyclerView = binding.recyclerView;
        setUpRecyclerView();
        return binding.getRoot();
    }

    private void setUpRecyclerView() {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewPlacesAdapter(recyclerView, mainPlaces,
                true, viewModel, viewModel, null); //TODO interfejs
        SlideInRightAnimator itemAnimation = new SlideInRightAnimator(new AccelerateInterpolator());
        recyclerView.setItemAnimator(itemAnimation);
        layoutManager.scrollToPosition(0);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
