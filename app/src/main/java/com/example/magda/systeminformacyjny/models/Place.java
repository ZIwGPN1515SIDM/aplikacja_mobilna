package com.example.magda.systeminformacyjny.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.magda.systeminformacyjny.BR;
import com.example.magda.systeminformacyjny.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by piotrek on 19.04.17.
 */

public class Place extends BaseObservable implements IPlaceItem {

    @SerializedName("ID")
    @Expose
    private Long id;

    @SerializedName("DESCRIPTION")
    @Expose
    private String description;

    @SerializedName("ADVERT")
    @Expose
    private String advert;

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
    private String googleId;

    @SerializedName("INSTANCE")
    @Expose
    private String instance;

    @SerializedName("NAME")
    @Expose
    private String name;

    @SerializedName("NAMESPACE_ID")
    @Expose
    private Long namespaceId;

    @SerializedName("PLACES_PHOTOS")
    @Expose
    private List<Photo> photos;

    @SerializedName("EVENT_NAME")
    @Expose
    private String eventName;

    @SerializedName("EVENT_END")
    @Expose
    private String eventEnd;

    private Float distance;

    private String namespace;

    public Place(Long id, String description, String advert, String eventContent, String addedOn,
                 Float sumScore, Long commentsCount, String googleId, String instance, String name,
                 Long namespaceId, List<Photo> photos, String eventName, String eventEnd) {
        this.id = id;
        this.description = description;
        this.advert = advert;
        this.eventContent = eventContent;
        this.addedOn = addedOn;
        this.sumScore = sumScore;
        this.commentsCount = commentsCount;
        this.googleId = googleId;
        this.instance = instance;
        this.name = name;
        this.namespaceId = namespaceId;
        this.photos = photos;
        this.eventName = eventName;
        this.eventEnd = eventEnd;

    }

    public Place(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Bindable
    @Override
    public Float getDistance() {
        return distance;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String getCategoryName() { //TODO
        return null;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdvert() {
        return advert;
    }

    public void setAdvert(String advert) {
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

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getName() {
        return name;
    }

    @Override
    public float getRating() {
        return commentsCount != 0 ? sumScore / commentsCount : 0;
    }

    @Override
    public String getPhoto() {
        return photos != null ? photos.get(0).getURL() : "https://vignette3.wikia.nocookie.net/lego/images/a/ac/No-Image-Basic" +
                ".png/revision/latest?cb=20130819001030";
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNamespaceId() {
        return namespaceId;
    }

    public void setNamespaceId(Long namespaceId) {
        this.namespaceId = namespaceId;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Place))
            return false;
        Place other = (Place) obj;
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

    public String getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(String eventEnd) {
        this.eventEnd = eventEnd;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
        notifyPropertyChanged(BR.distance);
    }
}
