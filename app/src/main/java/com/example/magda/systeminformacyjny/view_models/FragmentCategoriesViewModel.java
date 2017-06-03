package com.example.magda.systeminformacyjny.view_models;

import android.support.annotation.NonNull;

import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.base.Lifecycle;
import com.example.magda.systeminformacyjny.fragments.PlacesBasePageFragment;
import com.example.magda.systeminformacyjny.models.Category;
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
import static com.example.magda.systeminformacyjny.utils.Constants.FULL_SCREEN_PROGRESS_BAR;

/**
 * Created by JB on 2017-04-25.
 */

public class FragmentCategoriesViewModel implements AbstractRecyclerViewEndlessAdapter.IErrorViewModel,
        Lifecycle.ViewModel {

    private ArrayList<Category> categories;
    private PlacesBasePageFragment viewCallback;
    private CompositeDisposable compositeDisposable;
    private DataRequestManager dataRequestManager;

    private ErrorResponse errorResponse;
    private SuccessResponse successResponse;

    public FragmentCategoriesViewModel(PlacesBasePageFragment viewCallback) {
        this.viewCallback = viewCallback;
        this.compositeDisposable = new CompositeDisposable();
        dataRequestManager = DataRequestManager.getInstance();
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
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
        this.viewCallback = (PlacesBasePageFragment) viewCallback;
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
        download();
    }

    public void download() {
        String apiKey = viewCallback.getString(R.string.server_api_key);
        categories.clear();
        categories.add(new Category(FULL_SCREEN_PROGRESS_BAR));
        viewCallback.recyclerViewNotify();

        sendDownloadRequest(apiKey);
    }

    private void sendDownloadRequest(String apiKey) {
        dataRequestManager.downloadCategories(apiKey).subscribe(new MaybeObserver<List<Category>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(List<Category> value) {
                categories.clear();
                categories.addAll(value);
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

}
