package com.example.magda.systeminformacyjny.network;

/**
 * Created by piotrek on 11.04.17.
 */

public class ErrorResponse {

    public static final int DOWNLOAD_ERROR = 0;
    public static final int SEND_NEWSLETTER_ERROR = 1;
    public static final int SEND_OPINION_ERROR = 2;
    public static final int SEND_PATH_ERROR = 3;


    private int errorType;

    public ErrorResponse(int errorType) {
        this.errorType = errorType;
    }

    public int getErrorType() {
        return errorType;
    }

    public void setErrorType(int errorType) {
        this.errorType = errorType;
    }
}
