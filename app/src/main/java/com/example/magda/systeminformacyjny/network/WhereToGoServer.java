package com.example.magda.systeminformacyjny.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by piotrek on 11.04.17.
 */

public class WhereToGoServer {

    private static WhereToGoService haterickServerApi;
    private Retrofit retrofit;
    private static final String BASE_URL_SERVER = "http://graymanix.ovh/api/v2/";

    private WhereToGoServer() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL_SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public static synchronized WhereToGoServer getInstance() {
        return new WhereToGoServer();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

}
