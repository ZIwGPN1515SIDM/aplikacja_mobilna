package com.example.magda.systeminformacyjny.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.magda.systeminformacyjny.databinding.EventViewHolderBinding;
import com.example.magda.systeminformacyjny.models.Event;
import java.util.ArrayList;

import static com.example.magda.systeminformacyjny.utils.Constants.ERROR_INFO_VIEW_HOLDER;
import static com.example.magda.systeminformacyjny.utils.Constants.FULL_SCREEN_PROGRESS_BAR;

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
        if (getDataSet().size() == 1 && getDataSet().get(position) == null) {
            return FULL_SCREEN_EMPTY_VIEW_HOLDER;
        } else if (getDataSet().size() == 1 && getDataSet().get(position).getId() == FULL_SCREEN_PROGRESS_BAR) {
            return FULL_SCREEN_PROGRESS_BAR_VIEW_HOLDER;
        } else if (getDataSet().size() == 1 && getDataSet().get(position).getId() == ERROR_INFO_VIEW_HOLDER) {
            return ERROR_INFO;
        } else { // jeśli wyglad podstawowy
            return NORMAL_VIEW_HOLDER;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateBasicItemViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        EventViewHolderBinding binding = EventViewHolderBinding.inflate(inflater, parent,
                false);
        return new BasicView(binding);
    }

    @Override
    public void onBindBasicItemView(RecyclerView.ViewHolder genericHolder, int position) {
        BasicView basicView = (BasicView) genericHolder;
        basicView.bind(getDataSet().get(position));
    }

    public class BasicView extends RecyclerView.ViewHolder {

        private EventViewHolderBinding binding;

        public BasicView(EventViewHolderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Event event) {
            binding.setEvent(event);
            binding.executePendingBindings();
        }
    }
}
