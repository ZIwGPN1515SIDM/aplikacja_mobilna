package com.example.magda.systeminformacyjny.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.databinding.ActivityLoginBinding;
import com.example.magda.systeminformacyjny.network.DataRequestManager;
import com.example.magda.systeminformacyjny.network.user.LoginRequest;
import com.example.magda.systeminformacyjny.network.user.LoginResponse;
import com.example.magda.systeminformacyjny.utils.PreferencesManager;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;


public class LoginActivity extends AppCompatActivity implements OnMapReadyCallback {

    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    private LoginButton loginButton;
    private ProgressDialog progressDialog;
    private DataRequestManager dataRequestManager;

    private static final String PROGRESS_DIALOG_TITLE = "Logowanie";
    private static final String PROGRESS_DIALOG_MESSAGE = "Trwa logowanie...";
    private static final String FACEBOOK_ID_TAG = "idFacebook";
    private static final String FACEBOOK_EMAIL_TAG = "email";
    private static final String FACEBOOK_NAME_TAG = "first_name";
    private static final String FACEBOOK_LAST_NAME_TAG = "last_name";
    private static final String FACEBOOK_PIC_TAG = "profile_pic";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginButton = binding.loginButton;
        initFacebookLogin();

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        dataRequestManager = DataRequestManager.getInstance();
    }

    private void initFacebookLogin() {
        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
            }
        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();

        FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setTitle(PROGRESS_DIALOG_TITLE);
                progressDialog.setMessage(PROGRESS_DIALOG_MESSAGE);
                progressDialog.setIndeterminate(true);
                progressDialog.show();
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), (object, response) -> {
                    try {
                        Bundle bFacebookData = getFacebookData(object);
                        finishLogin(bFacebookData);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        };
        //loginButton.setReadPermissions("user_friends");
        loginButton.registerCallback(callbackManager, callback);
    }

    private void finishLogin(Bundle bFacebookData) {
        String email = bFacebookData.getString(FACEBOOK_EMAIL_TAG);
        String usersId = bFacebookData.getString(FACEBOOK_ID_TAG);
        String name = bFacebookData.getString(FACEBOOK_NAME_TAG) + " "
                + bFacebookData.getString(FACEBOOK_LAST_NAME_TAG);
        PreferencesManager.setEmail(this, email);
        PreferencesManager.setUserId(this, usersId);
        PreferencesManager.setName(this, name);
        PreferencesManager.setUserPhoto(this, bFacebookData.getString(FACEBOOK_PIC_TAG));

        sendLogin(email, usersId, name);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Profile.getCurrentProfile() != null)
            startNewActivity();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void onStop() {
        super.onStop();
        //Facebook login
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void startNewActivity( ) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //nie potrzeba nic dodawac tutaj
    }

    private Bundle getFacebookData(JSONObject object) throws JSONException {

        Bundle bundle = new Bundle();
        String id = object.getString("id");

        try {
            URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
            bundle.putString(FACEBOOK_PIC_TAG, profile_pic.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        bundle.putString(FACEBOOK_ID_TAG, id);
        if (object.has(FACEBOOK_NAME_TAG))
            bundle.putString(FACEBOOK_NAME_TAG, object.getString(FACEBOOK_NAME_TAG));
        if (object.has(FACEBOOK_LAST_NAME_TAG))
            bundle.putString(FACEBOOK_LAST_NAME_TAG, object.getString(FACEBOOK_LAST_NAME_TAG));
        if (object.has(FACEBOOK_EMAIL_TAG))
            bundle.putString(FACEBOOK_EMAIL_TAG, object.getString(FACEBOOK_EMAIL_TAG));

        return bundle;
    }

    private void sendLogin(String email, String id, String name) {
        String apiKey = getString(R.string.server_api_key);
        LoginRequest request = new LoginRequest(email, id, name);
        dataRequestManager.login(apiKey, request).subscribe(new MaybeObserver<LoginResponse.User>() {
            @Override
            public void onSubscribe(Disposable d) {
                //TODO aby usuwac jak jest w tle
            }

            @Override
            public void onSuccess(LoginResponse.User value) {
                PreferencesManager.setOurId(LoginActivity.this, value.getId());
                PreferencesManager.setNewsletter(LoginActivity.this, value.isNewsletter());
                startNewActivity();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Log.d("JESTEM", ((HttpException)e).response().raw().request().url().toString());
            }

            @Override
            public void onComplete() {
                progressDialog.dismiss();
            }
        });
    }
}
