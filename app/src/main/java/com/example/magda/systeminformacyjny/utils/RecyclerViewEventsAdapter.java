package com.example.magda.systeminformacyjny.utils;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.magda.systeminformacyjny.models.Event;

import java.util.ArrayList;

/**
 * Created by piotrek on 19.04.17.
 */

public class RecyclerViewEventsAdapter extends AbstractRecyclerViewEndlessAdapter<Event>{

    IErrorViewModel viewModel;

    public RecyclerViewEventsAdapter(RecyclerView recyclerView, ArrayList<Event> dataSet,
                                     boolean scrollListener, OnLoadMoreListener onLoadMoreListener,
                                     IErrorViewModel viewModel) {
        super(recyclerView, dataSet, scrollListener, onLoadMoreListener);
        this.viewModel = viewModel;

    }

    @Override
    protected IErrorViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateBasicItemViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindBasicItemView(RecyclerView.ViewHolder genericHolder, int position) {

    }
}
