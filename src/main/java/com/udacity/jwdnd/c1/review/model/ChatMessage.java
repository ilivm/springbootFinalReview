package com.udacity.jwdnd.c1.review.model;

public class ChatMessage {
    private Integer messageId;

    private String userName;

    private String messageText;

    public ChatMessage(Integer messageId, String userName, String messageText) {
        this.messageId = messageId;
        this.userName = userName;
        this.messageText = messageText;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    @Override
    public String toString() {
        return userName + ": " + messageText;
    }
}
