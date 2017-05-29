package com.example.magda.systeminformacyjny.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by piotrek on 29.05.17.
 */

public class DefaultIdWrapper {

    @SerializedName("ID")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
