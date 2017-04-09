package com.example.magda.systeminformacyjny.utils;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.multidex.MultiDex;
import android.widget.ImageView;

import com.kontakt.sdk.android.common.KontaktSDK;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;

/**
 * Created by piotrek on 05.04.17.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        KontaktSDK.initialize(this);
        //MultiDex.install(this);

        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
            }
            @Override
            public void cancel(ImageView imageView) {
                Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }
        });

    }


}
