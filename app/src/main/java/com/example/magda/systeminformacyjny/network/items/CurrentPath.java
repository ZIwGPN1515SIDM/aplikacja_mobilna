package com.example.magda.systeminformacyjny.network.items;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by piotrek on 01.06.17.
 */

public class CurrentPath {

    @SerializedName("FB_USER_ID")
    private Long userId;

    @SerializedName("NAME")
    private String name;

    @SerializedName("PATH")
    private String pathNames;

    public CurrentPath(Long userId, String name, String pathNames) {
        this.userId = userId;
        this.name = name;
        this.pathNames = pathNames;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPathNames() {
        return pathNames;
    }

    public void setPathNames(String pathNames) {
        this.pathNames = pathNames;
    }
}
