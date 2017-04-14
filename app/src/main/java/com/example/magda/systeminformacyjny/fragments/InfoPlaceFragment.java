package com.example.magda.systeminformacyjny.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.databinding.FragmentInfoPlaceBinding;

/**
 * Created by JB on 2017-04-09.
 */

public class InfoPlaceFragment extends Fragment {

    public static InfoPlaceFragment getInstance() {return new InfoPlaceFragment();}

    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable
    ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        FragmentInfoPlaceBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_info_place, null, false);
        return binding.getRoot();
    }

}
