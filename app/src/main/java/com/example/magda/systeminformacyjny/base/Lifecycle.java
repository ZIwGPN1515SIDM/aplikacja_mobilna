package com.example.magda.systeminformacyjny.base;

import android.support.annotation.NonNull;

import com.example.magda.systeminformacyjny.network.ErrorResponse;
import com.example.magda.systeminformacyjny.network.SuccessResponse;

import java.util.List;

/**
 * Created by piotrek on 19.02.17.
 */

public interface Lifecycle {

    interface View {
        void showToast(String message);
        void onSuccess(SuccessResponse successResponse);
        void onError(ErrorResponse errorResponse);
    }

    interface ViewModel {
        void onViewResumed();
        void onViewAttached(@NonNull View viewCallback);
        void onViewDetached();
        void onDestroy();
        void onErrorResponse();
        void onSuccessResponse();
    }
}
