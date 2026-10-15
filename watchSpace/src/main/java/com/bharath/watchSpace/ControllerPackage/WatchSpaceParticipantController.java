package com.bharath.watchSpace.ControllerPackage;

import com.bharath.watchSpace.DTO.GetParticipantsDTO;
import com.bharath.watchSpace.DTO.JoinWatchSpaceDTO;
import com.bharath.watchSpace.ServicePackage.WatchSpaceParticipantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@Tag(
        name = "WatchSpace Participants",
        description = "APIs for joining, leaving and viewing WatchSpace participants")
@SecurityRequirement(name = "bearerAuth")
public class WatchSpaceParticipantController
{
    @Autowired
    WatchSpaceParticipantService watchSpaceParticipantService;


    @Operation(
            summary = "Join WatchSpace",
            description = "Allows a PLAYER to join an ACTIVE WatchSpace using its invite code")
    @PostMapping("/player/watchSpaces/join/{inviteCode}")
    public JoinWatchSpaceDTO joinWatchSpace(@PathVariable String inviteCode)
    {
        return watchSpaceParticipantService.joinWatchSpace(inviteCode);
    }


    @Operation(
            summary = "Get Participants",
            description = "Returns the players who joined the specified WatchSpace")
    @GetMapping("host/watchSpaces/{id}/participants")
    public List<GetParticipantsDTO> getParticipants(@PathVariable Integer id)
    {
        return watchSpaceParticipantService.getParticipants(id);
    }


    @Operation(
            summary = "Leave WatchSpace",
            description = "Allows a PLAYER to leave a WatchSpace")
    @PostMapping("/player/watchSpaces/{id}/leave")
    public String leaveWatchSpace()
    {
        watchSpaceParticipantService.leaveWatchSpace();
        return "Successfully Left";
    }


}
