package com.example.magda.systeminformacyjny.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.magda.systeminformacyjny.fragments.EventsPlaceFragment;
import com.example.magda.systeminformacyjny.fragments.InfoPlaceFragment;
import com.example.magda.systeminformacyjny.fragments.RatingPlaceFragment;
import com.example.magda.systeminformacyjny.fragments.SubLocationEventFragment;
import com.example.magda.systeminformacyjny.models.MainPlace;
import com.example.magda.systeminformacyjny.models.Place;

import java.util.HashMap;

/**
 * Created by piotrek on 23.04.17.
 */

public class SubPlaceViewPager extends FragmentPagerAdapter {
    private int numberOfPages;
    private HashMap<Integer, Fragment> fragments;
    private String date;
    private String description;
    private String name;
    private Place place;

    public static final int FRAGMENT_INFO_POSITION = 0;
    public static final int FRAGMENT_RATING_POSITION= 1;
    public static final int FRAGMENT_EVENTS_POSITION = 2;
    public static final String FRAGMENT_INFO_TITLE = "Informacja";
    public static final String FRAGMENT_RATING_TITLE = "Ocena";
    public static final String FRAGMENT_EVENTS_TITLE = "Wydarzenia";
    public static final String TOO_MUCH_FRAGMENTS_ERROR_MESSAGE = "Wydarzenia";

    public SubPlaceViewPager(FragmentManager fm, int numberOfPages,
                            Place place) {
        super(fm);
        this.numberOfPages = numberOfPages;
        this.fragments = new HashMap<>();
        this.name = place.getEventName();
        this.description = place.getEventContent();
        this.date = place.getEventEnd();
        this.place = place;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch(position) {
            case FRAGMENT_INFO_POSITION:
                fragment = InfoPlaceFragment.getInstance(place);
                break;
            case  FRAGMENT_RATING_POSITION:
                fragment = RatingPlaceFragment.getInstance(place);
                break;
            case FRAGMENT_EVENTS_POSITION:
                fragment = SubLocationEventFragment.getInstance(date, description, name);
                break;
            default:
                throw new IllegalArgumentException(TOO_MUCH_FRAGMENTS_ERROR_MESSAGE);
        }
        fragments.put(position, fragment);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position){
        switch(position){
            case 0:
                return FRAGMENT_INFO_TITLE;
            case 1:
                return FRAGMENT_RATING_TITLE;
            case 2:
                return FRAGMENT_EVENTS_TITLE;
            default:
                throw new IllegalArgumentException(TOO_MUCH_FRAGMENTS_ERROR_MESSAGE);
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
