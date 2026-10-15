package com.bharath.watchSpace.Entities;

import com.bharath.watchSpace.ENUMS.WatchSpaceStatus;
import jakarta.persistence.*;
import java.util.List;
import java.util.Random;

@Entity
public class WatchSpace
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer watchspaceId;

    @Column(unique = true, nullable = false)
    String inviteCode;

    @Enumerated(EnumType.STRING)
    WatchSpaceStatus status;

    @OneToMany(mappedBy = "watchSpace")
    List<ChatMessage> chatMessageList;

    @OneToMany(mappedBy = "watchSpace")
    List<WatchSpaceParticipant> watchSpaceParticipants;

    @ManyToOne
    @JoinColumn(name = "titleId")
    Title title;

    @ManyToOne
    @JoinColumn(name = "hostId")
    Users host;

    public static String createInviteCode()
    {
        String character = "0987654321ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer sb = new StringBuffer();
        Random random = new Random();

        for(int i=0; i<7; i++)
        {
            sb.append(character.charAt(random.nextInt(character.length())));
        }
        return sb.toString();
    }

    public Integer getWatchspaceId() {
        return watchspaceId;
    }

    public void setWatchspaceId(Integer watchspaceId) {
        this.watchspaceId = watchspaceId;
    }


    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public WatchSpaceStatus getStatus() {
        return status;
    }

    public void setStatus(WatchSpaceStatus status) {
        this.status = status;
    }

    public List<ChatMessage> getChatMessageList() {
        return chatMessageList;
    }

    public void setChatMessageList(List<ChatMessage> chatMessageList) {
        this.chatMessageList = chatMessageList;
    }

    public List<WatchSpaceParticipant> getWatchSpaceParticipants() {
        return watchSpaceParticipants;
    }

    public void setWatchSpaceParticipants(List<WatchSpaceParticipant> watchSpaceParticipants) {
        this.watchSpaceParticipants = watchSpaceParticipants;
    }

    public Users getHost() {
        return host;
    }

    public void setHost(Users host) {
        this.host = host;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public WatchSpace(Integer watchspaceId, String inviteCode, WatchSpaceStatus status, List<ChatMessage> chatMessageList, List<WatchSpaceParticipant> watchSpaceParticipants, Title title, Users host) {
        this.watchspaceId = watchspaceId;
        this.inviteCode = inviteCode;
        this.status = status;
        this.chatMessageList = chatMessageList;
        this.watchSpaceParticipants = watchSpaceParticipants;
        this.title = title;
        this.host = host;
    }

    public WatchSpace() {
    }
}
