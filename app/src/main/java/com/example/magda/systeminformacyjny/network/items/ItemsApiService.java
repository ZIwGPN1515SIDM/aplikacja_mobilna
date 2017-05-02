package com.example.magda.systeminformacyjny.network.items;

import android.util.Log;

import com.example.magda.systeminformacyjny.models.Category;
import com.example.magda.systeminformacyjny.models.Comment;
import com.example.magda.systeminformacyjny.models.Event;
import com.example.magda.systeminformacyjny.models.MainPlace;
import com.example.magda.systeminformacyjny.models.Place;
import com.example.magda.systeminformacyjny.network.DefaultResourceWrapper;
import com.example.magda.systeminformacyjny.network.WhereToGoService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

/**
 * Created by piotrek on 11.04.17.
 */

public class ItemsApiService {

    private WhereToGoService whereToGoService;

    public ItemsApiService(Retrofit retrofit) {
        this.whereToGoService = retrofit.create(WhereToGoService.class);
    }

    public MaybeSource<List<Category>> downloadCategories(String apiKey) {
        return whereToGoService.downloadCategories(apiKey)
                .subscribeOn(Schedulers.io())
                .map(CategoryResponse::getCategories)
                .observeOn(AndroidSchedulers.mainThread())
                .singleElement();
    }

    public MaybeSource<List<MainPlace>> downloadMainPlacesFromCategory(Long categoryId, String type,
                                                                       String apiKey, final String categoryName) {
        return whereToGoService.downloadMainPlacesFromCategory(categoryId, type, apiKey)
                .subscribeOn(Schedulers.io())
                .map(response -> {
                    List<MainPlace> tmp = response.getMainPlaces();
                    for (MainPlace m : tmp) {
                        m.setCategoryName(categoryName);
                    }
                    return tmp;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .singleElement();
    }

    public MaybeSource<List<Event>> downloadEvents(String type, String namespace, String apiKey) {
        return whereToGoService.downloadEvents(type, namespace, apiKey)
                .subscribeOn(Schedulers.io())
                .map(response -> {
                    List<Event> events = new ArrayList<>();
                    events.add(response.getNamespaceEvent());
                    events.addAll(response.getPlaceEvents());
                    return events;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .singleElement();
    }

    public MaybeSource<List<Place>> downloadPlaces(String type, Long namespaceId, String apiKey) {
        return whereToGoService.downloadPlaces(type, namespaceId, apiKey)
                .subscribeOn(Schedulers.io())
                .map(PlacesResponse::getPlaces)
                .observeOn(AndroidSchedulers.mainThread())
                .singleElement();
    }

    public MaybeSource<List<Comment>> downloadComments(String type, Long id, String apiKey) {
        return whereToGoService.downloadComments(type, id, apiKey)
                .subscribeOn(Schedulers.io())
                .map(CommentResponse::getComments)
                .observeOn(AndroidSchedulers.mainThread())
                .singleElement();
    }

    public MaybeSource<ResponseBody> sendComment(String apiKey, DefaultResourceWrapper request) {
        return whereToGoService.sendComment(apiKey, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .singleElement();
    }
}
