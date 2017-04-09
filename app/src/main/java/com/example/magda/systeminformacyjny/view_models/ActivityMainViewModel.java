package com.example.magda.systeminformacyjny.view_models;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import com.example.magda.systeminformacyjny.activities.LoginActivity;
import com.example.magda.systeminformacyjny.activities.MainActivity;
import com.example.magda.systeminformacyjny.fragments.AboutUsFragment;
import com.example.magda.systeminformacyjny.fragments.MainPageFragment;
import com.example.magda.systeminformacyjny.fragments.NearPlacesFragment;
import com.example.magda.systeminformacyjny.fragments.SettingsPageFragment;
import com.example.magda.systeminformacyjny.utils.DrawerCreator;
import com.facebook.login.LoginManager;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

/**
 * Created by piotrek on 05.04.17.
 */

public class ActivityMainViewModel implements Drawer.OnDrawerItemClickListener{

    private MainActivity viewCallback;
    private long selectedDrawerId;

    public ActivityMainViewModel(MainActivity viewCallback) {
        this.viewCallback = viewCallback;
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        selectedDrawerId = drawerItem.getIdentifier();
        drawerSelectedAction(selectedDrawerId);
        return false;
    }

    private void drawerSelectedAction(long drawerId) {
        if(drawerId == DrawerCreator.HOME_PAGE) {
            viewCallback.setToolbarTitle(DrawerCreator.HOME_TITLE);
            viewCallback.replaceFragment(createFragment(drawerId));
        }else if(drawerId == DrawerCreator.CREATE_ROUTE_PAGE) {

        }else if(drawerId == DrawerCreator.PLACES_BASE_PAGE) {

        }else if(drawerId == DrawerCreator.NEAR_PLACES_PAGE) {
            viewCallback.setToolbarTitle(DrawerCreator.NEAR_PLACES_TITLE);
            viewCallback.replaceFragment(createFragment(drawerId));
        }else if(drawerId == DrawerCreator.VISITED_PLACES_PAGE) {

        }else if(drawerId == DrawerCreator.VISITED_ROUTES_PAGE) {

        }else if(drawerId == DrawerCreator.SETTINGS_PAGE) {
            viewCallback.setToolbarTitle(DrawerCreator.SETTINGS_TITLE);
            viewCallback.replaceFragment(createFragment(drawerId));
        }else if(drawerId == DrawerCreator.ABOUT_US_PAGE) {
            viewCallback.setToolbarTitle(DrawerCreator.ABOUT_US_TITLE);
            viewCallback.replaceFragment(createFragment(drawerId));
        }else {
            LoginManager.getInstance().logOut();
            Intent intent = new Intent(viewCallback, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            viewCallback.startActivity(intent);
        }
    }

    public Fragment createFragment(long drawerId) {
        if(drawerId == DrawerCreator.HOME_PAGE) {
            return MainPageFragment.getInstance();
        }else if(drawerId == DrawerCreator.CREATE_ROUTE_PAGE) {

        }else if(drawerId == DrawerCreator.PLACES_BASE_PAGE) {

        }else if(drawerId == DrawerCreator.NEAR_PLACES_PAGE) {
            return NearPlacesFragment.getInstance();
        }else if(drawerId == DrawerCreator.VISITED_PLACES_PAGE) {

        }else if(drawerId == DrawerCreator.VISITED_ROUTES_PAGE) {

        }else if(drawerId == DrawerCreator.SETTINGS_PAGE) {
            return SettingsPageFragment.getInstance();
        }else if(drawerId == DrawerCreator.ABOUT_US_PAGE) {
            return AboutUsFragment.getInstance();
        }
        return null;
    }

    public long getSelectedDrawerId() {
        return selectedDrawerId;
    }

    public void setSelectedDrawerId(long selectedDrawerId) {
        this.selectedDrawerId = selectedDrawerId;
    }
}
