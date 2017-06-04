package com.example.magda.systeminformacyjny.view_models;

import android.content.Context;
import android.databinding.ObservableField;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;

import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.base.Lifecycle;
import com.example.magda.systeminformacyjny.fragments.SettingsPageFragment;
import com.example.magda.systeminformacyjny.network.DataRequestManager;
import com.example.magda.systeminformacyjny.network.ErrorResponse;
import com.example.magda.systeminformacyjny.network.SuccessResponse;
import com.example.magda.systeminformacyjny.network.user.LoginRequest;
import com.example.magda.systeminformacyjny.network.user.NewsletterRequest;
import com.example.magda.systeminformacyjny.utils.Constants;
import com.example.magda.systeminformacyjny.utils.PreferencesManager;

import io.reactivex.MaybeObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

import static com.example.magda.systeminformacyjny.network.ErrorResponse.SEND_NEWSLETTER_ERROR;
import static com.example.magda.systeminformacyjny.network.SuccessResponse.SEND_NEWSLETTER_SUCCESS;
import static com.example.magda.systeminformacyjny.utils.Constants.BLUE_COLOR;
import static com.example.magda.systeminformacyjny.utils.Constants.DARK_MAP;
import static com.example.magda.systeminformacyjny.utils.Constants.GREEN_COLOR;
import static com.example.magda.systeminformacyjny.utils.Constants.RED_COLOR;
import static com.example.magda.systeminformacyjny.utils.Constants.RETRO_MAP;
import static com.example.magda.systeminformacyjny.utils.Constants.STANDARD_MAP;

/**
 * Created by piotrek on 06.04.17.
 */

public class FragmentSettingsViewModel  implements Lifecycle.ViewModel{

    public ObservableField<Integer> standardMapImage;
    public ObservableField<Integer> retroMapImage;
    public ObservableField<Integer> darkMapImage;
    private boolean sendNewsletterError;
    private DataRequestManager dataRequestManager;
    private CompositeDisposable compositeDisposable;
    private SettingsPageFragment viewCallback;
    private SuccessResponse successResponse;
    private ErrorResponse errorResponse;

    public FragmentSettingsViewModel(SettingsPageFragment viewCallback) {
        this.viewCallback = viewCallback;
        int[] tmp = getMapView(viewCallback.getContext());
        standardMapImage = new ObservableField<>(tmp[0]);
        retroMapImage = new ObservableField<>(tmp[1]);
        darkMapImage = new ObservableField<>(tmp[2]);
        sendNewsletterError = false;
        dataRequestManager = DataRequestManager.getInstance();
        compositeDisposable = new CompositeDisposable();

    }

    public void changeNewsletter(CompoundButton buttonView, boolean isChecked) {
        if(!sendNewsletterError) {
            String apiKey = viewCallback.getString(R.string.server_api_key);
            Long userId = PreferencesManager.getOurId(viewCallback.getContext());
            NewsletterRequest request = new NewsletterRequest(userId, isChecked);
            dataRequestManager.sendNewsletter(apiKey, request).subscribe(new MaybeObserver<ResponseBody>() {
                @Override
                public void onSubscribe(Disposable d) {
                    compositeDisposable.add(d);
                }

                @Override
                public void onSuccess(ResponseBody value) {
                    successResponse = new SuccessResponse(SEND_NEWSLETTER_SUCCESS);
                    onSuccessResponse();
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    errorResponse = new ErrorResponse(SEND_NEWSLETTER_ERROR);
                    onErrorResponse();
                }

                @Override
                public void onComplete() {

                }
            });
        }
    }

    public void onChooseMapMode(View view) {
        switch (view.getId()) {
            case R.id.standardMap:
                standardMapImage.set(R.mipmap.standard_map_chosen);
                retroMapImage.set(R.mipmap.retro_map);
                darkMapImage.set(R.mipmap.dark_map);
                PreferencesManager.setMapMode(viewCallback.getContext(), STANDARD_MAP);
                break;
            case R.id.retroMap:
                standardMapImage.set(R.mipmap.standard_map);
                retroMapImage.set(R.mipmap.retro_map_chosen);
                darkMapImage.set(R.mipmap.dark_map);
                PreferencesManager.setMapMode(viewCallback.getContext(), RETRO_MAP);
                break;
            case R.id.darkMap:
                standardMapImage.set(R.mipmap.standard_map);
                retroMapImage.set(R.mipmap.retro_map);
                darkMapImage.set(R.mipmap.dark_map_chosen);
                PreferencesManager.setMapMode(viewCallback.getContext(), DARK_MAP);
                break;
        }
    }

    private void saveMapColor(int position) {

        switch (position) {
            case RED_COLOR:
                PreferencesManager.setRouteColor(viewCallback.getContext(), Constants.RED_COLOR);
                break;
            case GREEN_COLOR:
                PreferencesManager.setRouteColor(viewCallback.getContext(), Constants.GREEN_COLOR);
                break;
            case BLUE_COLOR:
                PreferencesManager.setRouteColor(viewCallback.getContext(), Constants.BLUE_COLOR);
                break;
        }
    }

    private void saveMeasure(int position) {
        PreferencesManager.setMeasureType(viewCallback.getContext(), position);
    }


    public AdapterView.OnItemSelectedListener createMeasureAdapterListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                saveMeasure(parent.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    public AdapterView.OnItemSelectedListener createColorAdapterListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                saveMapColor(parent.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    private int[] getMapView(Context context) {
        int[] tmp = {R.mipmap.standard_map, R.mipmap.retro_map, R.mipmap.dark_map};
        switch (PreferencesManager.mapMode(context)) {
            case STANDARD_MAP: //standard
                tmp[0] = R.mipmap.standard_map_chosen;
                break;
            case RETRO_MAP: //retro
                tmp[1] = R.mipmap.retro_map_chosen;
                break;
            case DARK_MAP: //dark
                tmp[2] = R.mipmap.dark_map_chosen;
                break;
        }
        return tmp;
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
        this.viewCallback = (SettingsPageFragment) viewCallback;
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
        if(viewCallback != null) {
            viewCallback.onSuccess(successResponse);
            successResponse = null;
        }
    }

    public void setSendNewsletterError(boolean sendNewsletterError) {
        this.sendNewsletterError = sendNewsletterError;
    }
}
