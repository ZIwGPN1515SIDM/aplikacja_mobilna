package com.example.magda.systeminformacyjny.network.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Wojciech on 17.04.2017.
 */

public class LoginResponse {

    @SerializedName("user")
    @Expose
    private List<User> userList;

    public LoginResponse(List<User> userList) {
        this.userList = userList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public static class User {

        @SerializedName("ID")
        @Expose
        private Long id;

        @SerializedName("NEWSLETTER")
        @Expose
        private boolean newsletter;

        public User(Long id, boolean newsletter) {
            this.id = id;
            this.newsletter = newsletter;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public boolean isNewsletter() {
            return newsletter;
        }

        public void setNewsletter(boolean newsletter) {
            this.newsletter = newsletter;
        }
    }

}
