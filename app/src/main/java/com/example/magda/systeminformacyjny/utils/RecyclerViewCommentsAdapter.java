package com.example.magda.systeminformacyjny.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.magda.systeminformacyjny.databinding.LocationOpinionViewHolderBinding;
import com.example.magda.systeminformacyjny.databinding.LocationViewHolderBinding;
import com.example.magda.systeminformacyjny.models.Comment;
import java.util.ArrayList;

import static com.example.magda.systeminformacyjny.utils.Constants.ERROR_INFO_VIEW_HOLDER;
import static com.example.magda.systeminformacyjny.utils.Constants.FULL_SCREEN_PROGRESS_BAR;

/**
 * Created by piotrek on 02.05.17.
 */

public class RecyclerViewCommentsAdapter extends AbstractRecyclerViewEndlessAdapter<Comment>{

    private IErrorViewModel viewModel;

    public RecyclerViewCommentsAdapter(RecyclerView recyclerView, ArrayList<Comment> dataSet,
                                       boolean scrollListener, OnLoadMoreListener onLoadMoreListener,
                                        IErrorViewModel viewModel) {
        super(recyclerView, dataSet, scrollListener, onLoadMoreListener);
        this.viewModel = viewModel;
    }

    @Override
    protected IErrorViewModel getViewModel() {
        return this.viewModel;
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
        LocationOpinionViewHolderBinding binding = LocationOpinionViewHolderBinding.inflate(inflater,
                parent, false);
        return new BasicView(binding);
    }

    @Override
    public void onBindBasicItemView(RecyclerView.ViewHolder genericHolder, int position) {
        Comment comment = getDataSet().get(position);
        BasicView basicView = (BasicView) genericHolder;
        basicView.bind(comment);
    }

    //TODO dorobic widok i bindowanie
    public class BasicView extends RecyclerView.ViewHolder {
        private LocationOpinionViewHolderBinding binding;
        public BasicView(LocationOpinionViewHolderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Comment comment) {
            binding.setCommnet(comment);
            binding.executePendingBindings();
        }
    }
}
