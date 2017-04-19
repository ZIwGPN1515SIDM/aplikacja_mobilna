package com.example.magda.systeminformacyjny.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.databinding.FragmentEventsPlaceBinding;

/**
 * Created by JB on 2017-04-09.
 */

public class EventsPlaceFragment extends Fragment {

    private RecyclerView recyclerView;

    public static EventsPlaceFragment getInstance() {return new EventsPlaceFragment();}

    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable
                                  Bundle savedInstanceState) {
        FragmentEventsPlaceBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_events_place, null, false);
        recyclerView = binding.recyclerView;
        return binding.getRoot();
    }

}
