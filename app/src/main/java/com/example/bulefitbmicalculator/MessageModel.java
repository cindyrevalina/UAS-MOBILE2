package com.example.bulefitbmicalculator;

public class MessageModel {
    private String text;
    private boolean isUser; // true jika user, false jika AI

    public MessageModel(String text, boolean isUser) {
        this.text = text;
        this.isUser = isUser;
    }

    public String getText() {
        return text;
    }

    public boolean isUser() {
        return isUser;
    }
}