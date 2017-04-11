package com.example.magda.systeminformacyjny.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.magda.systeminformacyjny.fragments.EventsPlaceFragment;
import com.example.magda.systeminformacyjny.fragments.InfoPlaceFragment;
import com.example.magda.systeminformacyjny.fragments.NearPlacesFragment;
import com.example.magda.systeminformacyjny.fragments.RatingPlaceFragment;

import java.util.HashMap;

/**
 * Created by piotrek on 09.04.17.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter{

    private int numberOfPages;
    private HashMap<Integer, Fragment> fragments;

    public static final int FRAGMENT_INFO_POSITION = 0;
    public static final int FRAGMENT_RATING_POSITION= 1;
    public static final int FRAGMENT_EVENTS_POSITION = 2;

    public ViewPagerAdapter(FragmentManager fm, int numberOfPages) {
        super(fm);
        this.numberOfPages = numberOfPages;
        this.fragments = new HashMap<>();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch(position) {
            case 0:
                fragment = InfoPlaceFragment.getInstance();
                break;
            case  1:
                fragment = RatingPlaceFragment.getInstance();
                break;
            case 2:
                fragment = EventsPlaceFragment.getInstance();
                break;
            default:
                throw new IllegalArgumentException("Too much fragments inside viewPager for top posts");
        }
        fragments.put(position, fragment);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position){
        switch(position){
            case 0:
                return "Info";
            case 1:
                return "Rating";
            case 2:
                return "Events";
            default:
                return "?";
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container,
                position);
        //dodaje fragment do hashmapy
        fragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        //usuwa fragment
        fragments.remove(position);
    }

    public Fragment getFragment(int pos) {
        return fragments.get(pos);
    }

    @Override
    public int getCount() {
        return numberOfPages;
    }
}
