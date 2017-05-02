package com.example.magda.systeminformacyjny.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.databinding.AddOpinionDialogBinding;


/**
 * Created by JB on 2017-04-24.
 */

public class AddOpinionDialogFragment extends DialogFragment {

    public static AddOpinionDialogFragment getInstance() {return new AddOpinionDialogFragment();}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        AddOpinionDialogBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.add_opinion_dialog, null, false);

        binding.back.setOnClickListener((v)->this.dismiss());
        return binding.getRoot();
    }

}
