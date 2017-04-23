package com.example.magda.systeminformacyjny.view_models;

import android.support.annotation.NonNull;

import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.activities.PlacesActivity;
import com.example.magda.systeminformacyjny.base.Lifecycle;
import com.example.magda.systeminformacyjny.models.Place;
import com.example.magda.systeminformacyjny.network.DataRequestManager;
import com.example.magda.systeminformacyjny.network.ErrorResponse;
import com.example.magda.systeminformacyjny.network.SuccessResponse;
import com.example.magda.systeminformacyjny.utils.AbstractRecyclerViewEndlessAdapter;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.example.magda.systeminformacyjny.network.ErrorResponse.DOWNLOAD_ERROR;
import static com.example.magda.systeminformacyjny.network.SuccessResponse.DOWNLOAD_SUCCESS;
import static com.example.magda.systeminformacyjny.utils.Constants.ERROR_INFO_VIEW_HOLDER;
import static com.example.magda.systeminformacyjny.utils.Constants.FULL_SCREEN_PROGRESS_BAR;

/**
 * Created by piotrek on 22.04.17.
 */

public class ActivityPlacesViewModel implements Lifecycle.ViewModel, AbstractRecyclerViewEndlessAdapter.IErrorViewModel{

    private ArrayList<Place> places;
    private PlacesActivity viewCallback;
    private DataRequestManager dataRequestManager;
    private CompositeDisposable compositeDisposable;
    private ErrorResponse errorResponse;
    private SuccessResponse successResponse;
    private Long namespaceId;

    private static final String TYPE = "place";

    public ActivityPlacesViewModel(PlacesActivity viewCallback) {
        this.viewCallback = viewCallback;
        this.dataRequestManager = DataRequestManager.getInstance();
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onViewResumed() {
        if(successResponse != null) {
            onSuccessResponse();
        }else if(errorResponse != null) {
            onErrorResponse();
        }
    }

    @Override
    public void onViewAttached(@NonNull Lifecycle.View viewCallback) {
        this.viewCallback = (PlacesActivity) viewCallback;
    }

    @Override
    public void onViewDetached() {
        this.viewCallback = null;
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
    }

    @Override
    public void onErrorResponse() {
        if(viewCallback != null) {
            viewCallback.onError(errorResponse);
            errorResponse = null;
        }
    }

    @Override
    public void onSuccessResponse() {
        if(viewCallback != null) {
            viewCallback.onSuccess(successResponse);
            successResponse = null;
        }
    }

    public void downloadPlaces() {
        String apiKey = viewCallback.getString(R.string.server_api_key);
        places.clear();
        places.add(new Place(FULL_SCREEN_PROGRESS_BAR));
        viewCallback.notifyRecyclerView();

        dataRequestManager.downloadPlaces(TYPE, namespaceId, apiKey)
                .subscribe(new MaybeObserver<List<Place>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<Place> value) {
                        places.clear();
                        places.addAll(value);
                        if(places.size() == 0)
                            places.add(null);
                        successResponse = new SuccessResponse(DOWNLOAD_SUCCESS);
                        onSuccessResponse();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        places.clear();
                        places.add(new Place(ERROR_INFO_VIEW_HOLDER));
                        errorResponse = new ErrorResponse(DOWNLOAD_ERROR);
                        onErrorResponse();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }

    @Override
    public void refreshAfterDownloadError() {
        downloadPlaces();
    }

    public void setNamespaceId(Long namespaceId) {
        this.namespaceId = namespaceId;
    }
}
