package com.example.magda.systeminformacyjny.view_models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.base.Lifecycle;
import com.example.magda.systeminformacyjny.fragments.NearPlacesFragment;
import com.example.magda.systeminformacyjny.models.NearPlace;
import com.example.magda.systeminformacyjny.models.Place;
import com.example.magda.systeminformacyjny.models.VisitedNamespace;
import com.example.magda.systeminformacyjny.network.DataRequestManager;
import com.example.magda.systeminformacyjny.network.DefaultIdWrapper;
import com.example.magda.systeminformacyjny.network.DefaultResourceWrapper;
import com.example.magda.systeminformacyjny.network.ErrorResponse;
import com.example.magda.systeminformacyjny.network.SuccessResponse;
import com.example.magda.systeminformacyjny.network.items.SendEnteredEvent;
import com.example.magda.systeminformacyjny.network.items.SendLeaveEvent;
import com.example.magda.systeminformacyjny.utils.PreferencesManager;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.kontakt.sdk.android.ble.configuration.ActivityCheckConfiguration;
import com.kontakt.sdk.android.ble.configuration.ForceScanConfiguration;
import com.kontakt.sdk.android.ble.configuration.ScanMode;
import com.kontakt.sdk.android.ble.configuration.ScanPeriod;
import com.kontakt.sdk.android.ble.device.EddystoneNamespace;
import com.kontakt.sdk.android.ble.manager.ProximityManager;
import com.kontakt.sdk.android.ble.manager.ProximityManagerFactory;
import com.kontakt.sdk.android.ble.manager.listeners.EddystoneListener;
import com.kontakt.sdk.android.ble.manager.listeners.SpaceListener;
import com.kontakt.sdk.android.ble.manager.listeners.simple.EddystoneSpaceListener;
import com.kontakt.sdk.android.ble.rssi.RssiCalculators;
import com.kontakt.sdk.android.ble.spec.EddystoneFrameType;
import com.kontakt.sdk.android.common.profile.IBeaconRegion;
import com.kontakt.sdk.android.common.profile.IEddystoneDevice;
import com.kontakt.sdk.android.common.profile.IEddystoneNamespace;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.MaybeObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

import static com.example.magda.systeminformacyjny.network.SuccessResponse.DOWNLOAD_SUCCESS;

/**
 * Created by piotrek on 09.04.17.
 */

public class FragmentNearPlacesViewModel implements Lifecycle.ViewModel {

    private NearPlacesFragment viewCallback;
    private ProximityManager proximityManager;
    private DataRequestManager dataRequestManager;
    private CompositeDisposable compositeDisposable;
    private ArrayList<NearPlace> nearPlaces;
    private SuccessResponse successResponse;
    private ErrorResponse errorResponse;
    private ArrayList<VisitedNamespace> visitedNamespaces;


    public FragmentNearPlacesViewModel(NearPlacesFragment viewCallback) {
        this.viewCallback = viewCallback;
        this.dataRequestManager = DataRequestManager.getInstance();
        this.compositeDisposable = new CompositeDisposable();
        this.visitedNamespaces = new ArrayList<>();
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
        proximityManager.spaces().eddystoneNamespace(EddystoneNamespace.EVERYWHERE);
        setUpNamespaces();
    }

    private void setUpNamespaces() {

        Collection<IEddystoneNamespace> eddystoneNamespaces = new ArrayList<>();

        IEddystoneNamespace namespace1 = new EddystoneNamespace.Builder()
                .identifier("WhereToGo")
                .namespace("00000000000000000001")
                .build();

        IEddystoneNamespace namespace2 = new EddystoneNamespace.Builder()
                .identifier("WhereToGo")
                .namespace("00000000000000000002")
                .build();

        IEddystoneNamespace namespace3 = new EddystoneNamespace.Builder()
                .identifier("WhereToGo")
                .namespace("00000000000000000003")
                .build();

        eddystoneNamespaces.add(namespace1);
        eddystoneNamespaces.add(namespace2);
        eddystoneNamespaces.add(namespace3);

        proximityManager.spaces().eddystoneNamespaces(eddystoneNamespaces);
    }

