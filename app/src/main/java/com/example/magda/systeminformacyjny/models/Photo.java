package com.example.magda.systeminformacyjny.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by piotrek on 19.04.17.
 */

public class Photo implements Serializable{

    @SerializedName("ID")
    @Expose
    private Long id;

    @SerializedName("URL")
    @Expose
    private String URL;

    @SerializedName(value = "PLACE_ID", alternate = "NAMESPACE_ID")
    @Expose
    private Long placeId;

    public Photo(Long id, String URL, Long placeId) {
        this.id = id;
        this.URL = URL;
        this.placeId = placeId;
    }

    public Photo(String URL, Long placeId) {
        this.id = -1L;
        this.URL = URL;
        this.placeId = placeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Photo))
            return false;
        Photo other = (Photo) obj;
        return id == null ? other.id == null : id.equals(other.id);
    }

    @Override
    public int hashCode() {
        int result = 117;
        result = 37 * result + id.hashCode();
        return result;
    }
}
