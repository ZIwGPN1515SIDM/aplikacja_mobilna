package com.example.magda.systeminformacyjny.utils;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.magda.systeminformacyjny.activities.LocationActivity;
import com.example.magda.systeminformacyjny.activities.MainPlacesActivity;
import com.example.magda.systeminformacyjny.activities.SubLocationActivity;
import com.example.magda.systeminformacyjny.databinding.LocationViewHolderBinding;
import com.example.magda.systeminformacyjny.models.IPlaceItem;
import com.example.magda.systeminformacyjny.models.MainPlace;
import com.example.magda.systeminformacyjny.models.Place;

import java.util.ArrayList;

import static com.example.magda.systeminformacyjny.activities.LocationActivity.MAIN_PLACE_TAG;
import static com.example.magda.systeminformacyjny.activities.MainPlacesActivity.LOCATION_ACTIVITY_CODE;
import static com.example.magda.systeminformacyjny.activities.SubLocationActivity.PLACE_TAG;
import static com.example.magda.systeminformacyjny.utils.Constants.ERROR_INFO_VIEW_HOLDER;
import static com.example.magda.systeminformacyjny.utils.Constants.FULL_SCREEN_PROGRESS_BAR;

/**
 * Created by piotrek on 10.04.17.
 */

public class RecyclerViewPlacesAdapter<T extends IPlaceItem> extends AbstractRecyclerViewEndlessAdapter<T>{

    private IErrorViewModel viewModel;
    private AppCompatActivity viewCallback;

    public RecyclerViewPlacesAdapter(RecyclerView recyclerView, ArrayList<T> dataSet,
                                     boolean scrollListener, OnLoadMoreListener onLoadMoreListener,
                                     IErrorViewModel viewModel, AppCompatActivity viewCallback) {
        super(recyclerView, dataSet, scrollListener, onLoadMoreListener);
        this.viewModel = viewModel;
        this.viewCallback = viewCallback;
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
        IPlaceItem place = getDataSet().get(position);
        BasicViewHolder basicViewHolder = (BasicViewHolder) genericHolder;
        basicViewHolder.bind(place, place instanceof MainPlace);
        basicViewHolder.itemView.setOnClickListener(v -> {
            Intent intent;
            if(place instanceof MainPlace) { //mainplace
                intent = new Intent(viewCallback, LocationActivity.class);
                intent.putExtra(MAIN_PLACE_TAG, (MainPlace)place);
                viewCallback.startActivityForResult(intent, LOCATION_ACTIVITY_CODE);
            }else { //place
                intent = new Intent(viewCallback, SubLocationActivity.class);
                intent.putExtra(PLACE_TAG, (Place)place);
                viewCallback.startActivity(intent);
                //TODO dorobic activity dla tego widoku i done :) albo moze sie ujednolici :)
            }
        });
    }

    public class BasicViewHolder extends RecyclerView.ViewHolder {
        private final LocationViewHolderBinding binding;
        public BasicViewHolder(LocationViewHolderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(IPlaceItem mainPlace, boolean settingsVisible) {
            binding.setPlace(mainPlace);
            binding.setSettingsVisible(settingsVisible);
            binding.executePendingBindings();
        }
    }
}
