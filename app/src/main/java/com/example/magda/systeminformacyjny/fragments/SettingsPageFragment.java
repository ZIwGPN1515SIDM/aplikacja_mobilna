package com.example.magda.systeminformacyjny.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.databinding.FragmentSettingsBinding;
import com.example.magda.systeminformacyjny.view_models.FragmentSettingsViewModel;

/**
 * Created by piotrek on 06.04.17.
 */

public class SettingsPageFragment extends Fragment{

    private Spinner colorSpinner;
    private Spinner measureSpinner;

    private FragmentSettingsViewModel viewModel;

    public static Fragment getInstance() {
        return new SettingsPageFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentSettingsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, null, false);
        viewModel = new FragmentSettingsViewModel();
        setUpSpinners(binding);
        return binding.getRoot();
    }


    private void setUpSpinners(FragmentSettingsBinding binding) {
        colorSpinner = binding.colorsSpinner;
        measureSpinner = binding.measuresSpinner;
        colorSpinner.setOnItemSelectedListener(viewModel);
        measureSpinner.setOnItemSelectedListener(viewModel);
    }

}
