package com.example.magda.systeminformacyjny.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Wojciech on 17.04.2017.
 */

public class Event {

    @SerializedName("ID")
    @Expose
    private Long id;

    @SerializedName("NAME")
    @Expose
    private String name;

    @SerializedName("EVENT_CONTENT")
    @Expose
    private String eventContent;

    @SerializedName("EVENT_END")
    @Expose
    private String eventEnd;

    @SerializedName("EVENT_NAME")
    @Expose
    private String eventName;

    public Event(Long id, String name, String eventContent, String eventEnd, String eventName) {
        this.id = id;
        this.name = name;
        this.eventContent = eventContent;
        this.eventEnd = eventEnd;
        this.eventName = eventName;
    }

    public Event(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEventContent() {
        return eventContent;
    }

    public void setEventContent(String eventContent) {
        this.eventContent = eventContent;
    }

    public String getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(String eventEnd) {
        this.eventEnd = eventEnd;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Event))
            return false;
        Event other = (Event) obj;
        return id == null ? other.id == null : id.equals(other.id);
    }

    @Override
    public int hashCode() {
        int result = 117;
        result = 37 * result + id.hashCode();
        return result;
    }


    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
