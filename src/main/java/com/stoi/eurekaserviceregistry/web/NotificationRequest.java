package com.stoi.eurekaserviceregistry.web;

public class NotificationRequest {
    private String message;
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "NotificationRequest{" +
                "message='" + message + '\'' +
                '}';
    }
}
