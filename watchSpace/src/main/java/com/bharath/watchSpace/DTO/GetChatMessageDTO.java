package com.bharath.watchSpace.DTO;

import java.time.LocalDateTime;

public class GetChatMessageDTO {

    private String username;
    private String message;
    private LocalDateTime messageTime;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(LocalDateTime messageTime) {
        this.messageTime = messageTime;
    }
}
