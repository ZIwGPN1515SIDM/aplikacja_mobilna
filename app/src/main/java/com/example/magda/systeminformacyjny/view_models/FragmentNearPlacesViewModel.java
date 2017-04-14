package com.example.magda.systeminformacyjny.view_models;

import android.util.Log;

import com.example.magda.systeminformacyjny.fragments.NearPlacesFragment;
import com.kontakt.sdk.android.ble.configuration.ActivityCheckConfiguration;
import com.kontakt.sdk.android.ble.configuration.ForceScanConfiguration;
import com.kontakt.sdk.android.ble.configuration.ScanMode;
import com.kontakt.sdk.android.ble.configuration.ScanPeriod;
import com.kontakt.sdk.android.ble.connection.OnServiceReadyListener;
import com.kontakt.sdk.android.ble.manager.ProximityManager;
import com.kontakt.sdk.android.ble.manager.ProximityManagerFactory;
import com.kontakt.sdk.android.ble.manager.listeners.EddystoneListener;
import com.kontakt.sdk.android.ble.manager.listeners.simple.EddystoneSpaceListener;
import com.kontakt.sdk.android.ble.rssi.RssiCalculators;
import com.kontakt.sdk.android.ble.spec.EddystoneFrameType;
import com.kontakt.sdk.android.common.profile.IEddystoneDevice;
import com.kontakt.sdk.android.common.profile.IEddystoneNamespace;

import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by piotrek on 09.04.17.
 */

public class FragmentNearPlacesViewModel {

    private NearPlacesFragment viewCallback;
    private ProximityManager proximityManager;

    public FragmentNearPlacesViewModel(NearPlacesFragment viewCallback) {
        this.viewCallback = viewCallback;
        setUpProximityManager();
    }

    private void setUpProximityManager() {
        proximityManager = ProximityManagerFactory.create(viewCallback.getContext());
        proximityManager.configuration()
                .scanMode(ScanMode.BALANCED)
                .scanPeriod(ScanPeriod.RANGING)
                .activityCheckConfiguration(ActivityCheckConfiguration.DEFAULT)
                .forceScanConfiguration(ForceScanConfiguration.MINIMAL)
                .deviceUpdateCallbackInterval(TimeUnit.SECONDS.toMillis(5))
                .rssiCalculator(RssiCalculators.DEFAULT)
                .monitoringEnabled(true)
                .monitoringSyncInterval(10)
                .eddystoneFrameTypes(EnumSet.of(EddystoneFrameType.UID,
                        EddystoneFrameType.URL,
                        EddystoneFrameType.TLM));
        proximityManager.setEddystoneListener(createEddystoneListener());
        proximityManager.setSpaceListener(createEddystoneSpaceListener());
    }

    public void onDestroy() {
        proximityManager.disconnect();
        proximityManager = null;
    }

    public void onStop() {
        proximityManager.stopScanning();
    }


    public void onStart() {
        startScanning();
    }



    private void startScanning() {
        proximityManager.connect(new OnServiceReadyListener() {
            @Override
            public void onServiceReady() {
                proximityManager.startScanning();
            }
        });
    }

    private EddystoneListener createEddystoneListener() {
        return new EddystoneListener() {
            @Override
            public void onEddystoneDiscovered(IEddystoneDevice eddystone, IEddystoneNamespace namespace) {
                //TO DO wykrycie beacona
                Log.d("JESTEM", "namspace " + eddystone.getNamespace() + "instance " + eddystone.getInstanceId());
            }

            @Override
            public void onEddystonesUpdated(List<IEddystoneDevice> eddystones, IEddystoneNamespace namespace) {

            }

            @Override
            public void onEddystoneLost(IEddystoneDevice eddystone, IEddystoneNamespace namespace) {
                //TO DO wyjscie ze strefy beacona
            }
        };
    }

    private EddystoneSpaceListener createEddystoneSpaceListener() {
        return new EddystoneSpaceListener() {
            @Override
            public void onNamespaceEntered(IEddystoneNamespace namespace) {
                //TO DO wejscie do neamspace danego miejsca
            }

            @Override
            public void onNamespaceAbandoned(IEddystoneNamespace namespace) {
                //TO DO wyjscie z danej strefy danego miejsca
            }
        };
    }

}
