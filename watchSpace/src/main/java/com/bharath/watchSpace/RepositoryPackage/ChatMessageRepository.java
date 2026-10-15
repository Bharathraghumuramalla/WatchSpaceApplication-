package com.bharath.watchSpace.RepositoryPackage;

import com.bharath.watchSpace.Entities.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer>
{
    List<ChatMessage> findByWatchSpace_WatchspaceIdOrderByMessageTimeAsc(Integer watchSpaceId);
}
