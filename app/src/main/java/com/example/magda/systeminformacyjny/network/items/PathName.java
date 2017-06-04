package com.example.magda.systeminformacyjny.network.items;

import com.example.magda.systeminformacyjny.models.Event;
import com.google.gson.annotations.SerializedName;

/**
 * Created by piotrek on 01.06.17.
 */

public class PathName {

    @SerializedName("id")
    private Long id;

    @SerializedName("name")
    private String name;


    public PathName(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public PathName(Long id) {
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


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof PathName))
            return false;
        PathName other = (PathName) obj;
        return id == null ? other.id == null : id.equals(other.id);
    }

    @Override
    public int hashCode() {
        int result = 117;
        result = 37 * result + id.hashCode();
        return result;
    }
}
