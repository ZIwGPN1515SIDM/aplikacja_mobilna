package com.example.magda.systeminformacyjny.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by piotrek on 05.04.17.
 */

public class FacebookUser implements Parcelable{

    private String name;
    private String lastName;
    private String imageProfileUrl;
    private String userId;


    public FacebookUser(String name, String lastName, String imageProfileUrl, String userId) {
        this.name = name;
        this.lastName = lastName;
        this.imageProfileUrl = imageProfileUrl;
        this.userId = userId;
    }

    protected FacebookUser(Parcel in) {
        name = in.readString();
        lastName = in.readString();
        imageProfileUrl = in.readString();
        userId = in.readString();
    }

    public static final Creator<FacebookUser> CREATOR = new Creator<FacebookUser>() {
        @Override
        public FacebookUser createFromParcel(Parcel in) {
            return new FacebookUser(in);
        }

        @Override
        public FacebookUser[] newArray(int size) {
            return new FacebookUser[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImageProfileUrl() {
        return imageProfileUrl;
    }

    public void setImageProfileUrl(String imageProfileUrl) {
        this.imageProfileUrl = imageProfileUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(lastName);
        dest.writeString(imageProfileUrl);
        dest.writeString(userId);
    }
}
