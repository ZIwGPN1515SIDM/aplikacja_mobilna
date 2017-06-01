package com.example.magda.systeminformacyjny.network;

/**
 * Created by piotrek on 11.04.17.
 */

public class SuccessResponse {


    public static final int DOWNLOAD_SUCCESS = 0;
    public static final int SEND_NEWSLETTER_SUCCESS = 1;
    public static final int SEND_OPINION_SUCCESS = 2;
    public static final int SEND_PATH_SUCCESS = 3;

    private int successType;

    public SuccessResponse(int successType) {
        this.successType = successType;
    }

    public int getSuccessType() {
        return successType;
    }

    public void setSuccessType(int successType) {
        this.successType = successType;
    }
}
