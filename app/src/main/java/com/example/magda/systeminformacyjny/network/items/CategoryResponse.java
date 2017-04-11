package com.example.magda.systeminformacyjny.network.items;

import com.example.magda.systeminformacyjny.models.Category;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by piotrek on 11.04.17.
 */

public class CategoryResponse {

    @SerializedName("categories")
    @Expose
    private List<Category> categories;


    public CategoryResponse(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
