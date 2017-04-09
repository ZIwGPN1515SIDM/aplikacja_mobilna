package com.example.magda.systeminformacyjny.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.databinding.FragmentSettingsBinding;
import com.example.magda.systeminformacyjny.utils.PreferencesManager;
import com.example.magda.systeminformacyjny.view_models.FragmentSettingsViewModel;

import static com.example.magda.systeminformacyjny.utils.Constants.BLUE_COLOR;
import static com.example.magda.systeminformacyjny.utils.Constants.GREEN_COLOR;
import static com.example.magda.systeminformacyjny.utils.Constants.RED_COLOR;

/**
 * Created by piotrek on 06.04.17.
 */

public class SettingsPageFragment extends Fragment{

    private Spinner colorSpinner;
    private Spinner measureSpinner;

    private String[] colors;
    private String[] measures;

    private FragmentSettingsViewModel viewModel;

    public static Fragment getInstance() {
        return new SettingsPageFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentSettingsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, null, false);
        viewModel = new FragmentSettingsViewModel(getContext());
        setUpSpinners(binding);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    private void setUpSpinners(FragmentSettingsBinding binding) {
        measures = getResources().getStringArray(R.array.measure_array);
        colors = getResources().getStringArray(R.array.color_array);

        ArrayAdapter<String> measuresAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, measures);
        ArrayAdapter<String> colorsAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, colors);

        colorSpinner = binding.colorsSpinner;
        colorSpinner.setAdapter(colorsAdapter);
        colorSpinner.setSelection(getSpinnerColorRoutePosition());
        measureSpinner = binding.measuresSpinner;
        measureSpinner.setAdapter(measuresAdapter);
        measureSpinner.setSelection(PreferencesManager.measureType(getContext()));
        colorSpinner.setOnItemSelectedListener(viewModel.createColorAdapterListener());
        measureSpinner.setOnItemSelectedListener(viewModel.createMeasureAdapterListener());

    }


    private int getSpinnerColorRoutePosition() {
        int tmpPosition;
        switch (PreferencesManager.routeColor(getContext())) {
            case Color.RED:
                tmpPosition = RED_COLOR;
                break;
            case Color.GREEN:
                tmpPosition = GREEN_COLOR;
                break;
            case Color.BLUE:
                tmpPosition = BLUE_COLOR;
                break;
            default:
                tmpPosition = RED_COLOR;
        }
        return tmpPosition;
    }



}
