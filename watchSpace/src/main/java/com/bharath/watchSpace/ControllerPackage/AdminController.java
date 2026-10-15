package com.bharath.watchSpace.ControllerPackage;

import com.bharath.watchSpace.DTO.PostTitlesDTO;
import com.bharath.watchSpace.ServicePackage.TitleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(
        name = "Admin API's",
        description = "These API's are only accessed by Admin only")
public class AdminController
{
    @Autowired
    TitleService titleService;

    @Operation(
            summary = "Create Title",
            description = "Allows a HOST to create a new title")
    @PostMapping("/admin/postTitles")
    public String postTitle(@Valid @RequestBody PostTitlesDTO postTitlesDTO)
    {
        titleService.createTitle(postTitlesDTO);
        return "Posted Successfully";
    }
}
