package com.socialmedia.main.domain.payload;

import java.util.UUID;

public record UsersFound(UUID id, String name, String nickname, boolean alreadyRequested, boolean hasRequest) {
}
