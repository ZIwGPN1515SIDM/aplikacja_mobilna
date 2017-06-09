package com.example.magda.systeminformacyjny.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.magda.systeminformacyjny.activities.SubLocationActivity;
import com.example.magda.systeminformacyjny.databinding.DownloadingPlaceInfoBinding;
import com.example.magda.systeminformacyjny.databinding.LocationViewHolderBinding;
import com.example.magda.systeminformacyjny.models.IPlaceItem;
import com.example.magda.systeminformacyjny.models.NearPlace;

import java.util.ArrayList;

import static com.example.magda.systeminformacyjny.activities.SubLocationActivity.PLACE_TAG;
import static com.example.magda.systeminformacyjny.utils.Constants.ERROR_INFO_VIEW_HOLDER;
import static com.example.magda.systeminformacyjny.utils.Constants.FULL_SCREEN_PROGRESS_BAR;

/**
 * Created by piotrek on 04.06.17.
 */

public class RecyclerViewNearPlacesAdapter extends AbstractRecyclerViewEndlessAdapter<NearPlace>{

    public RecyclerViewNearPlacesAdapter(ArrayList<NearPlace> dataSet) {
        super(dataSet);
    }

    @Override
    protected IErrorViewModel getViewModel() {
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (getDataSet().get(position) == null) {
            return FULL_SCREEN_EMPTY_VIEW_HOLDER;
        } else if (getDataSet().size() == 1 && getDataSet().get(position).getId() == FULL_SCREEN_PROGRESS_BAR) {
            return FULL_SCREEN_PROGRESS_BAR_VIEW_HOLDER;
        } else if (getDataSet().size() == 1 && getDataSet().get(position).getId() == ERROR_INFO_VIEW_HOLDER) {
            return ERROR_INFO;
        } else if (getDataSet().get(position).getPlace() == null) { // jeśli ma być progressBar w ViewHolder
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
        IPlaceItem place = getDataSet().get(position).getPlace();
        BasicViewHolder basicViewHolder = (BasicViewHolder) genericHolder;
//        Log.d("JESTEM" , "pos + " + position + ", des " + place.getDescription());
        basicViewHolder.itemView.setOnClickListener(v-> {
            Intent intent = new Intent(genericHolder.itemView.getContext(), SubLocationActivity.class);
            intent.putExtra(PLACE_TAG, place);
            genericHolder.itemView.getContext().startActivity(intent);
        });
        basicViewHolder.bind(place);
    }

    public void onBindProgressBarViewHolder(RecyclerView.ViewHolder genericHolder, int position) {
    }

    public RecyclerView.ViewHolder onCreateProgressBarViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        DownloadingPlaceInfoBinding binding = DownloadingPlaceInfoBinding.inflate(inflater,parent, false);
        return new DownloadingInfo(binding);
    }


    public class BasicViewHolder extends RecyclerView.ViewHolder {
        private final LocationViewHolderBinding binding;

        public BasicViewHolder(LocationViewHolderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(IPlaceItem mainPlace) {
            binding.setPlace(mainPlace);
            binding.setSettingsVisible(false);
            binding.executePendingBindings();
        }

    }

    public class DownloadingInfo extends RecyclerView.ViewHolder {
        private final DownloadingPlaceInfoBinding binding;

        public DownloadingInfo(DownloadingPlaceInfoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind() {
            binding.executePendingBindings();
        }
    }
}
