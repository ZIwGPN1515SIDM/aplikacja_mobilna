package com.example.magda.systeminformacyjny.network;

import com.example.magda.systeminformacyjny.models.Category;
import com.example.magda.systeminformacyjny.models.Comment;
import com.example.magda.systeminformacyjny.models.Event;
import com.example.magda.systeminformacyjny.models.MainPlace;
import com.example.magda.systeminformacyjny.models.Place;
import com.example.magda.systeminformacyjny.network.items.CurrentPath;
import com.example.magda.systeminformacyjny.network.items.ItemsApiService;
import com.example.magda.systeminformacyjny.network.items.PathName;
import com.example.magda.systeminformacyjny.network.items.PathResponse;
import com.example.magda.systeminformacyjny.network.user.LoginRequest;
import com.example.magda.systeminformacyjny.network.user.LoginResponse;
import com.example.magda.systeminformacyjny.network.user.NewsletterRequest;
import com.example.magda.systeminformacyjny.network.user.UserApiService;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

/**
 * Created by piotrek on 11.04.17.
 */

public class DataRequestManager {

    private static DataRequestManager instance;
    private Retrofit retrofit;
    private WhereToGoServer whereToGoServer;
    private ItemsApiService itemsApiService;
    private UserApiService userApiService;

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
        userApiService = new UserApiService(retrofit);
    }

    public MaybeSource<List<Category>> downloadCategories(String apiKey) {
        return itemsApiService.downloadCategories(apiKey);
    }

    public MaybeSource<List<MainPlace>> downloadMainPLacesFromCategory(Long categoryId, String type,
                                                                       String apiKey, final String categoryName) {
        return itemsApiService.downloadMainPlacesFromCategory(categoryId, type, apiKey, categoryName);
    }

    public MaybeSource<List<Event>> downloadEvents(String type, String namespace, String apiKey) {
        return itemsApiService.downloadEvents(type, namespace, apiKey);
    }

    public MaybeSource<LoginResponse.User> login(String apiKey, LoginRequest loginRequest) {
        return userApiService.login(apiKey, loginRequest);
    }

    public MaybeSource<ResponseBody> sendNewsletter(String apiKey, NewsletterRequest request) {
        return userApiService.sendNewsletter(apiKey, request);
    }

    public MaybeSource<List<Place>> downloadPlaces(String type, Long namespaceId, String apiKey) {
        return itemsApiService.downloadPlaces(type, namespaceId, apiKey);
    }

    public MaybeSource<List<Comment>> downloadComments(String type, Long id, String apiKey) {
        return itemsApiService.downloadComments(type, id, apiKey);
    }

    public MaybeSource<DefaultIdWrapper> sendComment(String apiKey, DefaultResourceWrapper request) {
        return itemsApiService.sendComment(apiKey, request);
    }

    public MaybeSource<ResponseBody> sendPath(String apiKey, DefaultResourceWrapper<CurrentPath> currentPath) {
        return itemsApiService.sendPath(apiKey, currentPath);
    }

    public MaybeSource<List<PathResponse>> downloadPaths(String fields, String filter, String apiKey) {
        return itemsApiService.downloadPaths(fields, filter, apiKey);
    }

    public MaybeSource<List<PathName>> downloadCurrentPath(String filter, String apiKey) {
        return itemsApiService.downloadCurrentPath(filter, apiKey);
    }
}
