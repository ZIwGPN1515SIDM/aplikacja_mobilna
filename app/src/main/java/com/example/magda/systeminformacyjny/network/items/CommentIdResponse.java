package com.example.magda.systeminformacyjny.network.items;

import com.example.magda.systeminformacyjny.network.DefaultIdWrapper;
import com.google.gson.annotations.SerializedName;

/**
 * Created by piotrek on 11.06.17.
 */

public class CommentIdResponse {

    @SerializedName("comment")
    private DefaultIdWrapper id;

    public DefaultIdWrapper getId() {
        return id;
    }

    public void setId(DefaultIdWrapper id) {
        this.id = id;
    }
}
