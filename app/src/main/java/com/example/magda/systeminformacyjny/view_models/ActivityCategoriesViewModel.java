package com.example.magda.systeminformacyjny.view_models;

import com.example.magda.systeminformacyjny.activities.CategoryActivity;
import com.example.magda.systeminformacyjny.models.Category;
import com.example.magda.systeminformacyjny.utils.AbstractRecyclerViewEndlessAdapter;

import java.util.ArrayList;

/**
 * Created by piotrek on 10.04.17.
 */

public class ActivityCategoriesViewModel implements AbstractRecyclerViewEndlessAdapter.OnLoadMoreListener,
        AbstractRecyclerViewEndlessAdapter.IErrorViewModel{

    private ArrayList<Category> categories;
    private CategoryActivity viewCallback;

    public ActivityCategoriesViewModel(CategoryActivity viewCallback) {
        this.viewCallback = viewCallback;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }


    public void openCategory(Category category) {
        //TODO otworzyc nowe activity z lista miejsc nalezacych do tej kategorii
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void refreshAfterDownloadError() {

    }
}
