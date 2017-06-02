package com.example.magda.systeminformacyjny.fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.activities.LocationActivity;
import com.example.magda.systeminformacyjny.activities.LoginActivity;
import com.example.magda.systeminformacyjny.databinding.FragmentMainPageBinding;
import com.example.magda.systeminformacyjny.utils.LocationProvider;
import com.example.magda.systeminformacyjny.utils.PreferencesManager;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.example.magda.systeminformacyjny.utils.Constants.DARK_MAP;
import static com.example.magda.systeminformacyjny.utils.Constants.RETRO_MAP;
import static com.example.magda.systeminformacyjny.utils.Constants.STANDARD_MAP;


/**
 * Created by piotrek on 05.04.17.
 */

public class MainPageFragment extends Fragment implements OnMapReadyCallback, LocationProvider.LocationCallback {

    private GoogleMap googleMap;
    private boolean shouldRepeatPermission;
    private static final int REQUEST_LOCATION_PERMISSION_CODE = 123;
    private boolean isFirstLocationUpdate;
    private ArrayList<LatLng> locations = new ArrayList();
    private Polyline polyLine = null;
    private RequestQueue requestQueue;
    private MapView gMapView;
    private LocationProvider locationProvider;
    private AtomicBoolean enableFocusOnUserLocation;


    public static MainPageFragment getInstance() {
        return new MainPageFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentMainPageBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_page, null, false);
        gMapView = binding.map;
        locationProvider = new LocationProvider(getContext(), this);
        locations.add(new LatLng(51.1097, 17.0328));
        locations.add(new LatLng(51.1097, 17.0418));
        locations.add(new LatLng(51.1055, 17.0313));
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        gMapView.onCreate(savedInstanceState);
        gMapView.getMapAsync(this);
        isFirstLocationUpdate = true;
        setRequestQueue();
        enableFocusOnUserLocation = new AtomicBoolean(true);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            gMapView.onResume();
                 locationProvider.connect();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        gMapView.onPause();
            locationProvider.disconnect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        gMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        gMapView.onLowMemory();
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
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMyLocationButtonClickListener(() -> {
            enableFocusOnUserLocation.set(true);
            return false;
        });
    }



    private void setRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(getContext());
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

    @Override
    public void handleNewLocation(Location location) {

            double currentLatitude = location.getLatitude();
            double currentLongitude = location.getLongitude();
            LatLng latLng = new LatLng(currentLatitude, currentLongitude);
            CameraUpdate camUpdate;
            if (isFirstLocationUpdate) {
                camUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
                googleMap.animateCamera(camUpdate);
                isFirstLocationUpdate = false;}
//            } else if (enableFocusOnUserLocation.get()) {
//                camUpdate = CameraUpdateFactory.newLatLng(latLng);
//                googleMap.animateCamera(camUpdate);

            //calculateDistanceToLocationAndShowDialogInfo(latLng, mapLocations);
            downloadNewPath(latLng, locations);
    }

    private void downloadNewPath(LatLng myLocation, ArrayList<LatLng> locations) {
        String url = createURLForPath(myLocation, locations, getContext());
        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(url, null,
                response -> {
                    try {
                        drawPath(parseJSONGoogleMaps(response));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);
        jsonObjectReq.setShouldCache(false);
        requestQueue.add(jsonObjectReq);
    }

    private void drawPath(List<LatLng> latLngs) {
        PolylineOptions lineOptions = new PolylineOptions();
        lineOptions.addAll(latLngs);
        lineOptions.width(10);
        lineOptions.color(Color.RED);
        if (latLngs != null && latLngs.size() != 0) {
            Polyline tmpPolyline = polyLine;
            polyLine = googleMap.addPolyline(lineOptions);
            if (tmpPolyline != null) {
                tmpPolyline.remove();
            }

        }
    }

    private String createURLForPath(LatLng myLocation, List<LatLng> locations, Context context) {
        String url = "https://maps.googleapis.com/maps/api/directions/json?";
        // walking&
        String mode = "mode=walking"
                + "&";
        String origin = "origin=" + myLocation.latitude + "," + myLocation.longitude + "&";
        String destination = "destination=" + locations.get(locations.size() - 1).latitude +
                "," + locations.get(locations.size() - 1).longitude + "&";
        String waypoints = "waypoints=";
        for (int i = 0; i < locations.size() - 1; i++) {
            waypoints += locations.get(i).latitude + "," + locations.get(i).longitude;
            if (i != locations.size() - 2)
                waypoints += "|";
        }
        waypoints += "&";
        String apiKey = "key=" + context.getString(R.string.google_maps_key);
        url += mode + origin + waypoints + destination + apiKey;
        return url;
    }

    public static List<LatLng> parseJSONGoogleMaps(JSONObject JSON) throws JSONException {
        List<LatLng> latLngs;
        JSONObject mainObj = JSON;
        JSONArray routesArray = mainObj.getJSONArray("routes");
        JSONObject routeObj = routesArray.getJSONObject(0);
        JSONObject opObj = routeObj.getJSONObject("overview_polyline");
        String tmp = opObj.getString("points");
        latLngs = PolyUtil.decode(tmp);
        return latLngs;
    }

    public boolean startGoogleMaps() {
        if (gMapView != null) {
            if (googleMap == null) {
                gMapView.getMapAsync(this);
            } else {
                gMapView.onResume();
            }
            return true;
        } else {
            return false;
        }
    }

}