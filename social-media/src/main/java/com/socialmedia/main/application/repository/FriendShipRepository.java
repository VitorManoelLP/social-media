package com.socialmedia.main.application.repository;

import com.socialmedia.main.application.exception.AlreadyExistsFriendRequestException;
import com.socialmedia.main.domain.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FriendShipRepository extends JpaRepository<Friendship, UUID> {

    default void saveFriendship(Friendship friendship) {

        if (existsFriendship(friendship.getFriend().getId(), friendship.getRequester().getId())) {
            throw new AlreadyExistsFriendRequestException("Already exists friend request to the same person");
        }

        save(friendship);
    }

    @Query("SELECT COUNT(f) > 0 FROM Friendship f WHERE f.friend.id = :friend AND f.requester.id = :requester")
    boolean existsFriendship(@Param("friend") String idFriend, @Param("requester") String idRequester);

}
