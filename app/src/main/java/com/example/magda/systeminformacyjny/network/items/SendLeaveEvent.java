package com.example.magda.systeminformacyjny.network.items;

import com.google.gson.annotations.SerializedName;

import retrofit2.http.Query;

/**
 * Created by piotrek on 03.06.17.
 */

public class SendLeaveEvent {


    @SerializedName("ID")
    private Long id;

    @SerializedName("LEAVE_DATE")
    private String leaveDate;

    public SendLeaveEvent(Long id, String leaveDate) {
        this.id = id;
        this.leaveDate = leaveDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(String leaveDate) {
        this.leaveDate = leaveDate;
    }
}
