package com.socialmedia.main.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.socialmedia.main.domain.enums.FriendshipStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@ToString(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "friendship", schema = "social_media")
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JsonIgnoreProperties("friends")
    @JoinColumn(name = "id_friend")
    private User friend;

    @ManyToOne
    @JsonIgnoreProperties("friendRequests")
    @JoinColumn(name = "id_requester")
    private User requester;

    @Enumerated(EnumType.STRING)
    private FriendshipStatus status;

}
