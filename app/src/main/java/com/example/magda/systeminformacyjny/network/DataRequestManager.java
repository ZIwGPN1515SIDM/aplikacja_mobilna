package com.example.magda.systeminformacyjny.network;

import com.example.magda.systeminformacyjny.models.Category;
import com.example.magda.systeminformacyjny.models.MainPlace;
import com.example.magda.systeminformacyjny.network.items.ItemsApiService;

import java.util.List;

import io.reactivex.MaybeSource;
import retrofit2.Retrofit;

/**
 * Created by piotrek on 11.04.17.
 */

public class DataRequestManager {

    private static DataRequestManager instance;
    private Retrofit retrofit;
    private WhereToGoServer whereToGoServer;
    private ItemsApiService itemsApiService;

    public static synchronized DataRequestManager getInstance() {
        if (instance == null) {
            instance = new DataRequestManager();
        }
        return instance;
    }

    private DataRequestManager() {
        whereToGoServer = WhereToGoServer.getInstance();
        retrofit = whereToGoServer.getRetrofit();
        itemsApiService = new ItemsApiService(retrofit);
    }

    public MaybeSource<List<Category>> downloadCategories(String apiKey) {
        return itemsApiService.downloadCategories(apiKey);
    }

    public MaybeSource<List<MainPlace>> downloadMainPLacesFromCategory(Long categoryId, String type,
                                                                       String apiKey) {
        return itemsApiService.downloadMainPlacesFromCategory(categoryId, type, apiKey);
    }

}
