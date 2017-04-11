package com.example.magda.systeminformacyjny.network;

import com.example.magda.systeminformacyjny.network.items.CategoryResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by piotrek on 06.04.17.
 */

public interface WhereToGoService {

    //TODO interfejsy :)

    @GET("category")
    Observable<CategoryResponse> downloadCategories(@Query("api_key") String apiKey);

}
