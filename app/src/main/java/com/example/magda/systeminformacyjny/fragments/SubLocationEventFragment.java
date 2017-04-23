package com.example.magda.systeminformacyjny.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.databinding.FragmentSubLocationEventBinding;

/**
 * Created by piotrek on 23.04.17.
 */

public class SubLocationEventFragment extends Fragment{

    private String name;
    private String description;
    private String date;

    public static final String DATE_TAG = "date";
    public static final String DESCRIPTION_TAG = "description";
    public static final String NAME_TAG = "name";

    public static SubLocationEventFragment getInstance(String date, String description, String name) {
        Bundle bundle = new Bundle();
        bundle.putString(DATE_TAG, date);
        bundle.putString(DESCRIPTION_TAG, description);
        bundle.putString(NAME_TAG, name);
        SubLocationEventFragment subLocationEventFragment = new SubLocationEventFragment();
        subLocationEventFragment.setArguments(bundle);
        return subLocationEventFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        date = getArguments().getString(DATE_TAG);
        name = getArguments().getString(NAME_TAG);
        description = getArguments().getString(DESCRIPTION_TAG);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentSubLocationEventBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sub_location_event,
                null,false);
        binding.setDate(date);
        binding.setDescription(description);
        binding.setName(name);
        return binding.getRoot();
    }
}
