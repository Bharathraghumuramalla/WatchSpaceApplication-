package com.bharath.watchSpace.ControllerPackage;

import com.bharath.watchSpace.DTO.GetChatMessageDTO;
import com.bharath.watchSpace.DTO.PostMessageDTO;
import com.bharath.watchSpace.ServicePackage.ChatMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(
        name = "Chat",
        description = "WatchSpace chat APIs")
@SecurityRequirement(name = "bearerAuth")
public class ChatMessageController
{
    @Autowired
    ChatMessageService chatMessageService;


    @Operation(
            summary = "Send Message",
            description = "Allows a HOST or joined PLAYER to send a message")
    @PostMapping("/watchSpace/postMessage")
    public void postMessage(@Valid @RequestBody PostMessageDTO postMessageDTO)
    {
        chatMessageService.postMessage(postMessageDTO);
    }



    @Operation(
            summary = "Get Messages",
            description = "Returns all messages from the specified WatchSpace")
    @GetMapping("/watchSpace/{watchSpaceId}/messages")
    public List<GetChatMessageDTO> getMessages()
    {
        return chatMessageService.getMessages();
    }
}
