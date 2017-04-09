package com.example.magda.systeminformacyjny.fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.databinding.FragmentMainPageBinding;
import com.example.magda.systeminformacyjny.utils.PreferencesManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MapStyleOptions;

import static com.example.magda.systeminformacyjny.utils.Constants.DARK_MAP;
import static com.example.magda.systeminformacyjny.utils.Constants.RETRO_MAP;
import static com.example.magda.systeminformacyjny.utils.Constants.STANDARD_MAP;


/**
 * Created by piotrek on 05.04.17.
 */

public class MainPageFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private boolean shouldRepeatPermission;
    private static final int REQUEST_LOCATION_PERMISSION_CODE = 123;

    public static MainPageFragment getInstance() {
        return new MainPageFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentMainPageBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_page, null, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return binding.getRoot();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            setUpGoogleMaps();
        } else {
            requestForPermissions();
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Anuluj", null)
                .create()
                .show();
    }

    @SuppressWarnings("MissingPermission")
    private void setUpGoogleMaps() {
        this.googleMap.setMyLocationEnabled(true);
        this.googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        this.googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(
                getContext(), getMapStyle()));
    }

    private int getMapStyle() {
        int mapMode;
        switch (PreferencesManager.mapMode(getContext())) {
            case STANDARD_MAP:
                mapMode=  R.raw.sandard_map;
            break;
            case RETRO_MAP:
                mapMode =  R.raw.retro_map;
            break;
            case DARK_MAP:
                mapMode =  R.raw.dark_map;
            break;
            default:
                mapMode =  R.raw.sandard_map;
        }
        return mapMode;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    setUpGoogleMaps();
                } else {
                    // Permission Denied
                    if (!shouldRepeatPermission) {
                        Toast.makeText(getContext(), "Odmowa lokalizacji!!", Toast.LENGTH_SHORT)
                                .show();
                        //TO DO SHOW ERROR AND CLOSE APPLICATION :)
                    } else {
                        requestForPermissions();
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void requestForPermissions() {
        if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            showMessageOKCancel("Musisz dać dostęp do lokalizacji!",
                    (dialog, which) -> requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_LOCATION_PERMISSION_CODE));
            return;
        } else {
            shouldRepeatPermission = true;
        }
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_LOCATION_PERMISSION_CODE);
    }

}
