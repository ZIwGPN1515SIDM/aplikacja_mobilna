package com.example.magda.systeminformacyjny.view_models;

import java.io.Serializable;

/**
 * Created by piotrek on 23.02.17.
 */

public class BaseDialogModel implements Serializable{

    private int imageId;
    private String title;
    private String message;
    private String button;

    public BaseDialogModel() {

    }

    public BaseDialogModel(int imageId, String title, String message, String button) {
        this.imageId = imageId;
        this.title = title;
        this.message = message;
        this.button = button;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }
}
