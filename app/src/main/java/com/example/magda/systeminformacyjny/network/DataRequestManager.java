package com.example.magda.systeminformacyjny.network;

import com.example.magda.systeminformacyjny.models.Category;
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
    private WhereToGoServer whwreToGoServer;
    private ItemsApiService itemsApiService;

    public static synchronized DataRequestManager getInstance() {
        if (instance == null) {
            instance = new DataRequestManager();
        }
        return instance;
    }

    private DataRequestManager() {
        whwreToGoServer = WhereToGoServer.getInstance();
        retrofit = whwreToGoServer.getRetrofit();
        itemsApiService = new ItemsApiService(retrofit);
    }

    public MaybeSource<List<Category>> downloadCategories(String apiKey) {
        return itemsApiService.downloadCategories(apiKey);
    }


}