    @Override
    public void onViewResumed() {
        if (successResponse != null) {
            onSuccessResponse();
        } else if (errorResponse != null) {
            onErrorResponse();
        }
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
        if (viewCallback != null) {
            viewCallback.onError(errorResponse);
            errorResponse = null;
        }
    }

    @Override
    public void onSuccessResponse() {
        if (viewCallback != null) {
            viewCallback.onSuccess(successResponse);
            successResponse = null;
        }
    }

    public void onStop() {
        proximityManager.stopScanning();
    }


    public void onStart() {
        startScanning();
    }

    private void startScanning() {
        //  setUpProximityManager();
        proximityManager.connect(() -> proximityManager.startScanning());
    }

    private EddystoneListener createEddystoneListener() {
        return new EddystoneListener() {
            @Override
            public void onEddystoneDiscovered(IEddystoneDevice eddystone, IEddystoneNamespace namespace) {
                if (viewCallback != null) {
                    viewCallback.getActivity().runOnUiThread(() -> sendInstanceEntered(eddystone.getInstanceId(), eddystone.getNamespace()));

                }
            }

            @Override
            public void onEddystonesUpdated(List<IEddystoneDevice> eddystones, IEddystoneNamespace namespace) {
                //   for(IEddystoneDevice e: eddystones) {
                //       if(getIndexOfPlaces(e.getNamespace(), e.getInstanceId()) == -1) {
                //         sendInstanceEntered(e.getInstanceId(), e.getNamespace());
                //     }
                //  }
            }

            @Override
            public void onEddystoneLost(IEddystoneDevice eddystone, IEddystoneNamespace namespace) {
                if (viewCallback != null) {
                    viewCallback.getActivity().runOnUiThread(() -> {
                        int index = getIndexOfPlaces(eddystone.getNamespace(), eddystone.getInstanceId());
                        //  Log.d("JESTEM", "edystone lost " + eddystone.getNamespace() + " " + eddystone.getInstanceId());
                        if (index != -1) {
                            sendInstanceLeave(nearPlaces.get(index).getId());
                            nearPlaces.remove(index);
                            if (nearPlaces.size() == 0)
                                nearPlaces.add(null);
                            viewCallback.notifyRecyclerView();
                        }
                    });
                }

            }
        };
    }

    private SpaceListener createEddystoneSpaceListener() {
        return new SpaceListener() {
            @Override
            public void onRegionEntered(IBeaconRegion region) {

            }

            @Override
            public void onRegionAbandoned(IBeaconRegion region) {

            }

            @Override
            public void onNamespaceEntered(IEddystoneNamespace namespace) {
                Log.d("JESTEM", "namespace visit " + namespace.getNamespace() + "instance " + namespace.getInstanceId());
                if (viewCallback != null) {
                    viewCallback.getActivity().runOnUiThread(() -> sendNamespaceEntered(namespace.getNamespace()));
                }


            }

            @Override
            public void onNamespaceAbandoned(IEddystoneNamespace namespace) {

                if (viewCallback != null) {
                    viewCallback.getActivity().runOnUiThread(() -> {
                        int index = visitedNamespaces.indexOf(new VisitedNamespace(namespace.getNamespace()));
                        if (index != -1) {
                            sendNamespaceLeave(visitedNamespaces.get(index).getId());
                            visitedNamespaces.remove(index);
                        }
                    });
                }
            }
        };
    }

    public void sendNamespaceEntered(String namespace) {
        if (viewCallback != null) {
            String apiKey = viewCallback.getString(R.string.server_api_key);
            Long userId = PreferencesManager.getOurId(viewCallback.getContext());
            SendEnteredEvent sendEnteredEvent = new SendEnteredEvent(userId, namespace, null);
            dataRequestManager.sendEnteredNamespace(apiKey, sendEnteredEvent).subscribe(new MaybeObserver<DefaultResourceWrapper<DefaultIdWrapper>>() {
                @Override
                public void onSubscribe(Disposable d) {
                    compositeDisposable.add(d);
                }

                @Override
                public void onSuccess(DefaultResourceWrapper<DefaultIdWrapper> value) {
                    visitedNamespaces.add(new VisitedNamespace(namespace, value.getResource().get(0).getId()));
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    // try {
                    //      Log.d("JESTEM", ((HttpException)e).response().errorBody().string().toString());
                    //  } catch (IOException e1) {
                    //      e1.printStackTrace();
                    //  }
                }

                @Override
                public void onComplete() {

                }
            });
        }
    }

