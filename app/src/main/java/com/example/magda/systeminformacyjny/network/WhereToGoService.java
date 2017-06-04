package com.example.magda.systeminformacyjny.network;

import com.example.magda.systeminformacyjny.network.items.CommentResponse;
import com.example.magda.systeminformacyjny.network.items.EventResponse;
import com.example.magda.systeminformacyjny.network.items.InstanceResponse;
import com.example.magda.systeminformacyjny.network.items.MainPlacesFromCategoryResponse;
import com.example.magda.systeminformacyjny.network.items.CategoryResponse;
import com.example.magda.systeminformacyjny.network.items.PathResponse;
import com.example.magda.systeminformacyjny.network.items.CurrentPath;
import com.example.magda.systeminformacyjny.network.items.PlacesResponse;
import com.example.magda.systeminformacyjny.network.items.SendEnteredEvent;
import com.example.magda.systeminformacyjny.network.items.SendLeaveEvent;
import com.example.magda.systeminformacyjny.network.user.LoginResponse;

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

    @GET("place")
    Observable<InstanceResponse> downloadPlace(@Query("api_key") String apiKey,
                                               @Query("type") String type,
                                               @Query("namespace") String namespace,
                                               @Query("place") String instance);

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

    @POST("comments/add")
    Observable<DefaultIdWrapper> sendComment(@Query("api_key") String apiKey,
                                             @Body DefaultResourceWrapper commentRequest);


    @POST("sidm/_table/USER_PATHS")
    Observable<ResponseBody> sendPath(@Query("api_key") String apiKey,
                                      @Body DefaultResourceWrapper<CurrentPath> pathSendRequest);

    @GET("sidm/_table/USER_PATHS")
    Observable<DefaultResourceWrapper<PathResponse>> downloadRoutes(@Query(value = "fields", encoded = true) String fields,
                                                                    @Query(value = "filter", encoded = true) String filter,
                                                                    @Query("api_key") String apiKey);

    @GET("sidm/_table/USER_PATHS")
    Observable<DefaultResourceWrapper<CurrentPath>> downloadCurrentPath(@Query(value = "filter", encoded = true) String filter,
                                                                        @Query("api_key") String apiKey);


    @POST("sidm/_table/NAMESPACES_EVENTS")
    Observable<DefaultResourceWrapper<DefaultIdWrapper>> sendEnteredNamespace(@Query("api_key") String apiKey,
                                                  @Body DefaultResourceWrapper<SendEnteredEvent> sendEnteredEvent);

    @POST("sidm/_table/PLACES_EVENTS")
    Observable<DefaultResourceWrapper<DefaultIdWrapper>> sendEnteredInstance(@Query("api_key") String apiKey,
                                                 @Body DefaultResourceWrapper<SendEnteredEvent> sendEnteredEvent);

    @PATCH("sidm/_table/NAMESPACES_EVENTS")
    Observable<ResponseBody> sendLeaveNamespace(@Query("api_key") String apiKey,
                                                @Body DefaultResourceWrapper<SendLeaveEvent> sendLeaveEvent);

    @PATCH("sidm/_table/PLACES_EVENTS")
    Observable<ResponseBody> sendLeaveInstance(@Query("api_key") String apiKey,
                                               @Body DefaultResourceWrapper<SendLeaveEvent> sendLeaveEvent);

}
