package com.example.magda.systeminformacyjny.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.databinding.FragmentAboutUsBinding;

/**
 * Created by piotrek on 08.04.17.
 */

public class AboutUsFragment extends Fragment{


    public static AboutUsFragment getInstance() {
        return new AboutUsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentAboutUsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about_us, null, false);

        return binding.getRoot();
    }
}
