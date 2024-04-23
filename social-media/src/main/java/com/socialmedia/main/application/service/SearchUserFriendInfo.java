package com.socialmedia.main.application.service;

import com.socialmedia.main.domain.payload.UserFriendInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchUserFriendInfo {

    public UserFriendInfo search() {
        return null;
    }

}
