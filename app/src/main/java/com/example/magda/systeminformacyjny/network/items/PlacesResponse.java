package com.example.magda.systeminformacyjny.network.items;

import com.example.magda.systeminformacyjny.models.Place;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by piotrek on 21.04.17.
 */

public class PlacesResponse {

    @SerializedName("places")
    @Expose
    private List<Place> places;


    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }
}
