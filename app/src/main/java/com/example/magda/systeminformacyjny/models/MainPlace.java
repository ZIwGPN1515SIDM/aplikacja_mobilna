package com.example.magda.systeminformacyjny.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by piotrek on 10.04.17.
 */

public class MainPlace implements Serializable{

    @SerializedName("ID")
    @Expose
    private Long id;

    @SerializedName("DESCRIPTION")
    @Expose
    private String description;

    @SerializedName("ADVERT")
    @Expose
    private Object advert;

    @SerializedName("EVENT_CONTENT")
    @Expose
    private String eventContent;

    @SerializedName("ADDED_ON")
    @Expose
    private String addedOn;

    @SerializedName("SUM_SCORE")
    @Expose
    private Float sumScore;

    @SerializedName("COMMENTS_COUNT")
    @Expose
    private Long commentsCount;

    @SerializedName("GOOGLE_PLACE_ID")
    @Expose
    private String googlePlaceId;

    @SerializedName("INSTANCE")
    @Expose
    private String namespace;

    @SerializedName("LATITUDE")
    @Expose
    private Double latitude;

    @SerializedName("LONGITUDE")
    @Expose
    private Double longitude;

    @SerializedName("NAME")
    @Expose
    private String name;

    public MainPlace(Long id, String description, Object advert, String eventContent, String addedOn,
                     Float sumScore, Long commentsCount, String googlePlaceId, String namespace,
                     Double latitude, Double longitude, String name) {
        this.id = id;
        this.description = description;
        this.advert = advert;
        this.eventContent = eventContent;
        this.addedOn = addedOn;
        this.sumScore = sumScore;
        this.commentsCount = commentsCount;
        this.googlePlaceId = googlePlaceId;
        this.namespace = namespace;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public MainPlace(Long id) {
        this.id = id;
    }

    public MainPlace(String namespace) {
        this.namespace = namespace;
    }

    public MainPlace(Long id, String namespace) {
        this.namespace = namespace;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getAdvert() {
        return advert;
    }

    public void setAdvert(Object advert) {
        this.advert = advert;
    }

    public String getEventContent() {
        return eventContent;
    }

    public void setEventContent(String eventContent) {
        this.eventContent = eventContent;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }

    public Float getSumScore() {
        return sumScore;
    }

    public void setSumScore(Float sumScore) {
        this.sumScore = sumScore;
    }

    public Long getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(Long commentsCount) {
        this.commentsCount = commentsCount;
    }

    public String getGooglePlaceId() {
        return googlePlaceId;
    }

    public void setGooglePlaceId(String googlePlaceId) {
        this.googlePlaceId = googlePlaceId;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof MainPlace))
            return false;
        MainPlace other = (MainPlace) obj;
        return (id == null || namespace == null) ? (other.id == null || other.namespace == null)
                : (id.equals(other.id) || namespace.equals(other.namespace));
    }

    @Override
    public int hashCode() {
        int result = 117;
        result = 37 * result + id.hashCode() + namespace.hashCode();
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
