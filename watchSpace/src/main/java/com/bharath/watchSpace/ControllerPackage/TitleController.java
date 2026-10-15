package com.bharath.watchSpace.ControllerPackage;

import com.bharath.watchSpace.DTO.GetTitlesDTO;
import com.bharath.watchSpace.DTO.PostTitlesDTO;
import com.bharath.watchSpace.ServicePackage.TitleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(
        name = "Titles",
        description = "Movie/Title management APIs")
@SecurityRequirement(name = "bearerAuth")
public class TitleController
{
    @Autowired
    TitleService titleService;

    @Operation(
            summary = "Get All Titles",
            description = "Returns all available titles")
    @GetMapping("/watchSpace/getTitles")
    public List<GetTitlesDTO> getTitles()
    {
        return titleService.getTitle();
    }


    @Operation(
            summary = "Get Title By ID",
            description = "Returns a title using its ID")
    @GetMapping("/watchSpace/getTitles/{id}")
    public GetTitlesDTO getTitleById(@PathVariable int id)
    {
        return titleService.getTitleById(id);
    }

}
