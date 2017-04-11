package com.example.magda.systeminformacyjny.view_models;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.activities.CategoryActivity;
import com.example.magda.systeminformacyjny.base.Lifecycle;
import com.example.magda.systeminformacyjny.models.Category;
import com.example.magda.systeminformacyjny.network.DataRequestManager;
import com.example.magda.systeminformacyjny.utils.AbstractRecyclerViewEndlessAdapter;
import com.example.magda.systeminformacyjny.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.MaybeObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.example.magda.systeminformacyjny.utils.Constants.FULL_SCREEN_PROGRESS_BAR;

/**
 * Created by piotrek on 10.04.17.
 */

public class ActivityCategoriesViewModel implements AbstractRecyclerViewEndlessAdapter.OnLoadMoreListener,
        AbstractRecyclerViewEndlessAdapter.IErrorViewModel, Lifecycle.ViewModel {

    private ArrayList<Category> categories;
    private CategoryActivity viewCallback;
    private CompositeDisposable compositeDisposable;
    private DataRequestManager dataRequestManager;

    public ActivityCategoriesViewModel(CategoryActivity viewCallback) {
        this.viewCallback = viewCallback;
        this.compositeDisposable = new CompositeDisposable();
        dataRequestManager = DataRequestManager.getInstance();
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }


    public void openCategory(Category category) {
        //TODO
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void refreshAfterDownloadError() {

    }

    @Override
    public void onViewResumed() {

    }

    @Override
    public void onViewAttached(@NonNull Lifecycle.View viewCallback) {
        this.viewCallback = (CategoryActivity) viewCallback;
    }

    @Override
    public void onViewDetached() {
        this.viewCallback = null;
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
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
                viewCallback.recyclerViewNotify();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
