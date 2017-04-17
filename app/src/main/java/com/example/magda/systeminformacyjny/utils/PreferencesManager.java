package com.example.magda.systeminformacyjny.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import static com.example.magda.systeminformacyjny.utils.Constants.METER_KILOMETER;

/**
 * Created by piotrek on 06.04.17.
 */

public class PreferencesManager {


    public static final String PREF_KEY = "com.example.magda.systeminformacyjny";
    public static final String NEWSLETTER_PREF = "newsletter";
    public static final String MAP_MODE_PREF = "mapMode";
    public static final String ROUTE_COLOR_PREF = "routeColor";
    public static final String MEASURE_TYPE_PREF = "measureType";
    public static final String USER_ID_PREF = "userId";
    public static final String OUR_USER_ID_PREF = "ourUserId";
    public static final String EMAIL_PREF = "email";
    public static final String NAME_PREF = "name";
    public static final String USER_PHOTO_PREF = "userPhoto";

    public static boolean isNewsletter(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
        return preferences.getBoolean(NEWSLETTER_PREF, true);
    }

    public static int mapMode(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
        return preferences.getInt(MAP_MODE_PREF, 0);
    }

    public static int routeColor(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
        return preferences.getInt(ROUTE_COLOR_PREF, Color.RED);
    }

    public static int measureType(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
        return preferences.getInt(MEASURE_TYPE_PREF, METER_KILOMETER);
    }


    public static void setNewsletter(Context context, boolean flag) {
        context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE).edit().putBoolean(NEWSLETTER_PREF, flag)
                .apply();
    }

    public static void setMeasureType(Context context, int measure) {
        context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE).edit().putInt(MEASURE_TYPE_PREF, measure)
                .apply();
    }

    public static void setMapMode(Context context, int mapMode) {
        context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE).edit().putInt(MAP_MODE_PREF, mapMode)
                .apply();
    }

    public static void setRouteColor(Context context, int routeColor) {
        context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE).edit().putInt(ROUTE_COLOR_PREF, routeColor)
                .apply();
    }

    public static void setUserId(Context context, String userID) {
        context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE).edit().putString(USER_ID_PREF, userID)
                .apply();
    }

    public static String getUserId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
        return preferences.getString(USER_ID_PREF, "null");
    }

    public static Long getOurId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
        return preferences.getLong(OUR_USER_ID_PREF, -1L);
    }

    public static void setOurId(Context context, Long id) {
        context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE).edit().putLong(OUR_USER_ID_PREF, id)
                .apply();
    }

    public static void setEmail(Context context, String email) {
        context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE).edit().putString(EMAIL_PREF, email)
                .apply();
    }

    public static String getEmail(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
        return preferences.getString(EMAIL_PREF, "null");
    }

    public static String getName(Context context) {
        return context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE).getString(NAME_PREF, "null");
    }

    public static void setName(Context context, String name) {
        context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE).edit().putString(NAME_PREF,
                name).apply();
    }

    public static void setUserPhoto(Context context, String photoUrl) {
        context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE).edit().putString(USER_PHOTO_PREF,
                photoUrl).apply();
    }

    public static String getUserPhoto(Context context) {
        return context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE).getString(USER_PHOTO_PREF,
                "null");
    }

}
