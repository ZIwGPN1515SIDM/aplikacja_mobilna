package com.example.magda.systeminformacyjny.view_models;

import android.support.annotation.NonNull;

import com.example.magda.systeminformacyjny.activities.PlacesActivity;
import com.example.magda.systeminformacyjny.base.Lifecycle;
import com.example.magda.systeminformacyjny.models.Place;
import com.example.magda.systeminformacyjny.network.DataRequestManager;
import com.example.magda.systeminformacyjny.network.ErrorResponse;
import com.example.magda.systeminformacyjny.network.SuccessResponse;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by piotrek on 22.04.17.
 */

public class ActivityPlacesViewModel implements Lifecycle.ViewModel{

    private ArrayList<Place> places;
    private PlacesActivity viewCallback;
    private DataRequestManager dataRequestManager;
    private CompositeDisposable compositeDisposable;
    private ErrorResponse errorResponse;
    private SuccessResponse successResponse;

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

            errorResponse = null;
        }
    }

    @Override
    public void onSuccessResponse() {
        if(viewCallback != null) {


            successResponse = null;
        }
    }

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }
}
