package com.example.magda.systeminformacyjny.view_models;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.magda.systeminformacyjny.fragments.CreateRouteFragment;
import com.example.magda.systeminformacyjny.models.MainPlace;
import com.example.magda.systeminformacyjny.utils.AbstractRecyclerViewEndlessAdapter;

import java.util.ArrayList;

/**
 * Created by piotrek on 10.04.17.
 */

public class FragmentCreateRouteViewModel implements AbstractRecyclerViewEndlessAdapter.IErrorViewModel,
        AbstractRecyclerViewEndlessAdapter.OnLoadMoreListener{

    private CreateRouteFragment viewCallback;
    private String EMPTY_TEXT_ERROR = "Pusta nazwa drogi!";
    private ArrayList<MainPlace> mainPlaces;


    public FragmentCreateRouteViewModel(CreateRouteFragment viewCallback) {
        this.viewCallback = viewCallback;
    }

    public void addPlace(View v) {
        //TODO activity z pobierania miejsc :)
    }

    public void done(EditText editText) {
        String routeName = editText.getText().toString();
        if(routeName.trim().length() > 0) {
            //TODO wyslanie na serwer drogi
        } else {
            viewCallback.showToast(EMPTY_TEXT_ERROR);
        }

    }


    public void setMainPlaces(ArrayList<MainPlace> mainPlaces) {
        this.mainPlaces = mainPlaces;
    }

    @Override
    public void refreshAfterDownloadError() {

    }

    @Override
    public void onLoadMore() {

    }
}
