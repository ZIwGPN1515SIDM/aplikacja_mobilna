package com.example.magda.systeminformacyjny.network.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Wojciech on 17.04.2017.
 */

public class LoginRequest {

    @SerializedName("EMAIL")
    @Expose
    private String email;

    @SerializedName("FB_ID")
    @Expose
    private String fbId;

    @SerializedName("NAME")
    @Expose
    private String name;

    public LoginRequest(String email, String fbId, String name) {
        this.email = email;
        this.fbId = fbId;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
