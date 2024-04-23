package com.socialmedia.main.domain.payload;

import java.util.List;
import java.util.UUID;

public record UserFriendInfo(UUID id, int friendsRequestPending, List<UsersFound> pendingFriends, List<UsersFound> friendsAccepted) {
}
