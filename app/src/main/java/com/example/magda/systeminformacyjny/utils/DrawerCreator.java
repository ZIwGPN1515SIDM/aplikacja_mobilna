package com.example.magda.systeminformacyjny.utils;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;

import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.databinding.DrawerHeaderBinding;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

/**
 * Created by piotrek on 05.04.17.
 */

public class DrawerCreator {

    public static final long HOME_PAGE = 0;
    public static final long CREATE_ROUTE_PAGE = 1;
    public static final long PLACES_BASE_PAGE = 2;
    public static final long NEAR_PLACES_PAGE = 3;
    public static final long VISITED_PLACES_PAGE = 4;
    public static final long VISITED_ROUTES_PAGE = 5;
    public static final long SETTINGS_PAGE = 6;
    public static final long ABOUT_US_PAGE = 7;
    public static final long LOG_OUT_PAGE = 8;

    public static final String HOME_TITLE = "Strona głowna";
    public static final String CREATE_ROUTE_TITLE = "Tworzenie trasy";
    public static final String PLACES_BASE_TITLE = "Baza miejsc";
    public static final String NEAR_PLACES_TITLE = "Najbliższe miejsca";
    public static final String VISITED_PLACES_TITLE = "Odwiedzone miejsca";
    public static final String VISITED_ROUTES_TITILE = "Odwiedzone trasy";
    public static final String SETTINGS_TITLE = "Ustwienia";
    public static final String ABOUT_US_TITLE = "O nas";
    public static final String LOG_OUT_TITLE = "Wyloguj";


    public static Drawer createDrawer(AppCompatActivity activity, Toolbar toolbar,
                                      Drawer.OnDrawerItemClickListener listener) {
        PrimaryDrawerItem homeItem = new PrimaryDrawerItem().withName(HOME_TITLE)
                .withIdentifier(HOME_PAGE).withIcon(R.mipmap.drawer_main_grey)
                .withSelectedIcon(R.mipmap.drawer_main_blue);
        PrimaryDrawerItem createRouteItem = new PrimaryDrawerItem().withName(CREATE_ROUTE_TITLE)
                .withIdentifier(CREATE_ROUTE_PAGE).withIcon(R.mipmap.drawer_create_route_grey)
                .withSelectedIcon(R.mipmap.drawer_create_route_blue);
        PrimaryDrawerItem placesBaseItem = new PrimaryDrawerItem().withName(PLACES_BASE_TITLE)
                .withIdentifier(PLACES_BASE_PAGE).withIcon(R.mipmap.drawer_places_base_grey)
                .withSelectedIcon(R.mipmap.drawer_places_base_blue);
        PrimaryDrawerItem nearPlacesItem = new PrimaryDrawerItem().withName(NEAR_PLACES_TITLE)
                .withIdentifier(NEAR_PLACES_PAGE).withIcon(R.mipmap.drawer_nearby_places_grey)
                .withSelectedIcon(R.mipmap.drawer_nearby_places_blue);
        PrimaryDrawerItem visitedPlacesItem = new PrimaryDrawerItem().withName(VISITED_PLACES_TITLE)
                .withIdentifier(VISITED_PLACES_PAGE).withIcon(R.mipmap.drawer_visited_places_grey)
                .withSelectedIcon(R.mipmap.drawer_visited_places_blue);
        PrimaryDrawerItem visitedRoutesItem = new PrimaryDrawerItem().withName(VISITED_ROUTES_TITILE)
                .withIdentifier(VISITED_ROUTES_PAGE).withIcon(R.mipmap.drawer_visited_routes_grey)
                .withSelectedIcon(R.mipmap.drawer_visited_routes_blue);
        PrimaryDrawerItem settingsItem = new PrimaryDrawerItem().withName(SETTINGS_TITLE)
                .withIdentifier(SETTINGS_PAGE).withIcon(R.mipmap.drawer_settings_grey)
                .withSelectedIcon(R.mipmap.drawer_settings_blue);
        PrimaryDrawerItem aboutUsItem = new PrimaryDrawerItem().withName(ABOUT_US_TITLE)
                .withIdentifier(ABOUT_US_PAGE).withIcon(R.mipmap.drawer_about_us_grey)
                .withSelectedIcon(R.mipmap.drawer_about_us_blue);
        PrimaryDrawerItem logOutItem = new PrimaryDrawerItem().withName(LOG_OUT_TITLE)
                .withIdentifier(LOG_OUT_PAGE).withIcon(R.mipmap.drawer_logout_grey)
                .withSelectedIcon(R.mipmap.drawer_logout_blue);

        LayoutInflater inflater = LayoutInflater.from(activity);
        DrawerHeaderBinding binding = DataBindingUtil.inflate(inflater, R.layout.drawer_header, null,
                false);
        binding.setImageUrl(PreferencesManager.getUserPhoto(activity));
        binding.setUserName(PreferencesManager.getName(activity));
        Drawer drawer = new DrawerBuilder()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withHasStableIds(true)
                .withHeader(binding.getRoot())
                .addDrawerItems(homeItem, createRouteItem, placesBaseItem, nearPlacesItem, visitedPlacesItem,
                        visitedRoutesItem, new DividerDrawerItem(), settingsItem, aboutUsItem, new DividerDrawerItem(), logOutItem)
                .withOnDrawerItemClickListener(listener).build();

        return drawer;
    }



}
