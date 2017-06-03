package com.example.magda.systeminformacyjny.view_models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.base.Lifecycle;
import com.example.magda.systeminformacyjny.fragments.MyPathsFragment;
import com.example.magda.systeminformacyjny.network.DataRequestManager;
import com.example.magda.systeminformacyjny.network.ErrorResponse;
import com.example.magda.systeminformacyjny.network.SuccessResponse;
import com.example.magda.systeminformacyjny.network.items.PathResponse;
import com.example.magda.systeminformacyjny.utils.AbstractRecyclerViewEndlessAdapter;
import com.example.magda.systeminformacyjny.utils.PreferencesManager;
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
 * Created by piotrek on 01.06.17.
 */

public class FragmentMyPathsViewModel implements AbstractRecyclerViewEndlessAdapter.IErrorViewModel,
        Lifecycle.ViewModel{

    private MyPathsFragment viewCallback;
    private CompositeDisposable compositeDisposable;
    private DataRequestManager dataRequestManager;
    private ErrorResponse errorResponse;
    private SuccessResponse successResponse;
    private ArrayList<PathResponse> pathNames;

    public FragmentMyPathsViewModel(MyPathsFragment viewCallback) {
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


    public void downloadPathListRequest() {
        String apiKey = viewCallback.getString(R.string.server_api_key);
        String fields = "ID%20%2CCREATED_ON%20%2CNAME";
        String filter = "FB_USER_ID%3D" + PreferencesManager.getOurId(viewCallback.getContext());
        dataRequestManager.downloadPaths(fields, filter, apiKey).subscribe(new MaybeObserver<List<PathResponse>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(List<PathResponse> value) {
                pathNames.clear();
                pathNames.addAll(value);
                if(value.size() == 0)
                    pathNames.add(null);
                successResponse = new SuccessResponse(DOWNLOAD_SUCCESS);
                onSuccessResponse();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                pathNames.clear();
                pathNames.add(new PathResponse(ERROR_INFO_VIEW_HOLDER));
                errorResponse = new ErrorResponse(DOWNLOAD_ERROR);
                onErrorResponse();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void download() {
        pathNames.clear();
        pathNames.add(new PathResponse(FULL_SCREEN_PROGRESS_BAR));
        downloadPathListRequest();
    }

    @Override
    public void onViewAttached(@NonNull Lifecycle.View viewCallback) {
        this.viewCallback = (MyPathsFragment) viewCallback;
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
        if(viewCallback!= null) {
            viewCallback.onSuccess(successResponse);
            successResponse = null;
        }
    }

    @Override
    public void refreshAfterDownloadError() {
       download();
    }

    public void setPathNames(ArrayList<PathResponse> pathNames) {
        this.pathNames = pathNames;
    }
}
