package com.bharath.watchSpace.Entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Users
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer userId;
    String username;
    String email;
    String password;
    String role;

    @OneToMany(mappedBy = "users")
    List<WatchSpaceParticipant> participants;

    @OneToMany(mappedBy = "user")
    List<ChatMessage> messages;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<WatchSpaceParticipant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<WatchSpaceParticipant> participants) {
        this.participants = participants;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }

    public Users(Integer userId, String username, String email, String password, String role, List<ChatMessage> messages, List<WatchSpaceParticipant> participants) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.messages = messages;
        this.participants = participants;
    }

    public Users() {
    }

}
