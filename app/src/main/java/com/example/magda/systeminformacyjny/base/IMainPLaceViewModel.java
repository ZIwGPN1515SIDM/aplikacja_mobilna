package com.example.magda.systeminformacyjny.base;

import android.content.Context;

import com.example.magda.systeminformacyjny.models.MainPlace;

/**
 * Created by piotrek on 29.05.17.
 */

public interface IMainPLaceViewModel {

    void removeMainPlace(MainPlace mainPlace);
    void addMainPlace(MainPlace mainPlace);
    boolean containsMainPlace(MainPlace mainPlace);

}
