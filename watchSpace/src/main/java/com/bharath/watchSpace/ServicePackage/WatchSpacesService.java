package com.bharath.watchSpace.ServicePackage;

import com.bharath.watchSpace.DTO.GetWatchSpaceDTO;
import com.bharath.watchSpace.DTO.InviteCodeDTO;
import com.bharath.watchSpace.DTO.PostWatchSpaceDTO;
import com.bharath.watchSpace.ENUMS.WatchSpaceStatus;
import com.bharath.watchSpace.Entities.Title;
import com.bharath.watchSpace.Entities.Users;
import com.bharath.watchSpace.Entities.WatchSpace;
import com.bharath.watchSpace.ExceptionsAndErrors.ActiveWatchSpaceException;
import com.bharath.watchSpace.ExceptionsAndErrors.ResourceNotFoundException;
import com.bharath.watchSpace.RepositoryPackage.TitleRepository;
import com.bharath.watchSpace.RepositoryPackage.UsersRepository;
import com.bharath.watchSpace.RepositoryPackage.WatchSpaceParticipantRepository;
import com.bharath.watchSpace.RepositoryPackage.WatchSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class WatchSpacesService
{

    @Autowired
    WatchSpaceRepository watchSpaceRepository;

    @Autowired
    TitleRepository titleRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    WatchSpaceParticipantRepository watchSpaceParticipantRepository;

    public InviteCodeDTO createWatchspace(PostWatchSpaceDTO postWatchSpaceDTO)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Users user = usersRepository.findByusername(username).orElseThrow(()-> new ResourceNotFoundException("User not found"));
        if (watchSpaceRepository.existsByHostAndStatus(
                user, WatchSpaceStatus.ACTIVE)) {

            throw new ActiveWatchSpaceException(
                    "You are already hosting an active WatchSpace"
            );
        }
        Title title = titleRepository.findById(postWatchSpaceDTO.getTitleId()).orElseThrow(()-> new ResourceNotFoundException("Title not found"));

        WatchSpace watchSpace = new WatchSpace();
        watchSpace.setStatus(WatchSpaceStatus.ACTIVE);
        watchSpace.setTitle(title);
        watchSpace.setInviteCode(WatchSpace.createInviteCode());
        watchSpace.setHost(user);
        watchSpaceRepository.save(watchSpace);

        InviteCodeDTO inviteCodeDTO = new InviteCodeDTO();
        inviteCodeDTO.setInviteCode(watchSpace.getInviteCode());
        inviteCodeDTO.setMessage("WatchSpace created successfully");
        return inviteCodeDTO;
    }

    public List<GetWatchSpaceDTO> getWatchSpaces()
    {
        List<WatchSpace> list = watchSpaceRepository.findAll();

        List<GetWatchSpaceDTO> result = new ArrayList<>();

        for(WatchSpace watchSpace : list)
        {
            result.add(convertToDTO(watchSpace));
        }
        return result;
    }

    public GetWatchSpaceDTO getWatchSpaceById(int id)
    {
        WatchSpace watchSpace = watchSpaceRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("WatchSpace Not Found"));
        return convertToDTO(watchSpace);
    }

    public GetWatchSpaceDTO convertToDTO(WatchSpace watchSpace)
    {
        GetWatchSpaceDTO getWatchSpaceDTO = new GetWatchSpaceDTO();
        getWatchSpaceDTO.setTitleId(watchSpace.getTitle().getTitleId());
        getWatchSpaceDTO.setWatchspaceId(watchSpace.getWatchspaceId());
        getWatchSpaceDTO.setHostId(watchSpace.getHost().getUserId());
        getWatchSpaceDTO.setInviteCode(watchSpace.getInviteCode());
        getWatchSpaceDTO.setStatus(watchSpace.getStatus());
        return getWatchSpaceDTO;
    }

    public void endWatchSpace() {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        Users user = usersRepository.findByusername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        if (!user.getRole().equals("HOST")) {
            throw new AccessDeniedException("You are restricted");
        }

        WatchSpace watchSpace =
                watchSpaceRepository
                        .findByHost_UserIdAndStatus(
                                user.getUserId(),
                                WatchSpaceStatus.ACTIVE)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "You are not hosting an active WatchSpace"));

        Integer id = watchSpace.getWatchspaceId();

        watchSpace.setStatus(WatchSpaceStatus.ENDED);
        watchSpaceRepository.save(watchSpace);

        watchSpaceParticipantRepository
                .deleteByWatchSpace_WatchspaceId(id);
    }
}