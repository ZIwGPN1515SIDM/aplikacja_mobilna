package com.example.magda.systeminformacyjny.utils;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.activities.LocationActivity;
import com.example.magda.systeminformacyjny.activities.MainPlacesActivity;
import com.example.magda.systeminformacyjny.activities.SubLocationActivity;
import com.example.magda.systeminformacyjny.databinding.LocationViewHolderBinding;
import com.example.magda.systeminformacyjny.models.IPlaceItem;
import com.example.magda.systeminformacyjny.models.MainPlace;
import com.example.magda.systeminformacyjny.view_models.ActivityMainPlacesListViewModel;

import java.util.ArrayList;

import static com.example.magda.systeminformacyjny.activities.LocationActivity.MAIN_PLACE_TAG;
import static com.example.magda.systeminformacyjny.utils.Constants.ERROR_INFO_VIEW_HOLDER;
import static com.example.magda.systeminformacyjny.utils.Constants.FULL_SCREEN_PROGRESS_BAR;

/**
 * Created by piotrek on 10.04.17.
 */

public class RecyclerViewMainPlacesAdapter<T extends IPlaceItem> extends AbstractRecyclerViewEndlessAdapter<T> {

    private ActivityMainPlacesListViewModel viewModel;
    private AppCompatActivity viewCallback;
    private boolean settingsFlag;

    private static final String ADD_THIS_PLACE_TO_ROUTE_ERROR_INFO = "Dodałeś już do trasy to miejsce!";
    private static final String ADD_THIS_PLACE_TO_ROUTE_INFO = "Dodałeś miejsce do trasy!";

    public RecyclerViewMainPlacesAdapter(RecyclerView recyclerView, ArrayList<T> dataSet,
                                         boolean scrollListener, OnLoadMoreListener onLoadMoreListener,
                                         ActivityMainPlacesListViewModel viewModel, AppCompatActivity viewCallback,
                                         boolean settingsFlag) {
        super(recyclerView, dataSet, scrollListener, onLoadMoreListener);
        this.viewModel = viewModel;
        this.viewCallback = viewCallback;
        this.settingsFlag =settingsFlag;
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
        basicViewHolder.bind(place, settingsFlag);
        basicViewHolder.itemView.setOnClickListener(v -> {
            Intent intent;
            intent = new Intent(viewCallback, LocationActivity.class);
            intent.putExtra(MAIN_PLACE_TAG, place);
            viewCallback.startActivity(intent);
        });
        if(settingsFlag) {
            basicViewHolder.setUpPopUpMenu();
            basicViewHolder.popupMenu.setOnMenuItemClickListener(item -> {
                //dodanie do trays miejsca
                if (!viewModel.getCurrentRoad().contains(getDataSet().get(position))) {
                    viewModel.getCurrentRoad().add((MainPlace) getDataSet().get(position));
                    Toast.makeText(viewCallback, ADD_THIS_PLACE_TO_ROUTE_INFO, Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Toast.makeText(viewCallback, ADD_THIS_PLACE_TO_ROUTE_ERROR_INFO, Toast.LENGTH_SHORT)
                            .show();
                }

                return false;
            });
        }
    }

    public class BasicViewHolder extends RecyclerView.ViewHolder {
        private final LocationViewHolderBinding binding;
        private PopupMenu popupMenu;

        public BasicViewHolder(LocationViewHolderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(IPlaceItem mainPlace, boolean settingsVisible) {
            binding.setPlace(mainPlace);
            binding.setSettingsVisible(settingsVisible);
            binding.executePendingBindings();
        }

        public void setUpPopUpMenu() {
            popupMenu = new PopupMenu(viewCallback, binding.settingsButton);
            popupMenu.setGravity(Gravity.END);
            popupMenu.inflate(R.menu.add_location_menu);
            binding.settingsButton.setOnClickListener((v) -> popupMenu.show());
        }
    }
}
