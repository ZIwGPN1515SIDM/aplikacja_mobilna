package com.example.magda.systeminformacyjny.view_models;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.example.magda.systeminformacyjny.activities.PlacesActivity;
import com.example.magda.systeminformacyjny.base.Lifecycle;

/**
 * Created by piotrek on 18.04.17.
 */

public class ActivityLocationViewModel {

    public void openPlacesList(View view) {
        Intent intent = new Intent(view.getContext(), PlacesActivity.class);
        view.getContext().startActivity(intent);
    }
}
