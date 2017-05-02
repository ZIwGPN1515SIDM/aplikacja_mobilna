package com.example.magda.systeminformacyjny.network.items;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by piotrek on 02.05.17.
 */

public class SendCommentRequest {

    @SerializedName("CONTENT")
    @Expose
    private String content;

    @SerializedName("SCORE")
    @Expose
    private Float score;

    @SerializedName("PLACES_ID")
    @Expose
    private Long placesId;

    @SerializedName("NAMESPACES_ID")
    @Expose
    private Long namespaceId;

    @SerializedName("TYPE")
    @Expose
    private String type;

    @SerializedName("FB_USERS_ID")
    @Expose
    private Long fbUsersId;


    public SendCommentRequest(String content, Float score, Long placesId, Long namespaceId,
                              String type, Long fbUsersId) {
        this.content = content;
        this.score = score;
        this.placesId = placesId;
        this.namespaceId = namespaceId;
        this.type = type;
        this.fbUsersId = fbUsersId;
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

    public Long getPlacesId() {
        return placesId;
    }

    public void setPlacesId(Long placesId) {
        this.placesId = placesId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getFbUsersId() {
        return fbUsersId;
    }

    public void setFbUsersId(Long fbUsersId) {
        this.fbUsersId = fbUsersId;
    }

    public Long getNamespaceId() {
        return namespaceId;
    }

    public void setNamespaceId(Long namespaceId) {
        this.namespaceId = namespaceId;
    }
}
