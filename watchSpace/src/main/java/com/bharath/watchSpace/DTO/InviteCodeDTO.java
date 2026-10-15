package com.bharath.watchSpace.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public class InviteCodeDTO
{
    @NotBlank
    String InviteCode;
    @NotBlank
    String Message;

    public String getInviteCode() {
        return InviteCode;
    }

    public void setInviteCode(String inviteCode) {
        InviteCode = inviteCode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
