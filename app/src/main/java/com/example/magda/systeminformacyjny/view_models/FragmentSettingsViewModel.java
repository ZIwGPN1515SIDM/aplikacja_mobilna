package com.example.magda.systeminformacyjny.view_models;

import android.content.Context;
import android.databinding.ObservableField;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.utils.PreferencesManager;

import static com.example.magda.systeminformacyjny.utils.Constants.BLUE_COLOR;
import static com.example.magda.systeminformacyjny.utils.Constants.DARK_MAP;
import static com.example.magda.systeminformacyjny.utils.Constants.GREEN_COLOR;
import static com.example.magda.systeminformacyjny.utils.Constants.RED_COLOR;
import static com.example.magda.systeminformacyjny.utils.Constants.RETRO_MAP;
import static com.example.magda.systeminformacyjny.utils.Constants.STANDARD_MAP;

/**
 * Created by piotrek on 06.04.17.
 */

public class FragmentSettingsViewModel {


    public ObservableField<Integer> standardMapImage;
    public ObservableField<Integer> retroMapImage;
    public ObservableField<Integer> darkMapImage;
    private Context context;


    public FragmentSettingsViewModel(Context context) {
        //TO DO
        this.context = context;
        int[] tmp = getMapView(context);
        standardMapImage = new ObservableField<>(tmp[0]);
        retroMapImage = new ObservableField<>(tmp[1]);
        darkMapImage = new ObservableField<>(tmp[2]);

    }

    public void sendNewsletter() {
        //TODO
    }

    public void onChooseMapMode(View view) {
        Log.d("JESTEM", "click");
        switch (view.getId()) {
            case R.id.standardMap:
                standardMapImage.set(R.mipmap.standard_map_chosen);
                retroMapImage.set(R.mipmap.retro_map);
                darkMapImage.set(R.mipmap.dark_map);
                PreferencesManager.setMapMode(context, STANDARD_MAP);
                break;
            case R.id.retroMap:
                standardMapImage.set(R.mipmap.standard_map);
                retroMapImage.set(R.mipmap.retro_map_chosen);
                darkMapImage.set(R.mipmap.dark_map);
                PreferencesManager.setMapMode(context, RETRO_MAP);
                break;
            case R.id.darkMap:
                standardMapImage.set(R.mipmap.standard_map);
                retroMapImage.set(R.mipmap.retro_map);
                darkMapImage.set(R.mipmap.dark_map_chosen);
                PreferencesManager.setMapMode(context, DARK_MAP);
                break;
        }
    }

    private void saveMapColor(int position) {

        switch (position) {
            case RED_COLOR:
                PreferencesManager.setRouteColor(context, Color.RED);
                break;
            case GREEN_COLOR:
                PreferencesManager.setRouteColor(context, Color.GREEN);
                break;
            case BLUE_COLOR:
                PreferencesManager.setRouteColor(context, Color.BLUE);
                break;
        }
    }

    private void saveMeasure(int position) {
        PreferencesManager.setMeasureType(context, position);
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
}
