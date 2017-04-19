package com.example.magda.systeminformacyjny.view_models;

import android.support.annotation.NonNull;

import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.base.Lifecycle;
import com.example.magda.systeminformacyjny.fragments.EventsPlaceFragment;
import com.example.magda.systeminformacyjny.models.Event;
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

/**
 * Created by piotrek on 19.04.17.
 */

public class FragmentEventsPlaceFragmentViewModel implements Lifecycle.ViewModel,
        AbstractRecyclerViewEndlessAdapter.IErrorViewModel{

    private EventsPlaceFragment viewCallback;
    private CompositeDisposable compositeDisposable;
    private ErrorResponse errorResponse;
    private SuccessResponse successResponse;
    private DataRequestManager dataRequestManager;
    private String namespace;
    private ArrayList<Event> events;

    private static final String DOWNLOAD_TYPE = "namespace";

    public FragmentEventsPlaceFragmentViewModel(EventsPlaceFragment viewCallback) {
        this.viewCallback = viewCallback;
        this.dataRequestManager = DataRequestManager.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onViewResumed() {
        if(successResponse!= null) {
            onSuccessResponse();
        }else if(errorResponse != null) {
            onErrorResponse();
        }
    }

    public void downloadEvents() {
        String apiKey = viewCallback.getString(R.string.server_api_key);
        dataRequestManager.downloadEvents(DOWNLOAD_TYPE,namespace, apiKey)
                .subscribe(new MaybeObserver<List<Event>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<Event> value) {
                        events.clear();
                        events.addAll(value);
                        if(events.size() == 0)
                            events.add(null); //dla wyswietlenia pustej listy eventów
                        successResponse = new SuccessResponse(DOWNLOAD_SUCCESS);
                        //TODO
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        errorResponse = new ErrorResponse(DOWNLOAD_ERROR);
                        //TODO
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onViewAttached(@NonNull Lifecycle.View viewCallback) {
        this.viewCallback = (EventsPlaceFragment) viewCallback;
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

    @Override
    public void refreshAfterDownloadError() {
        //TODO wyrzucenie widoku errora :)
        downloadEvents();
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
}