    public void sendNamespaceLeave(Long id) {
        if (viewCallback != null) {
            String apiKey = viewCallback.getString(R.string.server_api_key);
            SendLeaveEvent sendLeaveEvent = new SendLeaveEvent(id, getCurrentDate());
            dataRequestManager.sendLeaveNamespace(apiKey, sendLeaveEvent).subscribe(new MaybeObserver<ResponseBody>() {
                @Override
                public void onSubscribe(Disposable d) {
                    compositeDisposable.add(d);
                }

                @Override
                public void onSuccess(ResponseBody value) {
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onComplete() {

                }
            });
        }
    }

    public void sendInstanceEntered(String instance, String namespace) {
        if (viewCallback != null) {
            String apiKey = viewCallback.getString(R.string.server_api_key);
            Long userId = PreferencesManager.getOurId(viewCallback.getContext());
            SendEnteredEvent sendEnteredEvent = new SendEnteredEvent(userId, namespace, instance);
            dataRequestManager.sendEnteredInstance(apiKey, sendEnteredEvent).subscribe(new MaybeObserver<DefaultResourceWrapper<DefaultIdWrapper>>() {
                @Override
                public void onSubscribe(Disposable d) {
                    compositeDisposable.add(d);
                }

                @Override
                public void onSuccess(DefaultResourceWrapper<DefaultIdWrapper> value) {

                    nearPlaces.add(new NearPlace(value.getResource().get(0).getId()));
                    downloadInstancePlace(value.getResource().get(0).getId(), namespace, instance);
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onComplete() {

                }
            });
        }
    }

    public void sendInstanceLeave(Long id) {
        if (viewCallback != null) {
            String apiKey = viewCallback.getString(R.string.server_api_key);
            SendLeaveEvent sendLeaveEvent = new SendLeaveEvent(id, getCurrentDate());
            dataRequestManager.sendLeaveInstance(apiKey, sendLeaveEvent).subscribe(new MaybeObserver<ResponseBody>() {
                @Override
                public void onSubscribe(Disposable d) {
                    compositeDisposable.add(d);
                }

                @Override
                public void onSuccess(ResponseBody value) {

                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onComplete() {

                }
            });
        }
    }

    public void downloadInstancePlace(Long id, String namespace, String instance) {
        if (viewCallback != null) {
            String apiKey = viewCallback.getString(R.string.server_api_key);
            String type = "place";
            dataRequestManager.downloadPlace(apiKey, type, instance, namespace).subscribe(new MaybeObserver<Place>() {
                @Override
                public void onSubscribe(Disposable d) {
                    compositeDisposable.add(d);
                }

                @Override
                public void onSuccess(Place value) {
                    int index = nearPlaces.indexOf(new NearPlace(id));
                    if (index != -1) {
                        nearPlaces.get(index).setPlace(value);
                        nearPlaces.get(index).getPlace().setNamespace(namespace);
                        nearPlaces.remove(null);
                        successResponse = new SuccessResponse(DOWNLOAD_SUCCESS);
                        onSuccessResponse();
                    }

                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    int index = nearPlaces.indexOf(new NearPlace(id));
                    if (index != -1) {
                        nearPlaces.remove(index);
                    }
                }

                @Override
                public void onComplete() {

                }
            });
        }
    }

    private int getIndexOfPlaces(String namespace, String instance) {
        int index = -1;
        for (NearPlace n : nearPlaces) {
            index++;
            if (n != null && n.getPlace() != null && n.getPlace().getInstance().equals(instance)
                    && n.getPlace().getNamespace().equals(namespace))
                return index;
        }
        return -1;
    }

    public void setNearPlaces(ArrayList<NearPlace> nearPlaces) {
        this.nearPlaces = nearPlaces;
    }

    private String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return df.format(c.getTime());
    }
}
