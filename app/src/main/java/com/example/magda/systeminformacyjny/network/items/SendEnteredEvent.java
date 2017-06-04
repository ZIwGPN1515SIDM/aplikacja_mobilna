package com.example.magda.systeminformacyjny.network.items;

import com.google.gson.annotations.SerializedName;

/**
 * Created by piotrek on 03.06.17.
 */

public class SendEnteredEvent {

    @SerializedName("FB_USER_ID")
    private Long userId;

    @SerializedName("NAMESPACE_INSTANCE")
    private String namespace;

    @SerializedName("PLACE_INSTANCE")
    private String instance;


    public SendEnteredEvent(Long userId, String namespace, String instance) {
        this.userId = userId;
        this.namespace = namespace;
        this.instance = instance;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }
}
