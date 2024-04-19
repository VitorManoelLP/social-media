package com.socialmedia.main.domain;

import com.google.common.collect.Sets;
import com.socialmedia.main.domain.enums.FriendshipStatus;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Immutable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@Table(name = "user_entity", schema = "keycloak")
@Immutable
public class User {

    @Id
    private UUID id;

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

    public Set<Friendship> getFriends() {
        return Sets.union(
                friendRequests.stream().filter(friend -> FriendshipStatus.ACCEPTED.equals(friend.getStatus())).collect(Collectors.toSet()),
                friendsAccepted.stream().filter(friend -> FriendshipStatus.ACCEPTED.equals(friend.getStatus())).collect(Collectors.toSet())
        );
    }

}
