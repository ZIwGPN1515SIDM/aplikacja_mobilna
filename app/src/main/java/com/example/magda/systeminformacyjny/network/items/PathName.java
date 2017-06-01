package com.example.magda.systeminformacyjny.network.items;

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
}
