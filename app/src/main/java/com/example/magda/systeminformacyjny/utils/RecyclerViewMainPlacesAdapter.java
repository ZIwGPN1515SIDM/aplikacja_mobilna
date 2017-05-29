package com.example.magda.systeminformacyjny.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.activities.LocationActivity;
import com.example.magda.systeminformacyjny.base.IMainPLaceViewModel;
import com.example.magda.systeminformacyjny.databinding.LocationViewHolderBinding;
import com.example.magda.systeminformacyjny.models.IPlaceItem;
import com.example.magda.systeminformacyjny.models.MainPlace;

import java.util.ArrayList;

import static com.example.magda.systeminformacyjny.activities.LocationActivity.MAIN_PLACE_TAG;
import static com.example.magda.systeminformacyjny.utils.Constants.ERROR_INFO_VIEW_HOLDER;
import static com.example.magda.systeminformacyjny.utils.Constants.FULL_SCREEN_PROGRESS_BAR;

/**
 * Created by piotrek on 10.04.17.
 */

public class RecyclerViewMainPlacesAdapter<T extends IPlaceItem> extends AbstractRecyclerViewEndlessAdapter<T> {

    private IMainPLaceViewModel viewModel;
    private boolean settingsFlag;
    private Context context;
    private IErrorViewModel errorViewModel;

    private static final String ADD_THIS_PLACE_TO_ROUTE_ERROR_INFO = "Dodałeś już do trasy to miejsce !";
    private static final String ADD_THIS_PLACE_TO_ROUTE_INFO = "Dodałeś miejsce do trasy !";
    private static final String REMOVE_THIS_PLACE_TO_ROUTE_INFO = "Usunałeś miejsce z trasy !";

    public RecyclerViewMainPlacesAdapter(ArrayList<T> dataSet, IMainPLaceViewModel viewModel, IErrorViewModel errorViewModel,
                                         Context context, boolean settingsFlag) {
        super(dataSet);
        this.viewModel = viewModel;
        this.settingsFlag = settingsFlag;
        this.context = context;
        this.errorViewModel = errorViewModel;
    }

    @Override
    protected IErrorViewModel getViewModel() {
        return errorViewModel;
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
            intent = new Intent(context, LocationActivity.class);
            intent.putExtra(MAIN_PLACE_TAG, place);
            context.startActivity(intent);
        });

        if (settingsFlag) {
            basicViewHolder.setUpPopUpMenu(viewModel.containsMainPlace((MainPlace) getDataSet().get(position)));
            setUpPopMenuListener(basicViewHolder.popupMenu, position, basicViewHolder);
        }
    }


    private void setUpPopMenuListener(PopupMenu popupMenu, int position, BasicViewHolder basicViewHolder) {
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.location_menu_add:
                    viewModel.addMainPlace((MainPlace) getDataSet().get(position));
                    Toast.makeText(context, ADD_THIS_PLACE_TO_ROUTE_INFO, Toast.LENGTH_SHORT)
                            .show();
                    basicViewHolder.setUpPopUpMenu(viewModel.containsMainPlace((MainPlace) getDataSet().get(position)));
                    setUpPopMenuListener(basicViewHolder.popupMenu, position, basicViewHolder);
                    break;
                case R.id.location_menu_remove:
                    viewModel.removeMainPlace((MainPlace) getDataSet().get(position));
                    Toast.makeText(context, REMOVE_THIS_PLACE_TO_ROUTE_INFO, Toast.LENGTH_SHORT)
                            .show();
                    basicViewHolder.setUpPopUpMenu(viewModel.containsMainPlace((MainPlace) getDataSet().get(position)));
                    setUpPopMenuListener(basicViewHolder.popupMenu, position, basicViewHolder);
                    break;
            }
            return false;
        });
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

        public void setUpPopUpMenu(boolean flag) {
            popupMenu = new PopupMenu(context, binding.settingsButton);
            popupMenu.setGravity(Gravity.END);
            popupMenu.inflate(flag ? R.menu.remove_location_menu : R.menu.add_location_menu);
            binding.settingsButton.setOnClickListener((v) -> popupMenu.show());
        }
    }
}
