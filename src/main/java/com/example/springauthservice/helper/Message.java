package com.example.springauthservice.helper;

import com.example.springauthservice.helper.enums.MessageType;

public class Message {

    private String message;
    private MessageType messageType;

    public Message() {

    }

    public Message(String message, MessageType messageType) {
        this.message = message;
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageType() {
        return messageType.name().toLowerCase();
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

}
