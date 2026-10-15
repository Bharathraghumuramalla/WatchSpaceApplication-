package com.bharath.watchSpace.Entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class WatchSpaceParticipant
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer watchSpaceParticipantId;
    LocalDateTime joinedTime;

    @ManyToOne()
    @JoinColumn(name = "userId")
    Users users;

    @ManyToOne()
    @JoinColumn(name = "watchspaceId")
    WatchSpace watchSpace;

    public Integer getWatchSpaceParticipantId() {
        return watchSpaceParticipantId;
    }

    public void setWatchSpaceParticipantId(Integer watchSpaceParticipantId) {
        this.watchSpaceParticipantId = watchSpaceParticipantId;
    }

    public LocalDateTime getJoinedTime() {
        return joinedTime;
    }

    public void setJoinedTime(LocalDateTime joinedTime) {
        this.joinedTime = joinedTime;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public WatchSpace getWatchSpace() {
        return watchSpace;
    }

    public void setWatchSpace(WatchSpace watchSpace) {
        this.watchSpace = watchSpace;
    }

    public WatchSpaceParticipant(Integer watchSpaceParticipantId, LocalDateTime joinedTime, Users users, WatchSpace watchSpace) {
        this.watchSpaceParticipantId = watchSpaceParticipantId;
        this.joinedTime = joinedTime;
        this.users = users;
        this.watchSpace = watchSpace;
    }

    public WatchSpaceParticipant() {
    }
}
