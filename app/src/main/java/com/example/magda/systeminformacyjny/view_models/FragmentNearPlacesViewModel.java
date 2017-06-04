package com.example.magda.systeminformacyjny.view_models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.magda.systeminformacyjny.base.Lifecycle;
import com.example.magda.systeminformacyjny.fragments.NearPlacesFragment;
import com.example.magda.systeminformacyjny.network.DataRequestManager;
import com.kontakt.sdk.android.ble.configuration.ActivityCheckConfiguration;
import com.kontakt.sdk.android.ble.configuration.ForceScanConfiguration;
import com.kontakt.sdk.android.ble.configuration.ScanMode;
import com.kontakt.sdk.android.ble.configuration.ScanPeriod;
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

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by piotrek on 09.04.17.
 */

public class FragmentNearPlacesViewModel implements Lifecycle.ViewModel{

    private NearPlacesFragment viewCallback;
    private ProximityManager proximityManager;
    private DataRequestManager dataRequestManager;
    private CompositeDisposable compositeDisposable;

    public FragmentNearPlacesViewModel(NearPlacesFragment viewCallback) {
        this.viewCallback = viewCallback;
        this.dataRequestManager = DataRequestManager.getInstance();
        this.compositeDisposable = new CompositeDisposable();
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

    @Override
    public void onViewResumed() {

    }

    @Override
    public void onViewAttached(@NonNull Lifecycle.View viewCallback) {
        this.viewCallback = (NearPlacesFragment) viewCallback;
    }

    @Override
    public void onViewDetached() {
        this.viewCallback = null;
    }

    public void onDestroy() {
        proximityManager.disconnect();
        proximityManager = null;
        this.compositeDisposable.clear();
    }

    @Override
    public void onErrorResponse() {

    }

    @Override
    public void onSuccessResponse() {

    }

    public void onStop() {
        proximityManager.stopScanning();
    }


    public void onStart() {
        startScanning();
    }



    private void startScanning() {
        proximityManager.connect(() -> proximityManager.startScanning());
    }

    private EddystoneListener createEddystoneListener() {
        return new EddystoneListener() {
            @Override
            public void onEddystoneDiscovered(IEddystoneDevice eddystone, IEddystoneNamespace namespace) {
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

    public void sendNamespaceEntred() {

    }

    public void sendNamespaceLeave() {

    }

    public void sendInstanceEntred() {

    }

    public void sendInstanceLeave() {

    }

    public void downloadInstancePlace() {

    }

}
