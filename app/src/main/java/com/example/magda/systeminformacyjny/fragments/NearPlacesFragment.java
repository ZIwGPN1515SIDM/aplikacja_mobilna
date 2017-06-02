package com.example.magda.systeminformacyjny.fragments;

import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.magda.systeminformacyjny.Manifest;
import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.activities.MainActivity;
import com.example.magda.systeminformacyjny.databinding.FragmentNearPlacesBinding;
import com.example.magda.systeminformacyjny.utils.Constants;
import com.example.magda.systeminformacyjny.view_models.FragmentNearPlacesViewModel;

/**
 * Created by piotrek on 09.04.17.
 */

public class NearPlacesFragment extends Fragment{

    private FragmentNearPlacesViewModel viewModel;
    int i = 1;

    public static NearPlacesFragment getInstance() {
        return new NearPlacesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentNearPlacesBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_near_places, null, false);
        viewModel = new FragmentNearPlacesViewModel(this);
        if(ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(getActivity(),new String[]{android.Manifest.permission.BLUETOOTH}, Constants.MY_PERMISSIONS_REQUEST_BLUETOOTH);
        return binding.getRoot();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constants.MY_PERMISSIONS_REQUEST_BLUETOOTH: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onDestroy() {
        viewModel.onDestroy();
        super.onDestroy();
    }


    @Override
    public void onStop() {
        viewModel.onStop();
        super.onStop();
    }

    @Override
    public void onStart() {
        viewModel.onStart();
        super.onStart();
    }
}
