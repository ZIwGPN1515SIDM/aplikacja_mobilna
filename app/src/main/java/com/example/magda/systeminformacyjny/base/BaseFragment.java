package com.example.magda.systeminformacyjny.base;

import android.support.v4.app.Fragment;

/**
 * Created by piotrek on 19.04.17.
 */

public abstract class BaseFragment extends Fragment implements Lifecycle.View{

    protected abstract Lifecycle.ViewModel getViewModel();

    @Override
    public void onResume() {
        super.onResume();
        getViewModel().onViewResumed();
    }

    @Override
    public void onStart() {
        super.onStart();
        getViewModel().onViewAttached(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        getViewModel().onViewDetached();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getViewModel().onDestroy();
    }
}
