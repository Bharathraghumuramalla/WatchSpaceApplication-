package com.bharath.watchSpace.DTO;

import com.bharath.watchSpace.ENUMS.WatchSpaceStatus;
public class GetWatchSpaceDTO
{
    Integer watchspaceId;
    String inviteCode;
    WatchSpaceStatus status;
    Integer titleId;
    Integer hostId;

    public WatchSpaceStatus getStatus() {
        return status;
    }

    public void setStatus(WatchSpaceStatus status) {
        this.status = status;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

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

    public Integer getHostId() {
        return hostId;
    }

    public void setHostId(Integer hostId) {
        this.hostId = hostId;
    }
}
