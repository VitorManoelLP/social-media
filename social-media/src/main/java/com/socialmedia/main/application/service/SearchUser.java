package com.socialmedia.main.application.service;

import com.socialmedia.main.application.repository.UserRepository;
import com.socialmedia.main.domain.payload.UsersFound;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchUser {

    private final UserRepository userRepository;

    public Page<UsersFound> search(String search, Pageable pageable) {
        return userRepository.findAll(search, pageable)
                .map(user -> new UsersFound(
                        UUID.fromString(user.getId()),
                        user.getFirstName(),
                        user.getUsername(),
                        user.alreadyRequested(),
                        user.hasRequest())
                );
    }

}
