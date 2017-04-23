package com.example.magda.systeminformacyjny.view_models;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.example.magda.systeminformacyjny.activities.PlacesActivity;
import com.example.magda.systeminformacyjny.base.Lifecycle;

import static com.example.magda.systeminformacyjny.activities.PlacesActivity.NAMESPACE_ID_TAG;

/**
 * Created by piotrek on 18.04.17.
 */

public class ActivityLocationViewModel {

    public void openPlacesList(View view, Long namespaceId) {
        Intent intent = new Intent(view.getContext(), PlacesActivity.class);
        intent.putExtra(NAMESPACE_ID_TAG, namespaceId);
        view.getContext().startActivity(intent);
    }
}
