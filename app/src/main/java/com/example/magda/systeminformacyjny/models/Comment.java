package com.example.magda.systeminformacyjny.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by piotrek on 02.05.17.
 */

public class Comment implements Serializable{

    @SerializedName("ID")
    @Expose
    private Long id;

    @SerializedName("CONTENT")
    @Expose
    private String content;

    @SerializedName("SCORE")
    @Expose
    private Float score;

    @SerializedName("TYPE")
    @Expose
    private String type;

    @SerializedName("NAMESPACES_ID")
    @Expose
    private Long namespaceId;

    @SerializedName("PLACES_ID")
    @Expose
    private Long placesId;

    @SerializedName("FB_USERS_ID")
    @Expose
    private Long fbUserId;


    public Comment(Long id){
        this.id = id;
    }

    public Comment(Long id, String content, Float score, String type, Long namespaceId, Long placesId,
                   Long fbUserId) {
        this.id = id;
        this.content = content;
        this.score = score;
        this.type = type;
        this.namespaceId = namespaceId;
        this.placesId = placesId;
        this.fbUserId = fbUserId;
    }

    public Comment(Long id, String content, Float score) {
        this.id = id;
        this.content = content;
        this.score = score;
        this.type = null;
        this.namespaceId = null;
        this.placesId = null;
        this.fbUserId = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getNamespaceId() {
        return namespaceId;
    }

    public void setNamespaceId(Long namespaceId) {
        this.namespaceId = namespaceId;
    }

    public Long getPlacesId() {
        return placesId;
    }

    public void setPlacesId(Long placesId) {
        this.placesId = placesId;
    }

    public Long getFbUserId() {
        return fbUserId;
    }

    public void setFbUserId(Long fbUserId) {
        this.fbUserId = fbUserId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Comment))
            return false;
        Comment other = (Comment) obj;
        return id == null ? other.id == null : id.equals(other.id);
    }

    @Override
    public int hashCode() {
        int result = 117;
        result = 37 * result + id.hashCode();
        return result;
    }
}
