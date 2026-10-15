package com.bharath.watchSpace.DTO;

import jakarta.validation.constraints.NotNull;

public class PostWatchSpaceDTO
{
    @NotNull
    Integer titleId;

    public Integer getTitleId() {
        return titleId;
    }

    public void setTitleId(Integer titleId) {
        this.titleId = titleId;
    }
}
