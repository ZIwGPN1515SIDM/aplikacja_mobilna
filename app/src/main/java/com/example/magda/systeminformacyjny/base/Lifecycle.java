package com.example.magda.systeminformacyjny.base;

import android.support.annotation.NonNull;

/**
 * Created by piotrek on 19.02.17.
 */

public interface Lifecycle {

    interface View {
        void showToast(String message);
    }

    interface ViewModel {
        void onViewResumed();
        void onViewAttached(@NonNull View viewCallback);
        void onViewDetached();
        void onDestroy();
    }
}
