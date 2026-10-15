package com.bharath.watchSpace.ServicePackage;

import com.bharath.watchSpace.DTO.GetChatMessageDTO;
import com.bharath.watchSpace.DTO.PostMessageDTO;
import com.bharath.watchSpace.ENUMS.WatchSpaceStatus;
import com.bharath.watchSpace.Entities.ChatMessage;
import com.bharath.watchSpace.Entities.Users;
import com.bharath.watchSpace.Entities.WatchSpace;
import com.bharath.watchSpace.Entities.WatchSpaceParticipant;
import com.bharath.watchSpace.ExceptionsAndErrors.ResourceNotFoundException;
import com.bharath.watchSpace.RepositoryPackage.ChatMessageRepository;
import com.bharath.watchSpace.RepositoryPackage.UsersRepository;
import com.bharath.watchSpace.RepositoryPackage.WatchSpaceParticipantRepository;
import com.bharath.watchSpace.RepositoryPackage.WatchSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatMessageService
{
    @Autowired
    ChatMessageRepository chatMessageRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    WatchSpaceRepository watchSpaceRepository;

    @Autowired
    WatchSpaceParticipantRepository watchSpaceParticipantRepository;

    public void postMessage(PostMessageDTO postMessageDTO)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Users user = usersRepository.findByusername(username).orElseThrow(()-> new ResourceNotFoundException("User not found"));
        WatchSpace watchSpace = null;
        if(user.getRole().equals("HOST"))
        {
            watchSpace = watchSpaceRepository
                    .findByHostAndStatus(user, WatchSpaceStatus.ACTIVE)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "No active WatchSpace found for this host"));
        }
        else
        {
            WatchSpaceParticipant participant =
                    watchSpaceParticipantRepository
                            .findByUsersAndWatchSpace_Status(
                                    user, WatchSpaceStatus.ACTIVE)
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "You are not in an active WatchSpace"));

             watchSpace = participant.getWatchSpace();
        }


        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setUser(user);
        chatMessage.setWatchSpace(watchSpace);
        chatMessage.setMessage(postMessageDTO.getMessage());
        chatMessage.setMessageTime(LocalDateTime.now());

        chatMessageRepository.save(chatMessage);
    }

    public List<GetChatMessageDTO> getMessages() {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        Users user = usersRepository.findByusername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        WatchSpace watchSpace;

        if (user.getRole().equals("HOST")) {

            watchSpace = watchSpaceRepository
                    .findByHostAndStatus(user, WatchSpaceStatus.ACTIVE)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "No active WatchSpace found for this host"));

        } else {

            WatchSpaceParticipant participant =
                    watchSpaceParticipantRepository
                            .findByUsersAndWatchSpace_Status(
                                    user, WatchSpaceStatus.ACTIVE)
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "You are not in an active WatchSpace"));

            watchSpace = participant.getWatchSpace();
        }

        Integer watchSpaceId = watchSpace.getWatchspaceId();

        List<ChatMessage> messages =
                chatMessageRepository
                        .findByWatchSpace_WatchspaceIdOrderByMessageTimeAsc(
                                watchSpaceId);

        List<GetChatMessageDTO> result = new ArrayList<>();

        for (ChatMessage chatMessage : messages) {

            GetChatMessageDTO dto = new GetChatMessageDTO();

            dto.setUsername(chatMessage.getUser().getUsername());
            dto.setMessage(chatMessage.getMessage());
            dto.setMessageTime(chatMessage.getMessageTime());

            result.add(dto);
        }

        return result;
    }
}
