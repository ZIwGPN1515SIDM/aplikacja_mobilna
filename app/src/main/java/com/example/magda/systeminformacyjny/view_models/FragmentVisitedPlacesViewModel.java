package com.example.magda.systeminformacyjny.view_models;

import android.support.annotation.NonNull;

import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.base.Lifecycle;
import com.example.magda.systeminformacyjny.fragments.VisitedPlacesPageFragment;
import com.example.magda.systeminformacyjny.models.MainPlace;
import com.example.magda.systeminformacyjny.network.DataRequestManager;
import com.example.magda.systeminformacyjny.network.ErrorResponse;
import com.example.magda.systeminformacyjny.network.SuccessResponse;
import com.example.magda.systeminformacyjny.utils.AbstractRecyclerViewEndlessAdapter;
import com.example.magda.systeminformacyjny.utils.Constants;
import com.example.magda.systeminformacyjny.utils.PreferencesManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.MaybeObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.example.magda.systeminformacyjny.network.ErrorResponse.DOWNLOAD_ERROR;
import static com.example.magda.systeminformacyjny.network.SuccessResponse.DOWNLOAD_SUCCESS;
import static com.example.magda.systeminformacyjny.utils.Constants.FULL_SCREEN_PROGRESS_BAR;

/**
 * Created by piotrek on 04.06.17.
 */

public class FragmentVisitedPlacesViewModel implements Lifecycle.ViewModel, AbstractRecyclerViewEndlessAdapter.IErrorViewModel{

    private VisitedPlacesPageFragment viewCallback;
    private CompositeDisposable compositeDisposable;
    private DataRequestManager dataRequestManager;
    private ErrorResponse errorResponse;
    private SuccessResponse successResponse;
    private ArrayList<MainPlace> mainPlaces;


    public FragmentVisitedPlacesViewModel(VisitedPlacesPageFragment viewCallback) {
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
        this.viewCallback = (VisitedPlacesPageFragment) viewCallback;
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

    public void download() {
        mainPlaces.clear();
        mainPlaces.add(new MainPlace(FULL_SCREEN_PROGRESS_BAR));
        viewCallback.notifyRecyclerView();
        downloadRequest();
    }

    public void downloadRequest() {
        String filter = "FB_USER_ID%3D" + PreferencesManager.getOurId(viewCallback.getContext());
        String order = "NAME%20ASC";
        String apiKey = viewCallback.getString(R.string.server_api_key);
        dataRequestManager.downloadVisitedPlaces(filter,order,apiKey).subscribe(new MaybeObserver<List<MainPlace>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(List<MainPlace> value) {
                mainPlaces.clear();
                mainPlaces.addAll(value);
                if(mainPlaces.size() == 0)
                    mainPlaces.add(null);
                successResponse = new SuccessResponse(DOWNLOAD_SUCCESS);
                onSuccessResponse();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                errorResponse = new ErrorResponse(DOWNLOAD_ERROR);
                onErrorResponse();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void setMainPlaces(ArrayList<MainPlace> mainPlaces) {
        this.mainPlaces = mainPlaces;
    }

    @Override
    public void refreshAfterDownloadError() {
        download();
    }
}
