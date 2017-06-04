package com.example.magda.systeminformacyjny.network.items;

import com.example.magda.systeminformacyjny.models.Place;
import com.google.gson.annotations.SerializedName;

/**
 * Created by piotrek on 04.06.17.
 */

public class InstanceResponse {

    @SerializedName("place")
    private Place place;

    public InstanceResponse(Place place) {
        this.place = place;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}
