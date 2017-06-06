package com.example.magda.systeminformacyjny.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by piotrek on 06.06.17.
 */

public class UserInfo implements Serializable{

    @SerializedName("ID")
    private Long id;

    @SerializedName("EMAIL")
    private String email;

    @SerializedName("NAME")
    private String name;


    public UserInfo(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public UserInfo(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
