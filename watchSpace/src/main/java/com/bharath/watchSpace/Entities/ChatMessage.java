package com.bharath.watchSpace.Entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
public class ChatMessage
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer chatMessageId;
    String message;
    LocalDateTime messageTime;

    @ManyToOne
    @JoinColumn(name = "userId")
    Users user;

    @ManyToOne
    @JoinColumn(name = "watchspaceId")
    WatchSpace watchSpace;

    public Integer getChatMessageId() {
        return chatMessageId;
    }

    public void setChatMessageId(Integer chatMessageId) {
        this.chatMessageId = chatMessageId;
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

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public WatchSpace getWatchSpace() {
        return watchSpace;
    }

    public void setWatchSpace(WatchSpace watchSpace) {
        this.watchSpace = watchSpace;
    }
}
