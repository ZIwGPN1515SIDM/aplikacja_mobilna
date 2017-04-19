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
    private Event namespaceEvent;

    @SerializedName("places")
    @Expose
    private List<Event> placeEvents;

    public Event getNamespaceEvent() {
        return namespaceEvent;
    }

    public void setNamespaceEvent(Event namespaceEvent) {
        this.namespaceEvent = namespaceEvent;
    }

    public List<Event> getPlaceEvents() {
        return placeEvents;
    }

    public void setPlaceEvents(List<Event> placeEvents) {
        this.placeEvents = placeEvents;
    }

}
