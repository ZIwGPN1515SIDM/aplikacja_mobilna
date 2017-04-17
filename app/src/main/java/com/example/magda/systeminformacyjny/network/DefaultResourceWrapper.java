package com.example.magda.systeminformacyjny.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wojciech on 17.04.2017.
 */

public class DefaultResourceWrapper <T> {

    @SerializedName("resource")
    @Expose
    private List<T> resource;

    public DefaultResourceWrapper(List<T> resource) {
        this.resource = resource;
    }

    public DefaultResourceWrapper(T item) {
        this.resource = new ArrayList<T>();
        this.resource.add(item);
    }

    public List<T> getResource() {
        return resource;
    }

    public void setResource(List<T> resource) {
        this.resource = resource;
    }
}
