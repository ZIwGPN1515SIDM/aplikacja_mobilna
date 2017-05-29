package com.example.magda.systeminformacyjny.view_models;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.example.magda.systeminformacyjny.activities.CategoriesActivity;
import com.example.magda.systeminformacyjny.base.IMainPLaceViewModel;
import com.example.magda.systeminformacyjny.fragments.CreateRouteFragment;
import com.example.magda.systeminformacyjny.models.MainPlace;
import com.example.magda.systeminformacyjny.utils.AbstractRecyclerViewEndlessAdapter;
import com.example.magda.systeminformacyjny.utils.Constants;

import java.util.ArrayList;

import static com.example.magda.systeminformacyjny.utils.Constants.CURRENT_ROAD;
import static com.example.magda.systeminformacyjny.utils.Constants.CURRENT_ROAD_ACTIVITY_REQUEST_CODE;
import static com.example.magda.systeminformacyjny.utils.Constants.SHOW_SETTINGS_MAIN_PLACE_ITEM;

/**
 * Created by piotrek on 10.04.17.
 */

public class FragmentCreateRouteViewModel implements AbstractRecyclerViewEndlessAdapter.IErrorViewModel,
        IMainPLaceViewModel{

    private CreateRouteFragment viewCallback;
    private String EMPTY_TEXT_ERROR = "Pusta nazwa drogi!";
    private ArrayList<MainPlace> currentRoad;


    public FragmentCreateRouteViewModel(CreateRouteFragment viewCallback) {
        this.viewCallback = viewCallback;
    }

    public void addPlace(View v) {
        Intent intent = new Intent(viewCallback.getContext(), CategoriesActivity.class);
        intent.putExtra(SHOW_SETTINGS_MAIN_PLACE_ITEM, true);
        intent.putExtra(CURRENT_ROAD, currentRoad);
        viewCallback.startActivityForResult(intent, CURRENT_ROAD_ACTIVITY_REQUEST_CODE);
    }

    public void done(EditText editText) {
        String routeName = editText.getText().toString();
        if(routeName.trim().length() > 0) {
            //TODO wyslanie na serwer drogi
        } else {
            viewCallback.showToast(EMPTY_TEXT_ERROR);
        }

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
}
