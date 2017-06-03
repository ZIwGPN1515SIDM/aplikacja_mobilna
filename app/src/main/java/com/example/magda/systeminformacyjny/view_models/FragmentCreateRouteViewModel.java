package com.example.magda.systeminformacyjny.view_models;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.activities.CategoriesActivity;
import com.example.magda.systeminformacyjny.base.IMainPLaceViewModel;
import com.example.magda.systeminformacyjny.base.Lifecycle;
import com.example.magda.systeminformacyjny.fragments.CreateRouteFragment;
import com.example.magda.systeminformacyjny.models.MainPlace;
import com.example.magda.systeminformacyjny.network.DataRequestManager;
import com.example.magda.systeminformacyjny.network.DefaultResourceWrapper;
import com.example.magda.systeminformacyjny.network.ErrorResponse;
import com.example.magda.systeminformacyjny.network.SuccessResponse;
import com.example.magda.systeminformacyjny.network.items.CurrentPath;
import com.example.magda.systeminformacyjny.network.items.PathName;
import com.example.magda.systeminformacyjny.utils.AbstractRecyclerViewEndlessAdapter;
import com.example.magda.systeminformacyjny.utils.PreferencesManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.MaybeObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

import static com.example.magda.systeminformacyjny.network.ErrorResponse.SEND_PATH_ERROR;
import static com.example.magda.systeminformacyjny.network.SuccessResponse.SEND_PATH_SUCCESS;
import static com.example.magda.systeminformacyjny.utils.Constants.CURRENT_ROAD;
import static com.example.magda.systeminformacyjny.utils.Constants.CURRENT_ROAD_ACTIVITY_REQUEST_CODE;
import static com.example.magda.systeminformacyjny.utils.Constants.SHOW_SETTINGS_MAIN_PLACE_ITEM;

/**
 * Created by piotrek on 10.04.17.
 */

public class FragmentCreateRouteViewModel implements AbstractRecyclerViewEndlessAdapter.IErrorViewModel,
        IMainPLaceViewModel, Lifecycle.ViewModel{

    private CreateRouteFragment viewCallback;
    private String EMPTY_TEXT_ERROR = "Pusta nazwa drogi!";
    private ArrayList<MainPlace> currentRoad;
    private CompositeDisposable compositeDisposable;
    private DataRequestManager dataRequestManager;
    private ErrorResponse errorResponse;
    private SuccessResponse successResponse;


    public FragmentCreateRouteViewModel(CreateRouteFragment viewCallback) {
        this.viewCallback = viewCallback;
        this.dataRequestManager = DataRequestManager.getInstance();
        this.compositeDisposable = new CompositeDisposable();
    }

    public void addPlace(View v) {
        Intent intent = new Intent(viewCallback.getContext(), CategoriesActivity.class);
        intent.putExtra(SHOW_SETTINGS_MAIN_PLACE_ITEM, true);
        intent.putExtra(CURRENT_ROAD, currentRoad);
        viewCallback.startActivityForResult(intent, CURRENT_ROAD_ACTIVITY_REQUEST_CODE);
    }

    public void done(EditText editText) {
        hideKeyboard(editText);
        String routeName = editText.getText().toString();
        if(routeName.trim().length() > 0) {
            sendRoute(routeName);
        } else {
            viewCallback.showToast(EMPTY_TEXT_ERROR);
        }

    }

    private void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) viewCallback.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    private void sendRoute(String name) {
        Gson gson = new Gson();
        String apiKey = viewCallback.getString(R.string.server_api_key);
        CurrentPath currentPath = new CurrentPath(PreferencesManager.getOurId(viewCallback.getContext()),
                name, gson.toJson(createPathNames()));
        Log.d("JESTEM", gson.toJson(new DefaultResourceWrapper<>(currentPath), DefaultResourceWrapper.class));
        viewCallback.showProgressDialog();
        dataRequestManager.sendPath(apiKey, new DefaultResourceWrapper<>(currentPath))
                .subscribe(new MaybeObserver<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(ResponseBody value) {
                        Log.d("JESTEM", "wysłano :) ");
                        successResponse = new SuccessResponse(SEND_PATH_SUCCESS);
                        onSuccessResponse();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        errorResponse = new ErrorResponse(SEND_PATH_ERROR);
                        onErrorResponse();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private List<PathName> createPathNames() {
        ArrayList<PathName> pathNames = new ArrayList<>();
        for(MainPlace m : currentRoad) {
            pathNames.add(new PathName(m.getId(), m.getName()));
        }
        return pathNames;
    }

    public void setCurrentRoad(ArrayList<MainPlace> currentRoad) {
        this.currentRoad = currentRoad;
    }

    @Override
    public void refreshAfterDownloadError() {
        //leave empty
    }

    @Override
    public void removeMainPlace(MainPlace mainPlace) {
        int index = currentRoad.indexOf(mainPlace);
        currentRoad.remove(index);
        viewCallback.removeItem(index);
    }

    @Override
    public void addMainPlace(MainPlace mainPlace) {
        //leave empty
    }

    @Override
    public boolean containsMainPlace(MainPlace mainPlace) {
        return currentRoad.contains(mainPlace);
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
        this.viewCallback = (CreateRouteFragment) viewCallback;
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
}
