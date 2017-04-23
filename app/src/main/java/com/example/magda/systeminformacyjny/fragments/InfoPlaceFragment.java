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
import com.example.magda.systeminformacyjny.models.IPlaceItem;
import com.example.magda.systeminformacyjny.models.MainPlace;

/**
 * Created by JB on 2017-04-09.
 */

public class InfoPlaceFragment extends Fragment {

    private IPlaceItem place;
    private static final String MAIN_PLACE_TAG = "place";

    public static InfoPlaceFragment getInstance(IPlaceItem place) {

        Bundle bundle = new Bundle();
        bundle.putSerializable(MAIN_PLACE_TAG, place);
        InfoPlaceFragment infoPlaceFragment = new InfoPlaceFragment();
        infoPlaceFragment.setArguments(bundle);
        return infoPlaceFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        place = (IPlaceItem) getArguments().getSerializable(MAIN_PLACE_TAG);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentInfoPlaceBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_info_place, null, false);
        binding.setPlace(place);
        return binding.getRoot();
    }
}
