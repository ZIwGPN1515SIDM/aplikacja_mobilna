package com.example.magda.systeminformacyjny.models;

import java.io.Serializable;

/**
 * Created by piotrek on 23.04.17.
 */

public interface IPlaceItem extends Serializable{

    Float getDistance();
    String getDescription();
    String getCategoryName();
    String getName();
    float getRating();
    String getPhoto();
    Long getId();
    Long getCommentsCount();
}
