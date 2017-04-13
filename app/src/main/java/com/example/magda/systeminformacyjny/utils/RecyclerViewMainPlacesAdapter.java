package com.example.magda.systeminformacyjny.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.magda.systeminformacyjny.databinding.LocationViewHolderBinding;
import com.example.magda.systeminformacyjny.models.MainPlace;

import java.util.ArrayList;

import static com.example.magda.systeminformacyjny.utils.Constants.ERROR_INFO_VIEW_HOLDER;
import static com.example.magda.systeminformacyjny.utils.Constants.FULL_SCREEN_PROGRESS_BAR;

/**
 * Created by piotrek on 10.04.17.
 */

public class RecyclerViewMainPlacesAdapter extends AbstractRecyclerViewEndlessAdapter<MainPlace>{

    private IErrorViewModel viewModel;

    public RecyclerViewMainPlacesAdapter(RecyclerView recyclerView, ArrayList<MainPlace> dataSet,
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
        } else if (getDataSet().get(position) == null) { // jeśli ma być progressBar w ViewHolder
            return PROGRESS_BAR_VIEW_HOLDER;
        } else { // jeśli wyglad podstawowy
            return NORMAL_VIEW_HOLDER;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateBasicItemViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        LocationViewHolderBinding binding = LocationViewHolderBinding.inflate(inflater, parent, false);
        return new BasicViewHolder(binding);
    }

    @Override
    public void onBindBasicItemView(RecyclerView.ViewHolder genericHolder, int position) {
        MainPlace mainPlace = getDataSet().get(position);
        BasicViewHolder basicViewHolder = (BasicViewHolder) genericHolder;
        basicViewHolder.bind(mainPlace);
    }

    public class BasicViewHolder extends RecyclerView.ViewHolder {
        private final LocationViewHolderBinding binding;
        public BasicViewHolder(LocationViewHolderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(MainPlace mainPlace) {
            binding.setMainPlace(mainPlace);
        }
    }
}
