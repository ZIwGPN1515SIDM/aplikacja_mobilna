package com.example.magda.systeminformacyjny.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by piotrek on 19.04.17.
 */

public class Place implements Serializable, IPlaceItem {

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
    private Long commentCount;

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

    @SerializedName("PLACES_PHOTO")
    @Expose
    private List<Photo> photos;


    public Place(Long id, String description, String advert, String eventContent, String addedOn,
                 Float sumScore, Long commentCount, String googleId, String instance, String name, Long namespaceId, List<Photo> photos) {
        this.id = id;
        this.description = description;
        this.advert = advert;
        this.eventContent = eventContent;
        this.addedOn = addedOn;
        this.sumScore = sumScore;
        this.commentCount = commentCount;
        this.googleId = googleId;
        this.instance = instance;
        this.name = name;
        this.namespaceId = namespaceId;
        this.photos = photos;
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

    @Override
    public String getDistance() { //TODO
        return null;
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

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
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
        return commentCount != 0 ? sumScore / commentCount : 0;
    }

    @Override
    public String getPhoto() {
        return photos != null? photos.get(0).getURL(): "https://vignette3.wikia.nocookie.net/lego/images/a/ac/No-Image-Basic" +
                ".png/revision/latest?cb=20130819001030"; //TODO NA SZTYWNO NA RAZIE
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

}
