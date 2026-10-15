package com.bharath.watchSpace.RepositoryPackage;

import com.bharath.watchSpace.ENUMS.WatchSpaceStatus;
import com.bharath.watchSpace.Entities.Users;
import com.bharath.watchSpace.Entities.WatchSpaceParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WatchSpaceParticipantRepository extends JpaRepository<WatchSpaceParticipant, Integer>
{
    boolean existsByUsers_UserIdAndWatchSpace_WatchspaceId(Integer userId,Integer watchspaceId);

    List<WatchSpaceParticipant> findByWatchSpace_WatchspaceIdOrderByJoinedTimeAsc(Integer watchspaceId);

    void deleteByWatchSpace_WatchspaceId(Integer watchSpaceId);

    Optional<WatchSpaceParticipant> findByUsers_UserIdAndWatchSpace_WatchspaceId(Integer userId, Integer watchspaceId);

    boolean existsByUsersAndWatchSpace_Status(
            Users users,
            WatchSpaceStatus status);


    Optional<WatchSpaceParticipant>
    findByUsersAndWatchSpace_Status(
            Users users,
            WatchSpaceStatus status
    );

    Optional<WatchSpaceParticipant> findByUsers_UserId(Integer userId);

}
