package com.example.magda.systeminformacyjny.network.items;

import com.example.magda.systeminformacyjny.models.Comment;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by piotrek on 02.05.17.
 */

public class CommentResponse {

    @SerializedName("comments")
    @Expose
    private List<Comment> comments;

    public CommentResponse(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
