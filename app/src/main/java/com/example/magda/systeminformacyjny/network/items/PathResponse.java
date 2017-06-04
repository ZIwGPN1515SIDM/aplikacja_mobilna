package com.example.magda.systeminformacyjny.network.items;

import com.google.gson.annotations.SerializedName;

/**
 * Created by piotrek on 01.06.17.
 */

public class PathResponse {

    @SerializedName(value = "ID", alternate = "id")
    private Long id;

    @SerializedName(value = "NAME", alternate = "name")
    private String name;

    @SerializedName("CREATED_ON")
    private String createdOn;

    public PathResponse(Long id, String name, String createdOn) {
        this.id = id;
        this.name = name;
        this.createdOn = createdOn;
    }

    public PathResponse(Long id) {
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

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof PathResponse))
            return false;
        PathResponse other = (PathResponse) obj;
        return id == null ? other.id == null : id.equals(other.id);
    }

    @Override
    public int hashCode() {
        int result = 117;
        result = 37 * result + id.hashCode();
        return result;
    }
}
