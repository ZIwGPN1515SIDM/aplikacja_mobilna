package com.example.magda.systeminformacyjny.utils;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.magda.systeminformacyjny.activities.CategoriesActivity;
import com.example.magda.systeminformacyjny.activities.MainPlacesActivity;
import com.example.magda.systeminformacyjny.databinding.InformationViewHolderBinding;
import com.example.magda.systeminformacyjny.models.Category;

import java.util.ArrayList;

import static com.example.magda.systeminformacyjny.activities.MainPlacesActivity.CATEGORY_ID;
import static com.example.magda.systeminformacyjny.activities.MainPlacesActivity.TITLE;
import static com.example.magda.systeminformacyjny.utils.Constants.COLOR_LIGHT_PRIMARY_TYPE;
import static com.example.magda.systeminformacyjny.utils.Constants.COLOR_PRIMARY_TYPE;
import static com.example.magda.systeminformacyjny.utils.Constants.CURRENT_ROAD;
import static com.example.magda.systeminformacyjny.utils.Constants.CURRENT_ROAD_ACTIVITY_REQUEST_CODE;
import static com.example.magda.systeminformacyjny.utils.Constants.ERROR_INFO_VIEW_HOLDER;
import static com.example.magda.systeminformacyjny.utils.Constants.FULL_SCREEN_PROGRESS_BAR;

/**
 * Created by piotrek on 10.04.17.
 */

public class RecyclerViewCategoriesAdapter extends AbstractRecyclerViewEndlessAdapter<Category> {

    private IErrorViewModel viewModel;
    private AppCompatActivity viewCallback;

    public RecyclerViewCategoriesAdapter(ArrayList<Category> dataSet,
                                         IErrorViewModel viewModel, AppCompatActivity viewCallback) {
        super(dataSet);
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
        InformationViewHolderBinding binding = InformationViewHolderBinding.inflate(inflater, parent, false);
        return new BasicView(binding);
    }

    @Override
    public void onBindBasicItemView(RecyclerView.ViewHolder genericHolder, int position) {
        BasicView basicView = (BasicView) genericHolder;
        Category category = getDataSet().get(position);
        basicView.bind(category.getName(), position % 2 == 0 ? COLOR_PRIMARY_TYPE : COLOR_LIGHT_PRIMARY_TYPE);
        basicView.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(viewCallback, MainPlacesActivity.class);
            intent.putExtra(TITLE, category.getName());
            intent.putExtra(CATEGORY_ID, category.getId());
            intent.putExtra(CURRENT_ROAD, viewCallback instanceof CategoriesActivity ?
                    ((CategoriesActivity) viewCallback).getCurrentRoad() : null);
            intent.putExtra(Constants.SHOW_SETTINGS_MAIN_PLACE_ITEM, viewCallback instanceof CategoriesActivity ?
                    ((CategoriesActivity) viewCallback).isSettingsMainPlaceButton() : false);
            viewCallback.startActivityForResult(intent, CURRENT_ROAD_ACTIVITY_REQUEST_CODE);
        });
    }

    public class BasicView extends RecyclerView.ViewHolder {
        private final InformationViewHolderBinding binding;

        public BasicView(InformationViewHolderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String category, int colorType) {
            binding.setName(category);
            binding.setColorType(colorType);
            binding.executePendingBindings();
        }
    }

}
