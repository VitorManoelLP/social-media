package com.socialmedia.main.application.service;

import com.socialmedia.main.application.repository.UserRepository;
import com.socialmedia.main.domain.payload.UsersFound;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchUser {

    private final UserRepository userRepository;

    public Page<UsersFound> search(String search, Pageable pageable) {
        return userRepository.findAll(search, pageable)
                .map(user -> new UsersFound(user.getFirstName(), user.getUsername()));
    }

}
