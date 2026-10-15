package com.bharath.watchSpace.RepositoryPackage;

import com.bharath.watchSpace.ENUMS.WatchSpaceStatus;
import com.bharath.watchSpace.Entities.Users;
import com.bharath.watchSpace.Entities.WatchSpace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WatchSpaceRepository extends JpaRepository<WatchSpace, Integer>
{
    Optional<WatchSpace> findByinviteCode(String inviteCode);

    boolean existsByHostAndStatus(Users host, WatchSpaceStatus status);

    Optional<WatchSpace> findByHostAndStatus(Users host, WatchSpaceStatus status);

    Optional<WatchSpace> findByHost_UserIdAndStatus(
            Integer userId,
            WatchSpaceStatus status
    );
}
