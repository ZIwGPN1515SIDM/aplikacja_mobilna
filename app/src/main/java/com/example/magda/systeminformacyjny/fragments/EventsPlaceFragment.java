package com.example.magda.systeminformacyjny.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Toast;

import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.base.BaseFragment;
import com.example.magda.systeminformacyjny.base.Lifecycle;
import com.example.magda.systeminformacyjny.databinding.FragmentEventsPlaceBinding;
import com.example.magda.systeminformacyjny.models.Event;
import com.example.magda.systeminformacyjny.network.ErrorResponse;
import com.example.magda.systeminformacyjny.network.SuccessResponse;
import com.example.magda.systeminformacyjny.utils.RecyclerViewEventsAdapter;
import com.example.magda.systeminformacyjny.view_models.FragmentEventsPlaceViewModel;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;

import static com.example.magda.systeminformacyjny.network.ErrorResponse.DOWNLOAD_ERROR;
import static com.example.magda.systeminformacyjny.network.SuccessResponse.DOWNLOAD_SUCCESS;
import static com.example.magda.systeminformacyjny.utils.Constants.FULL_SCREEN_PROGRESS_BAR;

/**
 * Created by JB on 2017-04-09.
 */

public class EventsPlaceFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private RecyclerViewEventsAdapter recyclerViewAdapter;
    private ArrayList<Event> events;
    private String mainNamespace;
    private FragmentEventsPlaceViewModel viewModel;
    private String categoryName;

    private static final String NAMESPACE_TAG = "namespace";
    private static final String ERROR_INFO = "Błąd połaczenia !";

    public static EventsPlaceFragment getInstance(String namespace) {
        Bundle bundle = new Bundle();
        bundle.putString(NAMESPACE_TAG, namespace);
        EventsPlaceFragment eventsPlaceFragment = new EventsPlaceFragment();
        eventsPlaceFragment.setArguments(bundle);
        return eventsPlaceFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainNamespace = getArguments().getString(NAMESPACE_TAG);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable
                                  Bundle savedInstanceState) {
        FragmentEventsPlaceBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_events_place, null, false);
        recyclerView = binding.recyclerView;
        events = new ArrayList<>();
        events.add(new Event(FULL_SCREEN_PROGRESS_BAR));
        viewModel = new FragmentEventsPlaceViewModel(this);
        setUpRecyclerView();
        viewModel.setEvents(events);
        viewModel.setNamespace(mainNamespace);
        viewModel.downloadEvents();
        return binding.getRoot();
    }

    private void setUpRecyclerView() {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewEventsAdapter(events,viewModel);
        SlideInRightAnimator itemAnimation = new SlideInRightAnimator(new AccelerateInterpolator());
        recyclerView.setItemAnimator(itemAnimation);
        layoutManager.scrollToPosition(0);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    protected Lifecycle.ViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(SuccessResponse successResponse) {
        if(successResponse.getSuccessType() == DOWNLOAD_SUCCESS) {
            recyclerViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(ErrorResponse errorResponse) {
        if(errorResponse.getErrorType() == DOWNLOAD_ERROR) {
            showToast(ERROR_INFO);
            recyclerViewAdapter.notifyDataSetChanged();
        }
    }

    public void notifyDataSetChange() {
        recyclerViewAdapter.notifyDataSetChanged();
    }
}
