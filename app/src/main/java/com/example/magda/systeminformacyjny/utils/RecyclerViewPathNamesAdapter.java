package com.example.magda.systeminformacyjny.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.magda.systeminformacyjny.databinding.InformationViewHolderBinding;
import com.example.magda.systeminformacyjny.network.items.PathResponse;
import java.util.ArrayList;

import static com.example.magda.systeminformacyjny.utils.Constants.COLOR_LIGHT_PRIMARY_TYPE;
import static com.example.magda.systeminformacyjny.utils.Constants.COLOR_PRIMARY_TYPE;
import static com.example.magda.systeminformacyjny.utils.Constants.ERROR_INFO_VIEW_HOLDER;
import static com.example.magda.systeminformacyjny.utils.Constants.FULL_SCREEN_PROGRESS_BAR;

/**
 * Created by piotrek on 01.06.17.
 */

public class RecyclerViewPathNamesAdapter extends AbstractRecyclerViewEndlessAdapter<PathResponse>{

    private IErrorViewModel viewModel;

    public RecyclerViewPathNamesAdapter(ArrayList<PathResponse> dataSet, IErrorViewModel viewModel) {
        super(dataSet);
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
        InformationViewHolderBinding binding = InformationViewHolderBinding.inflate(inflater, parent, false);
        return new BasicView(binding);
    }

    @Override
    public void onBindBasicItemView(RecyclerView.ViewHolder genericHolder, int position) {
        RecyclerViewPathNamesAdapter.BasicView basicView = (RecyclerViewPathNamesAdapter.BasicView) genericHolder;
        PathResponse pathResponse = getDataSet().get(position);
        basicView.bind(pathResponse.getName(), position % 2 == 0 ? COLOR_PRIMARY_TYPE : COLOR_LIGHT_PRIMARY_TYPE);
        basicView.itemView.setOnClickListener(v -> {
           //TODO
        });
    }

    public class BasicView extends RecyclerView.ViewHolder {
        private final InformationViewHolderBinding binding;

        public BasicView(InformationViewHolderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String name, int colorType) {
            binding.setName(name);
            binding.setColorType(colorType);
            binding.executePendingBindings();
        }
    }
}
