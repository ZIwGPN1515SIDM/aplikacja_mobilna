package com.example.magda.systeminformacyjny.network.items;

import com.example.magda.systeminformacyjny.models.Event;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Wojciech on 17.04.2017.
 */

public class EventResponse {

    @SerializedName("namespace")
    @Expose
    private List<Event> namespaceEvents;

    @SerializedName("places")
    @Expose
    private List<Event> placeEvents;

    public List<Event> getNamespaceEvents() {
        return namespaceEvents;
    }

    public void setNamespaceEvents(List<Event> namespaceEvents) {
        this.namespaceEvents = namespaceEvents;
    }

    public List<Event> getPlaceEvents() {
        return placeEvents;
    }

    public void setPlaceEvents(List<Event> placeEvents) {
        this.placeEvents = placeEvents;
    }

}
