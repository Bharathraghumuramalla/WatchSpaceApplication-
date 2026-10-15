package com.bharath.watchSpace.DTO;

import com.bharath.watchSpace.ENUMS.WatchSpaceStatus;

public class JoinWatchSpaceDTO
{

    private Integer watchspaceId;
    private Integer titleId;
    private String inviteCode;
    private WatchSpaceStatus status;
    private Integer hostId;

    public Integer getWatchspaceId() {
        return watchspaceId;
    }

    public void setWatchspaceId(Integer watchspaceId) {
        this.watchspaceId = watchspaceId;
    }

    public Integer getTitleId() {
        return titleId;
    }

    public void setTitleId(Integer titleId) {
        this.titleId = titleId;
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

    public Integer getHostId() {
        return hostId;
    }

    public void setHostId(Integer hostId) {
        this.hostId = hostId;
    }
}
