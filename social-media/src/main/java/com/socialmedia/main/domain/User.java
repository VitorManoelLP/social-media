package com.socialmedia.main.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Sets;
import com.socialmedia.main.domain.enums.FriendshipStatus;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.validator.constraints.UUID;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Entity
@Immutable
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@SQLRestriction(value = "email_verified = true")
@Table(name = "user_entity", schema = "keycloak")
public class User {

    @Id
    @UUID
    private String id;

    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String username;

    @OneToMany(mappedBy = "friend")
    private final Set<Friendship> friendsAccepted = new HashSet<>();

    @OneToMany(mappedBy = "requester")
    private final Set<Friendship> friendRequests = new HashSet<>();

    public boolean alreadyRequested() {
        return friendsAccepted.stream().anyMatch(friend -> requestedByLoggedUser(friend) && friend.getStatus().equals(FriendshipStatus.PENDING));
    }

    public boolean hasRequest() {
        return friendRequests.stream().anyMatch(friend -> hasRequest(friend) && friend.getStatus().equals(FriendshipStatus.PENDING));
    }

    private boolean requestedByLoggedUser(Friendship friend) {
        return friend.getRequester().getId().equals(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    private boolean hasRequest(Friendship friend) {
        return friend.getFriend().getId().equals(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public Set<Friendship> getFriends() {
        return Sets.union(
                friendRequests.stream().filter(friend -> FriendshipStatus.ACCEPTED.equals(friend.getStatus())).collect(Collectors.toSet()),
                friendsAccepted.stream().filter(friend -> FriendshipStatus.ACCEPTED.equals(friend.getStatus())).collect(Collectors.toSet())
        );
    }

}
