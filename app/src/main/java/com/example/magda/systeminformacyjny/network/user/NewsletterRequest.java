package com.example.magda.systeminformacyjny.network.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Wojciech on 17.04.2017.
 */

public class NewsletterRequest {

    @SerializedName("ID")
    @Expose
    private Long id;

    @SerializedName("NEWSLETTER")
    @Expose
    private boolean flag;


    public NewsletterRequest(Long id, boolean flag) {
        this.id = id;
        this.flag = flag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
