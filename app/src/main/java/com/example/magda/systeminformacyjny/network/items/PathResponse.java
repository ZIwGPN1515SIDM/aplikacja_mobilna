package com.example.magda.systeminformacyjny.network.items;

import com.google.gson.annotations.SerializedName;

/**
 * Created by piotrek on 01.06.17.
 */

public class PathResponse {

    @SerializedName("ID")
    private Long id;

    @SerializedName("NAME")
    private String name;

    @SerializedName("CREATED_ON")
    private String createdOn;

    public PathResponse(Long id, String name, String createdOn) {
        this.id = id;
        this.name = name;
        this.createdOn = createdOn;
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

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }
}
