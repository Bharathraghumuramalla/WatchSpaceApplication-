package com.bharath.watchSpace.ServicePackage;

import com.bharath.watchSpace.DTO.GetParticipantsDTO;
import com.bharath.watchSpace.DTO.JoinWatchSpaceDTO;
import com.bharath.watchSpace.ENUMS.WatchSpaceStatus;
import com.bharath.watchSpace.Entities.Users;
import com.bharath.watchSpace.Entities.WatchSpace;
import com.bharath.watchSpace.Entities.WatchSpaceParticipant;
import com.bharath.watchSpace.ExceptionsAndErrors.ActiveWatchSpaceException;
import com.bharath.watchSpace.ExceptionsAndErrors.BadRequestException;
import com.bharath.watchSpace.ExceptionsAndErrors.ResourceNotFoundException;
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
public class WatchSpaceParticipantService
{
    @Autowired
    WatchSpaceParticipantRepository watchSpaceParticipantRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    WatchSpaceRepository watchSpaceRepository;

    public JoinWatchSpaceDTO joinWatchSpace(String inviteCode)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Users user = usersRepository.findByusername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        if (watchSpaceParticipantRepository
                .existsByUsersAndWatchSpace_Status(
                        user, WatchSpaceStatus.ACTIVE)) {

            throw new ActiveWatchSpaceException(
                    "You are already participating in an active WatchSpace");
        }
        WatchSpace watchSpace = watchSpaceRepository.findByinviteCode(inviteCode)
                .orElseThrow(() ->
                        new ResourceNotFoundException("No WatchSpace Found"));

        if (watchSpace.getStatus() == WatchSpaceStatus.ENDED) {
            throw new BadRequestException("WatchSpace has already ended");
        }
        boolean alreadyJoined =
                watchSpaceParticipantRepository
                        .existsByUsers_UserIdAndWatchSpace_WatchspaceId(
                                user.getUserId(),
                                watchSpace.getWatchspaceId());

        if (alreadyJoined) {
            throw new BadRequestException(
                    "You have already joined this WatchSpace"
            );
        }

        WatchSpaceParticipant participant = new WatchSpaceParticipant();
        participant.setUsers(user);
        participant.setJoinedTime(LocalDateTime.now());
        participant.setWatchSpace(watchSpace);

        watchSpaceParticipantRepository.save(participant);

        JoinWatchSpaceDTO dto = new JoinWatchSpaceDTO();

        dto.setWatchspaceId(watchSpace.getWatchspaceId());
        dto.setTitleId(watchSpace.getTitle().getTitleId());
        dto.setInviteCode(watchSpace.getInviteCode());
        dto.setStatus(watchSpace.getStatus());
        dto.setHostId(watchSpace.getHost().getUserId());

        return dto;
    }
    public List<GetParticipantsDTO> getParticipants(Integer watchSpaceId)
    {
        watchSpaceRepository.findById(watchSpaceId)
                .orElseThrow(() -> new ResourceNotFoundException("WatchSpace not found"));

        List<WatchSpaceParticipant> participants =
                watchSpaceParticipantRepository
                        .findByWatchSpace_WatchspaceIdOrderByJoinedTimeAsc(watchSpaceId);

        List<GetParticipantsDTO> result = new ArrayList<>();

        for (WatchSpaceParticipant participant : participants)
        {
            GetParticipantsDTO dto = new GetParticipantsDTO();

            dto.setUserId(participant.getUsers().getUserId());
            dto.setUsername(participant.getUsers().getUsername());
            dto.setJoinedTime(participant.getJoinedTime());

            result.add(dto);
        }

        return result;
    }

    public void leaveWatchSpace() {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        Users user = usersRepository.findByusername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        WatchSpaceParticipant participant =
                watchSpaceParticipantRepository
                        .findByUsers_UserId(user.getUserId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "You are not part of any WatchSpace"));

        WatchSpace watchSpace = participant.getWatchSpace();

        watchSpaceParticipantRepository.delete(participant);
    }
}
