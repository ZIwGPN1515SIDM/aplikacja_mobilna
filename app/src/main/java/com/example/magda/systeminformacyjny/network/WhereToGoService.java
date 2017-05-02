package com.example.magda.systeminformacyjny.network;

import com.example.magda.systeminformacyjny.network.items.CommentResponse;
import com.example.magda.systeminformacyjny.network.items.EventResponse;
import com.example.magda.systeminformacyjny.network.items.MainPlacesFromCategoryResponse;
import com.example.magda.systeminformacyjny.network.items.CategoryResponse;
import com.example.magda.systeminformacyjny.network.items.PlacesResponse;
import com.example.magda.systeminformacyjny.network.user.LoginRequest;
import com.example.magda.systeminformacyjny.network.user.LoginResponse;
import com.example.magda.systeminformacyjny.network.user.NewsletterRequest;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by piotrek on 06.04.17.
 */

public interface WhereToGoService {

    //TODO interfejsy :)

    @GET("category")
    Observable<CategoryResponse> downloadCategories(@Query("api_key") String apiKey);

    @GET("place/category")
    Observable<MainPlacesFromCategoryResponse> downloadMainPlacesFromCategory(@Query("category") Long categoryId,
                                                                              @Query("type") String type,
                                                                              @Query("api_key") String apiKey);

    @POST("sidmapp/user")
    Observable<LoginResponse> login(@Query("api_key") String apiKey,
                                   @Body DefaultResourceWrapper loginRequest);

    @PATCH("sidmapp/user/modify")
    Observable<ResponseBody> sendNewsletter(@Query("api_key") String apiKey,
                                            @Body DefaultResourceWrapper request);

    @GET("place/event")
    Observable<EventResponse> downloadEvents(@Query("type") String type,
                                             @Query("namespace") String namespace,
                                             @Query("api_key") String apiKey);

    @GET("place/namespace")
    Observable<PlacesResponse> downloadPlaces(@Query("type") String type,
                                              @Query("namespaceid") Long namespaceId,
                                              @Query("api_key") String apiKey);

    @GET("comments")
    Observable<CommentResponse> downloadComments(@Query("type") String type,
                                                 @Query("id") Long id,
                                                 @Query("api_key") String apiKey);
}
