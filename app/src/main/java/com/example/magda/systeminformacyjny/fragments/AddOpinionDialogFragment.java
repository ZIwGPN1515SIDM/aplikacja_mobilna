package com.example.magda.systeminformacyjny.fragments;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.generated.callback.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.databinding.AddOpinionDialogBinding;
import com.example.magda.systeminformacyjny.view_models.FragmentRatingPlaceViewModel;

import io.reactivex.Observable;


/**
 * Created by JB on 2017-04-24.
 */

public class AddOpinionDialogFragment extends DialogFragment {

    private FragmentRatingPlaceViewModel viewModel;

    private static final String EMPTY_ERROR_INFO = "Nie można dodac pustej opinii";

    public static AddOpinionDialogFragment getInstance() {
        return new AddOpinionDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        AddOpinionDialogBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.add_opinion_dialog, null, false);
        binding.back.setOnClickListener((v) -> this.dismiss());
        binding.send.setOnClickListener((v) -> {
            if(viewModel != null) {
                String content = binding.opinion.getText().toString();
                float score = binding.ratingBar.getRating();
                if(content.trim().length() > 0 ) {
                    viewModel.sendComment(content, score);
                    dismiss();
                }else { //jak pusty strng to daj toasta z enie mozna wyslac pustego
                    Toast.makeText(getContext(), EMPTY_ERROR_INFO, Toast.LENGTH_SHORT).show();
                }
            }else {
                dismiss();
            }

        });
        return binding.getRoot();
    }

    public void setViewModel(FragmentRatingPlaceViewModel viewModel) {
        this.viewModel = viewModel;
    }
}
