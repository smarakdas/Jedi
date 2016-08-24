package com.example.android.starwars.ModelClass;

/**
 * Created by sanu on 24-Aug-16.
 */
public class MessageModel {
    private String message = null;
    private Boolean isSender = null;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSender() {
        return isSender;
    }

    public void setSender(Boolean sender) {
        isSender = sender;
    }
}
