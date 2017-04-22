package com.example.magda.systeminformacyjny.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.magda.systeminformacyjny.activities.MainPlacesActivity;
import com.example.magda.systeminformacyjny.databinding.CategoryViewHolderBinding;
import com.example.magda.systeminformacyjny.models.Category;
import java.util.ArrayList;

import static com.example.magda.systeminformacyjny.activities.MainPlacesActivity.CATEGORY_ID;
import static com.example.magda.systeminformacyjny.activities.MainPlacesActivity.TITLE;
import static com.example.magda.systeminformacyjny.models.Category.COLOR_LIGHT_PRIMARY_TYPE;
import static com.example.magda.systeminformacyjny.models.Category.COLOR_PRIMARY_TYPE;
import static com.example.magda.systeminformacyjny.utils.Constants.ERROR_INFO_VIEW_HOLDER;
import static com.example.magda.systeminformacyjny.utils.Constants.FULL_SCREEN_PROGRESS_BAR;

/**
 * Created by piotrek on 10.04.17.
 */

public class RecyclerViewCategoriesAdapter extends AbstractRecyclerViewEndlessAdapter<Category> {

    private IErrorViewModel viewModel;
    private Context context;

    public RecyclerViewCategoriesAdapter(RecyclerView recyclerView, ArrayList<Category> dataSet,
                                         boolean scrollListener, OnLoadMoreListener onLoadMoreListener,
                                         IErrorViewModel viewModel, Context context) {
        super(recyclerView, dataSet, scrollListener, onLoadMoreListener);
        this.viewModel = viewModel;
        this.context = context;
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
        CategoryViewHolderBinding binding = CategoryViewHolderBinding.inflate(inflater, parent, false);
        return new BasicView(binding);
    }

    @Override
    public void onBindBasicItemView(RecyclerView.ViewHolder genericHolder, int position) {
        BasicView basicView = (BasicView) genericHolder;
        Category category = getDataSet().get(position);
        basicView.bind(category, position % 2 == 0 ? COLOR_PRIMARY_TYPE : COLOR_LIGHT_PRIMARY_TYPE);
        basicView.itemView.setOnClickListener(v->{
            Intent intent = new Intent(context, MainPlacesActivity.class);
            intent.putExtra(TITLE, category.getName());
            intent.putExtra(CATEGORY_ID, category.getId());
            context.startActivity(intent); //TODO potem zmiana na startActivityForResult
        });
    }

    public class BasicView extends RecyclerView.ViewHolder {
        private final CategoryViewHolderBinding binding;

        public BasicView(CategoryViewHolderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Category category, int colorType) {
            binding.setCategory(category);
            binding.setColorType(colorType);
        }
    }

}
