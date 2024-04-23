package com.socialmedia.main.application.resource;

import com.socialmedia.main.application.service.SearchUser;
import com.socialmedia.main.application.service.SearchUserFriendInfo;
import com.socialmedia.main.application.service.SendRequest;
import com.socialmedia.main.domain.payload.UserFriendInfo;
import com.socialmedia.main.domain.payload.UsersFound;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserResource {

    private final SearchUser searchUser;
    private final SendRequest sendRequest;
    private final SearchUserFriendInfo searchUserFriendInfo;

    @GetMapping
    public ResponseEntity<Page<UsersFound>> findAllUsers(@RequestParam(required = false, defaultValue = "") String search, Pageable pageable) {
        return ResponseEntity.ok(searchUser.search(search, pageable));
    }

    @PostMapping("/request/{idUser}")
    public ResponseEntity<Void> sendFriendShipRequest(@PathVariable("idUser") String idUser) {
        sendRequest.execute(idUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/info")
    public ResponseEntity<UserFriendInfo> findUserFriendsInfo() {
        return ResponseEntity.ok(searchUserFriendInfo.search());
    }

}
