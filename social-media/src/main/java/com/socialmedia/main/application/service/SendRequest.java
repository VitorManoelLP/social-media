package com.socialmedia.main.application.service;

import com.socialmedia.main.application.repository.FriendShipRepository;
import com.socialmedia.main.application.util.SecurityUtils;
import com.socialmedia.main.domain.Friendship;
import com.socialmedia.main.domain.User;
import com.socialmedia.main.domain.enums.FriendshipStatus;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class SendRequest {

    private final EntityManager entityManager;
    private final FriendShipRepository friendShipRepository;

    public void execute(String idUser) {
        final User friendReference = entityManager.getReference(User.class, idUser);
        final User loggedUser = entityManager.getReference(User.class, SecurityUtils.getUserId());
        friendShipRepository.saveFriendship(new Friendship(UUID.randomUUID(), friendReference, loggedUser, FriendshipStatus.PENDING));
    }

}
