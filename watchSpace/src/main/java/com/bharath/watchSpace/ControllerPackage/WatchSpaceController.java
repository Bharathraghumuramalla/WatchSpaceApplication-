package com.bharath.watchSpace.ControllerPackage;

import com.bharath.watchSpace.DTO.GetWatchSpaceDTO;
import com.bharath.watchSpace.DTO.InviteCodeDTO;
import com.bharath.watchSpace.DTO.PostWatchSpaceDTO;
import com.bharath.watchSpace.ServicePackage.WatchSpacesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Tag(
        name = "WatchSpaces",
        description = "HOST WatchSpace management APIs")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/host")
public class WatchSpaceController
{
    @Autowired
    WatchSpacesService watchSpacesService;



    @Operation(
            summary = "Create WatchSpace",
            description = "Creates a new WatchSpace for a selected title and generates an invite code")
    @PostMapping("/watchSpace")
    public InviteCodeDTO createWatchSpace(@Valid @RequestBody PostWatchSpaceDTO postWatchSpaceDTO)
    {
        return watchSpacesService.createWatchspace(postWatchSpaceDTO);
    }


    @Operation(
            summary = "Get All WatchSpaces",
            description = "Returns all WatchSpaces")
    @GetMapping("/watchSpace/getWatchSpace")
    public List<GetWatchSpaceDTO> getWatchSpaces()
    {
        return watchSpacesService.getWatchSpaces();
    }


    @Operation(
            summary = "Get WatchSpace By ID",
            description = "Returns WatchSpace information using its ID")
    @GetMapping("/watchSpace/getWatchSpace/{id}")
    public GetWatchSpaceDTO getWatchSpacesById(@Valid @PathVariable int id)
    {
        return watchSpacesService.getWatchSpaceById(id);
    }

    @Operation(
            summary = "End WatchSpace",
            description = "Ends a WatchSpace. Only the HOST who created the WatchSpace can end it.")
    @PostMapping("/endWatchSpace")
    public String endWatchSpace()
    {
        watchSpacesService.endWatchSpace();
        return "Ended Successfully";
    }
}
