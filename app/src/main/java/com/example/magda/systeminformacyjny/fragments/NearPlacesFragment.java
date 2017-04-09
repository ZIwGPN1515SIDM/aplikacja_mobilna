package com.example.magda.systeminformacyjny.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.databinding.FragmentNearPlacesBinding;
import com.example.magda.systeminformacyjny.view_models.NearPlacesFragmentViewModel;

/**
 * Created by piotrek on 09.04.17.
 */

public class NearPlacesFragment extends Fragment{

    private NearPlacesFragmentViewModel viewModel;

    public static NearPlacesFragment getInstance() {
        return new NearPlacesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentNearPlacesBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_near_places, null, false);
        viewModel = new NearPlacesFragmentViewModel(this);
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        viewModel.onDestroy();
        super.onDestroy();
    }


    @Override
    public void onStop() {
        viewModel.onStop();
        super.onStop();
    }

    @Override
    public void onStart() {
        viewModel.onStart();
        super.onStart();
    }
}
