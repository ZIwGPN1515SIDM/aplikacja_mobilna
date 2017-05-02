package com.example.magda.systeminformacyjny.view_models;

import android.support.annotation.NonNull;

import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.base.Lifecycle;
import com.example.magda.systeminformacyjny.fragments.RatingPlaceFragment;
import com.example.magda.systeminformacyjny.models.Comment;
import com.example.magda.systeminformacyjny.models.IPlaceItem;
import com.example.magda.systeminformacyjny.network.DataRequestManager;
import com.example.magda.systeminformacyjny.network.ErrorResponse;
import com.example.magda.systeminformacyjny.network.SuccessResponse;
import com.example.magda.systeminformacyjny.utils.AbstractRecyclerViewEndlessAdapter;
import com.example.magda.systeminformacyjny.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.MaybeObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.example.magda.systeminformacyjny.network.ErrorResponse.DOWNLOAD_ERROR;
import static com.example.magda.systeminformacyjny.network.SuccessResponse.DOWNLOAD_SUCCESS;
import static com.example.magda.systeminformacyjny.utils.Constants.FULL_SCREEN_PROGRESS_BAR;

/**
 * Created by piotrek on 02.05.17.
 */

public class FragmentRatingPlaceViewModel implements Lifecycle.ViewModel,
        AbstractRecyclerViewEndlessAdapter.IErrorViewModel{

    private RatingPlaceFragment viewCallback;
    private String placeType;
    private IPlaceItem placeItem;
    private ArrayList<ErrorResponse> errorResponses;
    private ArrayList<SuccessResponse> successResponses;
    private CompositeDisposable compositeDisposable;
    private ArrayList<Comment> comments;
    private DataRequestManager dataRequestManager;

    public FragmentRatingPlaceViewModel(RatingPlaceFragment viewCallback) {
        this.viewCallback = viewCallback;
        compositeDisposable = new CompositeDisposable();
        this.dataRequestManager = DataRequestManager.getInstance();
        this.successResponses = new ArrayList<>();
        this.errorResponses = new ArrayList<>();
    }

    @Override
    public void onViewResumed() {
        if(successResponses.size() != 0) {
            onSuccessResponse();
        }else if(errorResponses.size() != 0) {
            onErrorResponse();
        }
    }

    @Override
    public void onViewAttached(@NonNull Lifecycle.View viewCallback) {
        this.viewCallback = (RatingPlaceFragment) viewCallback;
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
        if(viewCallback!= null) {
            for(ErrorResponse e: errorResponses)
                viewCallback.onError(e);
            errorResponses.clear();
        }
    }

    @Override
    public void onSuccessResponse() {
        if(viewCallback != null) {
            for (SuccessResponse s: successResponses)
                viewCallback.onSuccess(s);
            successResponses.clear();
        }
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public void setPlaceItem(IPlaceItem placeItem) {
        this.placeItem = placeItem;
    }


    @Override
    public void refreshAfterDownloadError() {
        comments.clear();
        comments.add(new Comment(FULL_SCREEN_PROGRESS_BAR));
        viewCallback.recyclerViewNotify();
        downloadComments();
    }

    public void downloadComments() {
        String apiKey = viewCallback.getString(R.string.server_api_key);
        dataRequestManager.downloadComments(placeType, placeItem.getId(), apiKey)
                .subscribe(new MaybeObserver<List<Comment>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<Comment> value) {
                        comments.clear();
                        comments.addAll(value);
                        if(comments.size() == 0)
                            comments.add(null);
                        successResponses.add(new SuccessResponse(DOWNLOAD_SUCCESS));
                        onSuccessResponse();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        errorResponses.add(new ErrorResponse(DOWNLOAD_ERROR));
                        onErrorResponse();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void sendComment() {
        //TODO
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
}
