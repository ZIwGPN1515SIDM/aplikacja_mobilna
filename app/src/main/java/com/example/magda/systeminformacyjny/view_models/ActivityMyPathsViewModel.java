package com.example.magda.systeminformacyjny.view_models;

import android.support.annotation.NonNull;
import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.activities.MyPathsActivity;
import com.example.magda.systeminformacyjny.base.Lifecycle;
import com.example.magda.systeminformacyjny.network.DataRequestManager;
import com.example.magda.systeminformacyjny.network.ErrorResponse;
import com.example.magda.systeminformacyjny.network.SuccessResponse;
import com.example.magda.systeminformacyjny.network.items.PathName;
import com.example.magda.systeminformacyjny.utils.AbstractRecyclerViewEndlessAdapter;
import com.example.magda.systeminformacyjny.utils.RecyclerViewCurrentPathAdapter;

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
 * Created by piotrek on 03.06.17.
 */

public class ActivityMyPathsViewModel implements AbstractRecyclerViewEndlessAdapter.IErrorViewModel,
        Lifecycle.ViewModel {

    public MyPathsActivity viewCallback;
    private CompositeDisposable compositeDisposable;
    private ErrorResponse errorResponse;
    private SuccessResponse successResponse;
    private ArrayList<PathName> pathNames;
    private long pathId;
    private DataRequestManager dataRequestManager;

    public ActivityMyPathsViewModel(MyPathsActivity viewCallback) {
        this.viewCallback = viewCallback;
        this.compositeDisposable = new CompositeDisposable();
        this.dataRequestManager = DataRequestManager.getInstance();
    }

    @Override
    public void onViewResumed() {
        if (successResponse != null) {
            onSuccessResponse();
        } else if (errorResponse != null) {
            onErrorResponse();
        }
    }

    public void download() {
        pathNames.clear();
        pathNames.add(new PathName(FULL_SCREEN_PROGRESS_BAR));
        downloadRequest();
    }

    public void downloadRequest() {
        String filter = "ID%3D" + pathId;
        String apiKey = viewCallback.getString(R.string.server_api_key);
        dataRequestManager.downloadCurrentPath(filter, apiKey).subscribe(new MaybeObserver<List<PathName>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(List<PathName> value) {
                pathNames.clear();
                pathNames.addAll(value);
                if (value.size() == 0)
                    pathNames.add(null);
                successResponse = new SuccessResponse(DOWNLOAD_SUCCESS);
                onSuccessResponse();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                pathNames.clear();
                pathNames.add(new PathName(ERROR_INFO_VIEW_HOLDER));
                errorResponse = new ErrorResponse(DOWNLOAD_ERROR);
                onErrorResponse();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onViewAttached(@NonNull Lifecycle.View viewCallback) {
        this.viewCallback = (MyPathsActivity) viewCallback;
    }

    @Override
    public void onViewDetached() {
        viewCallback = null;
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
    }

    @Override
    public void onErrorResponse() {
        if (viewCallback != null) {
            viewCallback.onError(errorResponse);
            errorResponse = null;
        }
    }

    @Override
    public void onSuccessResponse() {
        if (viewCallback != null) {
            viewCallback.onSuccess(successResponse);
            successResponse = null;
        }
    }

    @Override
    public void refreshAfterDownloadError() {
        download();
    }

    public void setPathNames(ArrayList<PathName> pathNames) {
        this.pathNames = pathNames;
    }

    public void setPathId(long pathId) {
        this.pathId = pathId;
    }
}
