package com.hw.aggregate.event.model;

public class Message {
    private String message;

    public Message(String msg) {
        this.setMessage(msg);
    }

    public String getMessage() {
        return message;
    }

    public Message setMessage(String message) {
        this.message = message;
        return this;
    }
}
