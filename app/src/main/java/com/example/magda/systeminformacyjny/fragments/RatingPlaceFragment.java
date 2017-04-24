package com.example.magda.systeminformacyjny.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.databinding.FragmentInfoPlaceBinding;
import com.example.magda.systeminformacyjny.databinding.FragmentRatingPlaceBinding;

/**
 * Created by JB on 2017-04-09.
 */

public class RatingPlaceFragment extends Fragment {

    public static RatingPlaceFragment getInstance() {return new RatingPlaceFragment();}

    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable
                                  Bundle savedInstanceState) {
        FragmentRatingPlaceBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rating_place, null, false);
        binding.addOpinionButton.setOnClickListener(v ->  {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            DialogFragment dialogFragment = AddOpinionDialogFragment.getInstance();
            dialogFragment.show(fragmentTransaction, "dialog");
        });
        return binding.getRoot();

    }


}
