package com.example.magda.systeminformacyjny.base;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by piotrek on 19.02.17.
 */

public abstract class BaseActivity extends AppCompatActivity implements Lifecycle.View{

    protected abstract Lifecycle.ViewModel getViewModel();

    @Override
    protected void onResume() {
        super.onResume();
        getViewModel().onViewResumed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getViewModel().onViewAttached(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        getViewModel().onViewDetached();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getViewModel().onDestroy();
    }
}
