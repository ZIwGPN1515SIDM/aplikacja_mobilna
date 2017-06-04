package com.example.magda.systeminformacyjny.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.base.BaseFragment;
import com.example.magda.systeminformacyjny.base.Lifecycle;
import com.example.magda.systeminformacyjny.databinding.FragmentSettingsBinding;
import com.example.magda.systeminformacyjny.network.ErrorResponse;
import com.example.magda.systeminformacyjny.network.SuccessResponse;
import com.example.magda.systeminformacyjny.utils.Constants;
import com.example.magda.systeminformacyjny.utils.PreferencesManager;
import com.example.magda.systeminformacyjny.view_models.FragmentSettingsViewModel;

import static com.example.magda.systeminformacyjny.network.ErrorResponse.SEND_NEWSLETTER_ERROR;
import static com.example.magda.systeminformacyjny.network.SuccessResponse.SEND_NEWSLETTER_SUCCESS;
import static com.example.magda.systeminformacyjny.utils.Constants.BLUE_COLOR;
import static com.example.magda.systeminformacyjny.utils.Constants.GREEN_COLOR;
import static com.example.magda.systeminformacyjny.utils.Constants.RED_COLOR;

/**
 * Created by piotrek on 06.04.17.
 */

public class SettingsPageFragment extends BaseFragment{

    private Spinner colorSpinner;
    private Spinner measureSpinner;
    private String[] colors;
    private String[] measures;
    private SwitchCompat switchCompat;
    private FragmentSettingsViewModel viewModel;


    private static final String ERROR_INFO = "Błąd polączenia !";

    public static Fragment getInstance() {
        return new SettingsPageFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentSettingsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, null, false);
        viewModel = new FragmentSettingsViewModel(this);
        setUpSpinners(binding);
        binding.setViewModel(viewModel);
        switchCompat = binding.switchCompact;
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
                PreferencesManager.setRouteColor(getContext(), Constants.RED_COLOR);
                tmpPosition = Constants.RED_COLOR;
                break;
            case Color.GREEN:
                PreferencesManager.setRouteColor(getContext(), Constants.GREEN_COLOR);
                tmpPosition = Constants.GREEN_COLOR;
                break;
            case Color.BLUE:
                PreferencesManager.setRouteColor(getContext(), Constants.BLUE_COLOR);
                tmpPosition = Constants.BLUE_COLOR;
                break;
            default:
                PreferencesManager.setRouteColor(getContext(), Constants.RED_COLOR);
                tmpPosition = Constants.RED_COLOR;
        }
        return tmpPosition;
    }


    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(SuccessResponse successResponse) {
        if(successResponse.getSuccessType() == SEND_NEWSLETTER_SUCCESS) {
            boolean previousFlag = PreferencesManager.isNewsletter(getContext());
            PreferencesManager.setNewsletter(getContext(), !previousFlag);
            PreferencesManager.setNewsletter(getContext(),
                    !previousFlag);
        }else {
            throw new IllegalArgumentException("Wrong success response code "
                    + successResponse.getSuccessType());
        }
    }

    @Override
    public void onError(ErrorResponse errorResponse) {
        if(errorResponse.getErrorType() == SEND_NEWSLETTER_ERROR) {
            viewModel.setSendNewsletterError(true);
            switchCompat.setChecked(PreferencesManager.isNewsletter(getContext()));
            viewModel.setSendNewsletterError(false);
            showToast(ERROR_INFO);
        }else {
            throw new IllegalArgumentException("Wrong error response code "
                    + errorResponse.getErrorType());
        }
    }

    @Override
    protected Lifecycle.ViewModel getViewModel() {
        return viewModel;
    }
}
