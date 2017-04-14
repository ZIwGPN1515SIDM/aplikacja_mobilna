package com.example.magda.systeminformacyjny.view_models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.activities.MainPlacesListActivity;
import com.example.magda.systeminformacyjny.base.Lifecycle;
import com.example.magda.systeminformacyjny.models.MainPlace;
import com.example.magda.systeminformacyjny.network.DataRequestManager;
import com.example.magda.systeminformacyjny.network.ErrorResponse;
import com.example.magda.systeminformacyjny.network.SuccessResponse;
import com.example.magda.systeminformacyjny.utils.AbstractRecyclerViewEndlessAdapter;
import com.example.magda.systeminformacyjny.utils.Constants;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

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
 * Created by Wojciech on 13.04.2017.
 */

public class ActivityMainPlacesListViewModel implements Lifecycle.ViewModel,
        AbstractRecyclerViewEndlessAdapter.IErrorViewModel{

    private ArrayList<MainPlace> mainPlaces;
    private MainPlacesListActivity viewCallback;
    private SuccessResponse successResponse;
    private ErrorResponse errorResponse;
    private CompositeDisposable compositeDisposable;
    private Long categoryId;
    private DataRequestManager dataRequestManagera;

    private static final String TYPE = "namespace";

    public ActivityMainPlacesListViewModel(MainPlacesListActivity viewCallback) {
        this.viewCallback = viewCallback;
        this.compositeDisposable = new CompositeDisposable();
        this.dataRequestManagera = DataRequestManager.getInstance();
    }

    @Override
    public void onViewResumed() {
        if(successResponse != null) {
            onSuccessResponse();
        }

        if(errorResponse != null) {
            onErrorResponse();
        }
    }

    @Override
    public void onViewAttached(@NonNull Lifecycle.View viewCallback) {
        this.viewCallback = (MainPlacesListActivity) viewCallback;
    }

    public void downloadMainPLaces() {
        mainPlaces.clear();
        mainPlaces.add(new MainPlace(FULL_SCREEN_PROGRESS_BAR));
        downloadRequest();
    }

    public void downloadRequest() {
        Log.d("JESTEM", "download " + categoryId);
        dataRequestManagera.downloadMainPLacesFromCategory(categoryId, TYPE,
                viewCallback.getString(R.string.server_api_key)).subscribe(new MaybeObserver<List<MainPlace>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<MainPlace> value) {
                        mainPlaces.clear();
                        mainPlaces.addAll(value);
                        successResponse = new SuccessResponse(DOWNLOAD_SUCCESS);
                        onSuccessResponse();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                       // Log.d("JESTEM", ((HttpException)e).response().errorBody().toString());
                        Log.d("JESTEM", ((HttpException)e).response().raw().request().url().toString());
                        errorResponse = new ErrorResponse(DOWNLOAD_ERROR);
                        onErrorResponse();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
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

    @Override
    public void refreshAfterDownloadError() {
        downloadMainPLaces();
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setMainPlaces(ArrayList<MainPlace> mainPlaces) {
        this.mainPlaces = mainPlaces;
    }
}
