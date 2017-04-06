package com.example.magda.systeminformacyjny.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

/**
 * Created by piotrek on 06.04.17.
 */

public class PreferencesManager {


    public static final String PREF_KEY = "com.example.magda.systeminformacyjny";
    public static final String NEWSLETTER_PREF = "newsletter";
    public static final String MAP_MODE_PREF = "mapMode";
    public static final String ROUTE_COLOR_PREF = "routeColor";
    public static final String MEASURE_TYPE_PREF = "measureType";
    public static final String USER_ID = "userId";

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
        return preferences.getInt(MEASURE_TYPE_PREF, 0);
    }


    public static void setNewsletter(Context context, boolean flag) {
        context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE).edit().putBoolean(NEWSLETTER_PREF, flag)
                .apply();
    }

    public static void setMapMode(Context context, int mapMode) {
        context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE).edit().putInt(NEWSLETTER_PREF, mapMode)
                .apply();
    }

    public static void setRouteColor(Context context, int routeColor) {
        context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE).edit().putInt(ROUTE_COLOR_PREF, routeColor)
                .apply();
    }

    public static void setUserId(Context context, String userID) {
        context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE).edit().putString(USER_ID, userID)
                .apply();
    }

    public static String getUserId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
        return preferences.getString(USER_ID, "null");
    }


}
